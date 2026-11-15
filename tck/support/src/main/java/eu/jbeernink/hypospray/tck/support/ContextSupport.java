package eu.jbeernink.hypospray.tck.support;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.spi.Context;
import jakarta.enterprise.inject.spi.CDI;

import org.jboss.cdi.tck.spi.Contexts;

public class ContextSupport implements Contexts<Context> {

	@Override
	public void setActive(Context context) {
	}

	@Override
	public void setInactive(Context context) {

	}

	@Override
	public Context getRequestContext() {
		return CDI.current().getBeanContainer().getContext(RequestScoped.class);
	}

	@Override
	public Context getDependentContext() {
		return CDI.current().getBeanContainer().getContext(Dependent.class);
	}

	@Override
	public void destroyContext(Context context) {

	}
}
