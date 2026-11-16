package eu.jbeernink.hypospray.arquillian.container;

import static java.lang.System.Logger.Level.INFO;

import java.lang.System.Logger;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.shrinkwrap.api.Archive;

public final class HyposprayContainer implements DeployableContainer<HyposprayContainerConfiguration> {

	private static final Logger logger = System.getLogger(HyposprayContainer.class.getName());

	private HyposprayContainerConfiguration configuration;
	private final TestArchiveManager testArchiveManager = new TestArchiveManager();
	private SeContainer container;

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

		TestArchiveManager.getInstance().setTestArchive(archive);

		if (configuration.isStartContainer()) {
			logger.log(INFO, "Booting Hypospray container.");
			container = SeContainerInitializer.newInstance().disableDiscovery().initialize();
		} else {
			logger.log(INFO, "Skipped booting of Hypospray container due to configuration.");
		}

		return new ProtocolMetaData();
	}

	@Override
	public void undeploy(Archive<?> archive) throws DeploymentException {
		try (var _ = container) {
			logger.log(INFO, "Shutting down Hypospray test instance.");
		}
	}
}
