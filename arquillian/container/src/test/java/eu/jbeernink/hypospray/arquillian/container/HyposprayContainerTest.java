package eu.jbeernink.hypospray.arquillian.container;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import jakarta.enterprise.inject.spi.CDI;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@ArquillianTest
class HyposprayContainerTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class);
	}

	@Test
	@DisplayName("starts a new Hypospray instance")
	void startsNewHyposprayInstance() {
		CDI<Object> current = CDI.current();

		assertInstanceOf(HyposprayContainer.class, current.get());
	}
}