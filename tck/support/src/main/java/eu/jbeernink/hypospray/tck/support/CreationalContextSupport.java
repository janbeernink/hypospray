package eu.jbeernink.hypospray.tck.support;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import jakarta.enterprise.context.spi.Contextual;
import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.CDI;

import org.jboss.cdi.tck.spi.CreationalContexts;

public class CreationalContextSupport implements CreationalContexts {
	@Override
	public <T> Inspectable<T> create(Contextual<T> contextual) {
		CreationalContext<T> creationalContext = CDI.current().getBeanContainer().createCreationalContext(contextual);

		return new InspectableCreationalContext<>(creationalContext);
	}

	private static class InspectableCreationalContext<T> implements Inspectable<T> {

		private final CreationalContext<T> creationalContext;
		private final ConcurrentLinkedDeque<T> stack = new ConcurrentLinkedDeque<>();
		private final AtomicBoolean released = new AtomicBoolean(false);

		private InspectableCreationalContext(CreationalContext<T> creationalContext) {
			this.creationalContext = creationalContext;
		}

		@Override
		public void push(T incompleteInstance) {
			stack.push(incompleteInstance);
			creationalContext.push(incompleteInstance);
		}

		@Override
		public void release() {
			released.set(true);
			creationalContext.release();
		}

		@Override
		public boolean isPushCalled() {
			return !stack.isEmpty();
		}

		@Override
		public Object getLastBeanPushed() {
		    return stack.peekLast();
		}

		@Override
		public boolean isReleaseCalled() {
			return released.get();
		}
	}
}
