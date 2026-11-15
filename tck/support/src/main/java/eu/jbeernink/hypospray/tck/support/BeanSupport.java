package eu.jbeernink.hypospray.tck.support;

import java.io.IOException;

import org.jboss.cdi.tck.spi.Beans;

public class BeanSupport implements Beans {
	@Override
	public boolean isProxy(Object instance) {
		return false;
	}

	@Override
	public byte[] passivate(Object instance) throws IOException {
		return new byte[0];
	}

	@Override
	public Object activate(byte[] bytes) throws IOException, ClassNotFoundException {
		return null;
	}
}
