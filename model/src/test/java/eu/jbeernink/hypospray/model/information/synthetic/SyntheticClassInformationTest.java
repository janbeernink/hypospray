package eu.jbeernink.hypospray.model.information.synthetic;

import static eu.jbeernink.hypospray.model.information.ClassKind.ANNOTATION;
import static eu.jbeernink.hypospray.model.information.ClassKind.ENUM;
import static eu.jbeernink.hypospray.model.information.ClassKind.INTERFACE;
import static eu.jbeernink.hypospray.model.information.ClassKind.PLAIN_CLASS;
import static eu.jbeernink.hypospray.model.information.ClassKind.RECORD;
import static eu.jbeernink.hypospray.model.information.synthetic.builder.SyntheticClassInformationBuilder.newClassBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

		private final SyntheticClassInformation<?> classInformation = newClassBuilder("foo", "Bar", _ -> {}).build();

		@Test
		@DisplayName("classInstance() throws IllegalStateException.")
		void classInstance_throwsIllegalStateException() {
			var exception = assertThrows(IllegalStateException.class, classInformation::classInstance);

			assertEquals("Unable to load class: foo.Bar.", exception.getMessage());
		}
	}

	@Nested
	@DisplayName("without a superclass")
	class WithoutSuperClass {
		private final SyntheticClassInformation<?> classInformation = newClassBuilder("foo", "Bar", _ -> {}).build();

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
		private final SyntheticClassInformation<?> classInformation = newClassBuilder("foo", "Bar", _ -> {}).withSuperclass(
				ClassInformationSource.getInstance().getClassInformation(Object.class)).build();

		@Test
		@DisplayName("superClass() returns the type instance of the super class.")
		void superClass_returnsTypeInstance() {
			TypeInstance superClass = classInformation.superClass();

			assertEquals(TypeFactory.getInstance().ofObject(), superClass);
		}
	}

	@Nested
	@DisplayName("with class of kind plain class")
	class WithClassOfKindPlainClass {
		private final SyntheticClassInformation<?> classInformation =
				newClassBuilder("foo", "Bar", _ -> {}).setClassKind(PLAIN_CLASS).build();

		@Test
		@DisplayName("isInterface() returns false.")
		void isInterface_returnsFalse() {
			boolean isInterface = classInformation.isInterface();

			assertFalse(isInterface);
		}

		@Test
		@DisplayName("isRecord() returns false.")
		void isRecord_returnsFalse() {
			boolean isRecord = classInformation.isRecord();

			assertFalse(isRecord);
		}

		@Test
		@DisplayName("isAnnotation() returns false.")
		void isAnnotation_returnsFalse() {
			boolean isAnnotation = classInformation.isAnnotation();

			assertFalse(isAnnotation);
		}

		@Test
		@DisplayName("isPlainClass() returns false.")
		void isPlainClass_returnsFalse() {
			boolean isPlainClass = classInformation.isPlainClass();

			assertTrue(isPlainClass);
		}

		@Test
		@DisplayName("isEnum() returns false.")
		void isEnum_returnsFalse() {
			boolean isEnum = classInformation.isEnum();

			assertFalse(isEnum);
		}
	}

	@Nested
	@DisplayName("with class of kind interface")
	class WithClassOfKindInterface {
		private final SyntheticClassInformation<?> classInformation =
				newClassBuilder("foo", "Bar", _ -> {}).setClassKind(INTERFACE).build();

		@Test
		@DisplayName("isInterface() returns true.")
		void isInterface_returnsTrue() {
			boolean isInterface = classInformation.isInterface();

			assertTrue(isInterface);
		}

		@Test
		@DisplayName("isRecord() returns false.")
		void isRecord_returnsFalse() {
			boolean isRecord = classInformation.isRecord();

			assertFalse(isRecord);
		}

		@Test
		@DisplayName("isAnnotation() returns false.")
		void isAnnotation_returnsFalse() {
			boolean isAnnotation = classInformation.isAnnotation();

			assertFalse(isAnnotation);
		}

		@Test
		@DisplayName("isPlainClass() returns false.")
		void isPlainClass_returnsFalse() {
			boolean isPlainClass = classInformation.isPlainClass();

			assertFalse(isPlainClass);
		}

		@Test
		@DisplayName("isEnum() returns false.")
		void isEnum_returnsFalse() {
			boolean isEnum = classInformation.isEnum();

			assertFalse(isEnum);
		}
	}

	@Nested
	@DisplayName("with class of kind enum")
	class WithClassOfKindEnum {
		private final SyntheticClassInformation<?> classInformation =
				newClassBuilder("foo", "Bar", _ -> {}).setClassKind(ENUM).build();

		@Test
		@DisplayName("isInterface() returns false.")
		void isInterface_returnsFalse() {
			boolean isInterface = classInformation.isInterface();

			assertFalse(isInterface);
		}

		@Test
		@DisplayName("isRecord() returns false.")
		void isRecord_returnsFalse() {
			boolean isRecord = classInformation.isRecord();

			assertFalse(isRecord);
		}

		@Test
		@DisplayName("isAnnotation() returns false.")
		void isAnnotation_returnsFalse() {
			boolean isAnnotation = classInformation.isAnnotation();

			assertFalse(isAnnotation);
		}

		@Test
		@DisplayName("isPlainClass() returns false.")
		void isPlainClass_returnsFalse() {
			boolean isPlainClass = classInformation.isPlainClass();

			assertFalse(isPlainClass);
		}

		@Test
		@DisplayName("isEnum() returns true.")
		void isEnum_returnsTrue() {
			boolean isEnum = classInformation.isEnum();

			assertTrue(isEnum);
		}
	}

	@Nested
	@DisplayName("with class of kind annotation")
	class WithClassOfKindAnnotation {
		private final SyntheticClassInformation<?> classInformation =
				newClassBuilder("foo", "Bar", _ -> {}).setClassKind(ANNOTATION).build();

		@Test
		@DisplayName("isInterface() returns false.")
		void isInterface_returnsFalse() {
			boolean isInterface = classInformation.isInterface();

			assertFalse(isInterface);
		}

		@Test
		@DisplayName("isRecord() returns false.")
		void isRecord_returnsFalse() {
			boolean isRecord = classInformation.isRecord();

			assertFalse(isRecord);
		}

		@Test
		@DisplayName("isAnnotation() returns true.")
		void isAnnotation_returnsTrue() {
			boolean isAnnotation = classInformation.isAnnotation();

			assertTrue(isAnnotation);
		}

		@Test
		@DisplayName("isPlainClass() returns false.")
		void isPlainClass_returnsFalse() {
			boolean isPlainClass = classInformation.isPlainClass();

			assertFalse(isPlainClass);
		}

		@Test
		@DisplayName("isEnum() returns false.")
		void isEnum_returnsFalse() {
			boolean isEnum = classInformation.isEnum();

			assertFalse(isEnum);
		}
	}

	@Nested
	@DisplayName("with class of kind record")
	class WithClassOfKindRecord {
		private final SyntheticClassInformation<?> classInformation =
				newClassBuilder("foo", "Bar", _ -> {}).setClassKind(RECORD).build();

		@Test
		@DisplayName("isInterface() returns false.")
		void isInterface_returnsFalse() {
			boolean isInterface = classInformation.isInterface();

			assertFalse(isInterface);
		}

		@Test
		@DisplayName("isRecord() returns true.")
		void isRecord_returnsTrue() {
			boolean isRecord = classInformation.isRecord();

			assertTrue(isRecord);
		}

		@Test
		@DisplayName("isAnnotation() returns false.")
		void isAnnotation_returnsFalse() {
			boolean isAnnotation = classInformation.isAnnotation();

			assertFalse(isAnnotation);
		}

		@Test
		@DisplayName("isPlainClass() returns false.")
		void isPlainClass_returnsFalse() {
			boolean isPlainClass = classInformation.isPlainClass();

			assertFalse(isPlainClass);
		}

		@Test
		@DisplayName("isEnum() returns false.")
		void isEnum_returnsFalse() {
			boolean isEnum = classInformation.isEnum();

			assertFalse(isEnum);
		}
	}

	@Test
	@DisplayName("toString() returns the class name.")
	void toString_returnsClassName() {
		SyntheticClassInformation<?> classInformation = newClassBuilder("foo", "Bar", _ -> {}).build();

		String string = classInformation.toString();

		assertEquals("foo.Bar", string);
	}
}