package eu.jbeernink.hypospray.tck.support;

import jakarta.el.ELContext;
import jakarta.enterprise.inject.spi.BeanManager;

import org.jboss.cdi.tck.spi.EL;

public class ElSupport implements EL {
	@Override
	public <T> T evaluateValueExpression(BeanManager beanManager, String expression, Class<T> expectedType) {
		throw new UnsupportedOperationException("EL is not supported in Hypospray.");
	}

	@Override
	public <T> T evaluateMethodExpression(BeanManager beanManager, String expression, Class<T> expectedType,
	                                      Class<?>[] expectedParamTypes, Object[] expectedParams) {
		throw new UnsupportedOperationException("EL is not supported in Hypospray.");
	}

	@Override
	public ELContext createELContext(BeanManager beanManager) {
		throw new UnsupportedOperationException("EL is not supported in Hypospray.");
	}
}
