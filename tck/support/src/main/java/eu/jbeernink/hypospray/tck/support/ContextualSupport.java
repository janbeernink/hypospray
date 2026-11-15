package eu.jbeernink.hypospray.tck.support;

import jakarta.enterprise.context.spi.Context;

import org.jboss.cdi.tck.spi.Contextuals;

public class ContextualSupport implements Contextuals {

	@Override
	public <T> Inspectable<T> create(T instance, Context context) {
    throw new UnsupportedOperationException("Not yet implemented");
	}
}
