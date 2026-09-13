package eu.jbeernink.hypospray.arquillian.container;

import java.lang.System.Logger;
import java.util.Map;

import jakarta.enterprise.inject.build.compatible.spi.BuildCompatibleExtension;
import jakarta.enterprise.inject.build.compatible.spi.Discovery;
import jakarta.enterprise.inject.build.compatible.spi.ScannedClasses;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/// Build compatible extension that performs bean discover in Shrinkwrap {@link JavaArchive} instances.
public class ShrinkwrapArchiveBeanDiscoveryExtension implements BuildCompatibleExtension {

	private static final Logger logger =  System.getLogger(ShrinkwrapArchiveBeanDiscoveryExtension.class.getName());

	@Discovery
	public void discoverBeans(ScannedClasses scannedClasses) {
		logger.log(Logger.Level.DEBUG, "Starting bean discovery from ");
		Archive<?> testArchive = TestArchiveManager.getInstance().getTestArchive();
		if (!(testArchive instanceof JavaArchive javaArchive)) {
			throw new IllegalStateException("Unsupported test archive type: %s".formatted(testArchive.getClass().getName()));
		}

		Map<ArchivePath, Node> content = javaArchive.getContent();
		Map<ArchivePath, Node> content1 = javaArchive.getContent(path -> path.get().endsWith(".class"));
		content.containsKey("/META-INF/beans.xml");
		content1.forEach((path , node) -> System.out.println(path + ": " + node));
	}
}
