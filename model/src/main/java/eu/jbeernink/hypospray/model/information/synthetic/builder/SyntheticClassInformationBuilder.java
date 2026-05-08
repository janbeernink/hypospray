package eu.jbeernink.hypospray.model.information.synthetic.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import jakarta.annotation.Nullable;

import eu.jbeernink.hypospray.model.information.ClassInformation;
import eu.jbeernink.hypospray.model.information.ClassKind;
import eu.jbeernink.hypospray.model.information.PackageInformation;
import eu.jbeernink.hypospray.model.information.builder.ClassBuilder;
import eu.jbeernink.hypospray.model.information.builder.PackageBuilder;
import eu.jbeernink.hypospray.model.information.synthetic.SyntheticClassInformation;

public final class SyntheticClassInformationBuilder<T> implements ClassBuilder<T, SyntheticClassInformationBuilder<T>> {

	private final PackageInformation packageInformation;
	private final String className;

	private @Nullable ClassInformation<? super T> superClass;
	private final List<ClassInformation<?>> superInterfaces = new ArrayList<>();
	private ClassKind classKind = ClassKind.PLAIN_CLASS;

	private SyntheticClassInformationBuilder(PackageInformation packageInformation, String className) {
		this.packageInformation = packageInformation;
		this.className = className;
	}

	@Override
	public SyntheticClassInformationBuilder<T> withSuperclass(PackageInformation packageInformation,
	                                                          String superclassName,
	                                                          Consumer<ClassBuilder<?, ?>> superClassBuilder) {
		var superClassbuilder = newClassBuilder(packageInformation, superclassName);
		superClassBuilder.accept(superClassbuilder);
		return withSuperclass(superClassbuilder.build());
	}

	@Override
	public SyntheticClassInformationBuilder<T> withSuperclass(String qualifiedSuperclassName,
	                                                          Consumer<ClassBuilder<?, ?>> superClassBuilderConsumer) {
		int classNameSeparator = qualifiedSuperclassName.lastIndexOf(".");
		// TODO handle missing dot.
		var packageName = qualifiedSuperclassName.substring(0, classNameSeparator);
		var className = qualifiedSuperclassName.substring(classNameSeparator + 1);

		PackageInformation packageInformation = SyntheticPackageInformationBuilder.newPackageBuilder(packageName).build();

		SyntheticClassInformationBuilder<?> superClassBuilder2 = newClassBuilder(packageInformation, className);
		superClassBuilderConsumer.accept(superClassBuilder2);

		@SuppressWarnings("unchecked")
		ClassInformation<? super T> superClass = (ClassInformation<? super T>) superClassBuilder2.build();

		return withSuperclass(superClass);
	}

	@Override
	public SyntheticClassInformationBuilder<T> withSuperclass(ClassInformation<? super T> superClass) {
		this.superClass = superClass;
		return this;
	}

	@Override
	public SyntheticClassInformationBuilder<T> setClassKind(ClassKind classKind) {
		this.classKind = classKind;
		return this;
	}

	@Override
	public SyntheticClassInformationBuilder<T> addInterface(PackageInformation packageInformation, String className,
	                                                        Consumer<ClassBuilder<?, ?>> interfaceBuilder) {
		SyntheticClassInformationBuilder<?> builder =
				newClassBuilder(packageInformation, className);

		interfaceBuilder.accept(builder);

		return addInterface(builder.build());
	}

	@Override
	public SyntheticClassInformationBuilder<T> addInterface(ClassInformation<?> superInterface) {
		if (!superInterface.isInterface()) {
			throw new IllegalArgumentException("%s is not an interface.".formatted(superInterface));
		}

		superInterfaces.add(superInterface);
		return this;
	}

	public SyntheticClassInformation<T> build() {
		return new SyntheticClassInformation<>(packageInformation, className, superClass, superInterfaces, classKind);
	}

	public static <T> SyntheticClassInformationBuilder<T> newClassBuilder(PackageInformation packageInformation,
	                                                                      String className) {
		return new SyntheticClassInformationBuilder<>(packageInformation, className);
	}

	public static <T> SyntheticClassInformationBuilder<T> newClassBuilder(String packageName, String className,
	                                                                      Consumer<PackageBuilder<?>> packageBuilderConsumer) {
		var packageBuilder = SyntheticPackageInformationBuilder.newPackageBuilder(packageName);
		packageBuilderConsumer.accept(packageBuilder);
		return newClassBuilder(packageBuilder.build(), className);
	}
}
