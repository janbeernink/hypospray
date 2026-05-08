package eu.jbeernink.hypospray.model.information.builder;

import java.util.function.Consumer;

import eu.jbeernink.hypospray.model.information.ClassInformation;
import eu.jbeernink.hypospray.model.information.ClassKind;
import eu.jbeernink.hypospray.model.information.PackageInformation;

/// A builder for creating [ClassInformation] instances.
public interface ClassBuilder<T, B extends ClassBuilder<T, B>> {

	/// Set the superclass of the resulting [ClassInformation].
	B withSuperclass(PackageInformation packageInformation, String superclassName, Consumer<ClassBuilder<?, ?>> superClassBuilder);

	B withSuperclass(String qualifiedSuperclassName, Consumer<ClassBuilder<?, ?>> superClassBuilder);

	B withSuperclass(ClassInformation<? super T> superClass);

	/// Sets the kind of class the resulting [ClassInformation] will have.
	B setClassKind(ClassKind classKind);

	B addInterface(PackageInformation packageInformation, String className,
	                                                 Consumer<ClassBuilder<?, ?>> interfaceBuilder);

	B addInterface(ClassInformation<?> superInterface);
}
