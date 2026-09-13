package eu.jbeernink.hypospray.arquillian.container;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.enterprise.inject.spi.CDI;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import eu.jbeernink.hypospray.arquillian.container.bean.TestBean;
import eu.jbeernink.hypospray.core.Container;

@ArquillianTest
class HyposprayContainerTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addClass(TestBean.class);
	}

	@Test
	@DisplayName("starts a new Hypospray instance")
	void startsNewHyposprayInstance() {
		CDI<Object> current = CDI.current();

		assertInstanceOf(Container.class, current);
	}

	@Nested
	@DisplayName("the started container")
	class StartedContainer {

		private CDI<Object> current;

		@BeforeEach
		void setUp() {
			current = CDI.current();
		}

		@Test
		@DisplayName("allows a bean provided in the ShrinkWrap archive to be obtained.")
		void allowsBeanInShrinkWrapArchiveToBeObtained() {
			TestBean testBean = current.select(TestBean.class).get();

			assertNotNull(testBean);
		}
	}
}