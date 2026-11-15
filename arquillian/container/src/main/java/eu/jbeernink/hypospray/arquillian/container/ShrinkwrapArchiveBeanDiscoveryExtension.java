package eu.jbeernink.hypospray.arquillian.container;

import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.INFO;

import java.lang.System.Logger;

import jakarta.enterprise.inject.build.compatible.spi.BuildCompatibleExtension;
import jakarta.enterprise.inject.build.compatible.spi.Discovery;
import jakarta.enterprise.inject.build.compatible.spi.ScannedClasses;

import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.asset.ClassAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/// Build compatible extension that performs bean discover in Shrinkwrap {@link JavaArchive} instances.
public class ShrinkwrapArchiveBeanDiscoveryExtension implements BuildCompatibleExtension {

	private static final Logger logger = System.getLogger(ShrinkwrapArchiveBeanDiscoveryExtension.class.getName());

	@Discovery
	public void discoverBeans(ScannedClasses scannedClasses) {
		TestArchiveManager.getInstance().getTestArchive().ifPresentOrElse(testArchive -> {
			logger.log(DEBUG, "Starting bean discovery from test archive: %s".formatted(testArchive));
			testArchive.getContent()
			           .values()
			           .stream()
			           .map(Node::getAsset)
			           .filter(asset -> asset instanceof ClassAsset)
			           .map(asset -> (ClassAsset) asset)
			           .map(ClassAsset::getSource)
			           .map(Class::getName)
			           .forEach(scannedClasses::add);
		}, () -> logger.log(INFO, "No test archive set, initializing container without bean discovery."));
	}
}
