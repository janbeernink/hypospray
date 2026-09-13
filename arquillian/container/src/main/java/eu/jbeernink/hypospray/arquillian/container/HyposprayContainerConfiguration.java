package eu.jbeernink.hypospray.arquillian.container;

import org.jboss.arquillian.container.spi.ConfigurationException;
import org.jboss.arquillian.container.spi.client.container.ContainerConfiguration;

public record HyposprayContainerConfiguration() implements ContainerConfiguration {
	@Override
	public void validate() throws ConfigurationException {

	}
}
