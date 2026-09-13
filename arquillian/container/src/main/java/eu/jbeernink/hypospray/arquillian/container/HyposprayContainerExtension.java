package eu.jbeernink.hypospray.arquillian.container;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;

public class HyposprayContainerExtension implements LoadableExtension {
	@Override
	public void register(ExtensionBuilder builder) {
		builder.service(DeployableContainer.class, HyposprayContainer.class);
		builder.service(ResourceProvider.class, HyposprayResourceProvider.class);
	}
}
