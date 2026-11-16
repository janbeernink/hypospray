package eu.jbeernink.hypospray.arquillian.container;

import org.jboss.arquillian.container.spi.ConfigurationException;
import org.jboss.arquillian.container.spi.client.container.ContainerConfiguration;

public class HyposprayContainerConfiguration implements ContainerConfiguration {

	private boolean startContainer = true;


	@Override
	public void validate() throws ConfigurationException {

	}

	public boolean isStartContainer() {
		return startContainer;
	}

	public void setStartContainer(boolean startContainer) {
		this.startContainer = startContainer;
	}
}
