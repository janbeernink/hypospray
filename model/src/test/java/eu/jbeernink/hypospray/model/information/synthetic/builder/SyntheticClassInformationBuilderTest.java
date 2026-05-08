package eu.jbeernink.hypospray.model.information.synthetic.builder;

import static eu.jbeernink.hypospray.model.information.synthetic.builder.SyntheticPackageInformationBuilder.newPackageBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import jakarta.inject.Named;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.jbeernink.hypospray.model.information.ClassKind;
import eu.jbeernink.hypospray.model.information.reflection.ReflectiveClassInformation;
import eu.jbeernink.hypospray.model.information.synthetic.SyntheticClassInformation;
import eu.jbeernink.hypospray.model.information.synthetic.SyntheticPackageInformation;

@DisplayName("SyntheticClassInformationBuilder")
class SyntheticClassInformationBuilderTest {

	@Test
	@DisplayName(
			"newClassBuilder(String, String, Consumer<PackageBuilder>) creates a class with the expected package information.")
	void newClassBuilder_StringStringConsumer_createsClassWithExpectedPackageInformation() {
		SyntheticClassInformation<?> classInformation = SyntheticClassInformationBuilder.newClassBuilder("foo", "bar",
				packageBuilder -> packageBuilder.addAnnotation(Named.class,
						annotationBuilder -> annotationBuilder.value("name"))).build();

		SyntheticPackageInformation expectedPackageInformation =
				newPackageBuilder("foo").addAnnotation(Named.class, annotationBuilder -> annotationBuilder.value("name"))
				                        .build();
		assertEquals(expectedPackageInformation, classInformation.packageInformation());
	}

	@Test
	@DisplayName("newClassBuilder(String, String, Consumer<PackageBuilder>) creates a class with the expected name.")
	void newClassBuilder_StringStringConsumer_createsClassWithExpectedName() {
		SyntheticClassInformation<?> classInformation =
				SyntheticClassInformationBuilder.newClassBuilder("foo", "bar", _ -> {}).build();

		assertEquals("foo.bar", classInformation.name());
	}

	@Test
	@DisplayName(
			"newClassBuilder(String, String, Consumer<PackageBuilder>) creates a class with the expected simple name.")
	void newClassBuilder_StringStringConsumer_createsClassWithExpectedSimpleName() {
		SyntheticClassInformation<?> classInformation =
				SyntheticClassInformationBuilder.newClassBuilder("foo", "bar", _ -> {}).build();

		assertEquals("bar", classInformation.simpleName());
	}

	@Test
	@DisplayName(
			"addInterface(PackageInformation, String, Consumer<ClassBuilder<?>>) with an interface, adds that interface to the set of super interfaces.")
	void addInterface_StringStringConsumer_withAnInterface_createsClassWithExpectedSuperInterfaces() {
		SyntheticClassInformation<?> classInformation =
				newClassInformationBuilder().addInterface(newPackageBuilder("java.lang").build(), "Runnable",
						builder -> builder.setClassKind(ClassKind.INTERFACE)).build();

		assertEquals(List.of(new ReflectiveClassInformation<>(Runnable.class)),
				classInformation.superInterfaceInformation());
	}

	@Test
	@DisplayName(
			"addInterface(PackageInformation, String, Consumer<ClassBuilder<?>>) with a non-interface class, throws IllegalArgumentException.")
	void addInterface_StringStringConsumer_createsClassWithExpectedSuperInterfaces() {
		SyntheticClassInformationBuilder<Object> classInformationBuilder = newClassInformationBuilder();

		var exception = assertThrows(IllegalArgumentException.class,
				() -> classInformationBuilder.addInterface(newPackageBuilder("java.lang").build(), "Object",
						builder -> builder.setClassKind(ClassKind.RECORD)));

		assertEquals("java.lang.Object is not an interface.", exception.getMessage());
	}

	@Test
	@DisplayName(
			"addInterface(ClassInformation<?>) with an interface, adds that interface to the set of super interfaces.")
	void addInterface_ClassInformation_withAnInterface_createsClassWithExpectedSuperInterfaces() {
		SyntheticClassInformation<?> classInformation =
				newClassInformationBuilder().addInterface(new ReflectiveClassInformation<>(Runnable.class)).build();

		assertEquals(List.of(new ReflectiveClassInformation<>(Runnable.class)),
				classInformation.superInterfaceInformation());
	}

	@Test
	@DisplayName(
			"addInterface(ClassInformation) with a non-interface class, throws IllegalArgumentException.")
	void addInterface_ClassInformation_createsClassWithExpectedSuperInterfaces() {
		SyntheticClassInformationBuilder<Object> classInformationBuilder = newClassInformationBuilder();

		var exception = assertThrows(IllegalArgumentException.class,
				() -> classInformationBuilder.addInterface(new ReflectiveClassInformation<>(Object.class)));

		assertEquals("java.lang.Object is not an interface.", exception.getMessage());
	}

	@ParameterizedTest
	@EnumSource(ClassKind.class)
	@DisplayName("setClassKind({0}) correctly sets the class kind on the constructed SyntheticClassInformation.")
	void setClassKind_setsClassKindOnConstructedClassInformation(ClassKind classKind) {
		SyntheticClassInformation<?> classInformation = newClassInformationBuilder().setClassKind(classKind).build();

		assertEquals(classKind, classInformation.classKind());
	}

	private static @NonNull SyntheticClassInformationBuilder<Object> newClassInformationBuilder() {
		return SyntheticClassInformationBuilder.newClassBuilder("foo", "Bar", _ -> {});
	}

}