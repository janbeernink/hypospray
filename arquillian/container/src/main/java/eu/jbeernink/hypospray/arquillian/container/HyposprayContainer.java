package eu.jbeernink.hypospray.arquillian.container;

import static java.lang.Character.isJavaIdentifierStart;
import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.INFO;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.asset.ArchiveAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public final class HyposprayContainer implements DeployableContainer<HyposprayContainerConfiguration> {

	private static final Logger logger = System.getLogger(HyposprayContainer.class.getName());

	private HyposprayContainerConfiguration configuration;
	private final TestArchiveManager testArchiveManager = new TestArchiveManager();
	private SeContainer container;
	private Path testDirectory;

	private String originalModulePath;
	private String originalClassPath;
	private ClassLoader parentLoader;

	@Override
	public Class<HyposprayContainerConfiguration> getConfigurationClass() {
		return HyposprayContainerConfiguration.class;
	}

	@Override
	public ProtocolDescription getDefaultProtocol() {
		return ProtocolDescription.DEFAULT;
	}

	@Override
	public void setup(HyposprayContainerConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public ProtocolMetaData deploy(Archive<?> archive) throws DeploymentException {
		if (container != null) {
			throw new IllegalStateException("Hypospray container already running.");
		}

		try {
			testDirectory = Files.createTempDirectory("HyposprayTestContainer");
			logger.log(DEBUG, "Created test directory: %s".formatted(testDirectory));
			parentLoader = Thread.currentThread().getContextClassLoader();
			ModuleLayer boot = ModuleLayer.boot();

			List<Path> testArchives = deployTestArchive(archive);

			ModuleFinder moduleFinder = ModuleFinder.of(testArchives.toArray(Path[]::new));
			List<String> moduleNames =
					moduleFinder.findAll().stream().map(reference -> reference.descriptor().name()).toList();
			Configuration layerConfiguration =
					ModuleLayer.boot().configuration().resolve(moduleFinder, ModuleFinder.of(), moduleNames);

			originalModulePath = System.getProperty("jdk.module.path");
			originalClassPath = System.getProperty("java.class.path");
			System.setProperty("jdk.module.path", testDirectory.toString());
			System.setProperty("java.class.path", "");

			ModuleLayer moduleLayer =
					boot.defineModulesWithManyLoaders(layerConfiguration, ClassLoader.getSystemClassLoader());
			ClassLoader moduleLoader =
					moduleLayer.modules().stream().findFirst().map(Module::getClassLoader).orElse(parentLoader);
			Thread.currentThread().setContextClassLoader(moduleLoader);
			if (configuration.isStartContainer()) {
				logger.log(INFO, "Booting Hypospray container.");
				container = SeContainerInitializer.newInstance().initialize();
			} else {
				logger.log(INFO, "Skipped booting of Hypospray container due to configuration.");
			}

			return new ProtocolMetaData();
		} catch (IOException e) {
			throw new DeploymentException("Unable to boot Hypospray container with test archive: ", e);
		}
	}

	private List<Path> deployTestArchive(Archive<?> archive) throws MalformedURLException {
		return switch (archive) {
			case JavaArchive javaArchive -> List.of(deployJavaArchive(javaArchive, javaArchive.getName()));
			case GenericArchive genericArchive -> genericArchive.getContent(path -> path.get().endsWith(".jar"))
			                                                    .entrySet()
			                                                    .stream()
			                                                    .map(entry -> deployJavaArchive(
					                                                    ((ArchiveAsset) entry.getValue()).getArchive(),
					                                                    entry.getKey().get()))
			                                                    .toList();
			default -> throw new IllegalStateException("Unexpected value: " + archive);
		};
	}

	private Path deployJavaArchive(Archive<?> archive, String archiveName) {
		if (Arrays.stream(archiveName.split("-")).mapToInt(s -> s.codePointAt(0)).anyMatch(c -> !isJavaIdentifierStart(c))) {
			archiveName = "testarchive" + ThreadLocalRandom.current().nextInt() + ".jar";
		}
		Path destinationPath = testDirectory.resolve(archiveName);
		archive.as(ZipExporter.class).exportTo(destinationPath.toFile());
		return destinationPath;
	}

	@Override
	public void undeploy(Archive<?> archive) throws DeploymentException {
		try (var _ = container) {
			logger.log(INFO, "Shutting down Hypospray test instance.");
		} finally {
			Thread.currentThread().setContextClassLoader(parentLoader);
			System.setProperty("jdk.module.path", originalModulePath == null ? "" : originalModulePath);
			System.setProperty("java.class.path", originalClassPath);
		}
	}
}
