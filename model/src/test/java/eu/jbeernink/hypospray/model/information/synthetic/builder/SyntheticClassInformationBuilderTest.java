package eu.jbeernink.hypospray.model.information.synthetic.builder;

import static eu.jbeernink.hypospray.model.information.synthetic.builder.SyntheticPackageInformationBuilder.newPackageBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.inject.Named;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.jbeernink.hypospray.model.information.synthetic.SyntheticClassInformation;
import eu.jbeernink.hypospray.model.information.synthetic.SyntheticPackageInformation;

@DisplayName("SyntheticClassInformationBuilder")
class SyntheticClassInformationBuilderTest {

	@Test
	@DisplayName("newClassBuilder(String, String, Consumer<PackageBuilder>) creates a class with the expected package information.")
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
		SyntheticClassInformation<?> classInformation = SyntheticClassInformationBuilder.newClassBuilder("foo", "bar", _ -> {}).build();

		assertEquals("foo.bar", classInformation.name());
	}

	@Test
	@DisplayName("newClassBuilder(String, String, Consumer<PackageBuilder>) creates a class with the expected simple name.")
	void newClassBuilder_StringStringConsumer_createsClassWithExpectedSimpleName() {
		SyntheticClassInformation<?> classInformation = SyntheticClassInformationBuilder.newClassBuilder("foo", "bar", _ -> {}).build();

		assertEquals("bar", classInformation.simpleName());
	}

}