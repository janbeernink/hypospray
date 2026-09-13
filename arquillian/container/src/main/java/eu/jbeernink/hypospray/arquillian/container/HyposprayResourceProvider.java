package eu.jbeernink.hypospray.arquillian.container;

import java.lang.annotation.Annotation;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;

public final class HyposprayResourceProvider implements ResourceProvider {
	@Override
	public boolean canProvide(Class<?> type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object lookup(ArquillianResource resource, Annotation... qualifiers) {
		throw new UnsupportedOperationException();
	}
}
