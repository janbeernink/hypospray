package eu.jbeernink.hypospray.model.information.synthetic;

import static eu.jbeernink.hypospray.model.information.synthetic.builder.SyntheticClassInformationBuilder.newClassBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import eu.jbeernink.hypospray.model.TypeFactory;
import eu.jbeernink.hypospray.model.information.source.ClassInformationSource;
import eu.jbeernink.hypospray.model.types.TypeInstance;

@DisplayName("SyntheticClassInformation")
class SyntheticClassInformationTest {

	@Nested
	@DisplayName("with an invalid class name")
	class WithInvalidClassName {

		private final SyntheticClassInformation<?> classInformation = newClassBuilder("foo", "Bar", _ -> {})
				.build();

		@Test
		@DisplayName("classInstance() throws IllegalStateException.")
		void classInstance_throwsIllegalStateException() {
			var exception =
					assertThrows(IllegalStateException.class, classInformation::classInstance);

			assertEquals("Unable to load class: foo.Bar.", exception.getMessage());
		}
	}

	@Nested
	@DisplayName("without a superclass")
	class WithoutSuperClass {
		private final SyntheticClassInformation<?> classInformation = newClassBuilder("foo", "Bar", _ -> {})
				.build();

		@Test
		@DisplayName("superClass() returns null.")
		void superClass_returnsNull() {
			TypeInstance superClass = classInformation.superClass();

			assertNull(superClass);
		}
	}

	@Nested
	@DisplayName("with a superclass")
	class WithSuperClass {
		private final SyntheticClassInformation<?> classInformation = newClassBuilder("foo", "Bar", _ -> {})
				.withSuperclass(ClassInformationSource.getInstance().getClassInformation(Object.class)).build();

		@Test
		@DisplayName("superClass() returns the type instance of the super class.")
		void superClass_returnsTypeInstance() {
			TypeInstance superClass = classInformation.superClass();

			assertEquals(TypeFactory.getInstance().ofObject(), superClass);
		}
	}
}