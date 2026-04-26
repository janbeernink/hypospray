package eu.jbeernink.hypospray.model.information.synthetic.builder;

import java.util.function.Consumer;

import jakarta.annotation.Nullable;

import eu.jbeernink.hypospray.model.information.ClassInformation;
import eu.jbeernink.hypospray.model.information.PackageInformation;
import eu.jbeernink.hypospray.model.information.builder.ClassBuilder;
import eu.jbeernink.hypospray.model.information.builder.PackageBuilder;
import eu.jbeernink.hypospray.model.information.synthetic.SyntheticClassInformation;

public final class SyntheticClassInformationBuilder<T> implements ClassBuilder<T, SyntheticClassInformationBuilder<T>> {

	private final PackageInformation packageInformation;
	private final String className;

	private @Nullable ClassInformation<? super T> superClass;

	private SyntheticClassInformationBuilder(PackageInformation packageInformation, String className) {
		this.packageInformation = packageInformation;
		this.className = className;
	}

	@Override
	public SyntheticClassInformationBuilder<T> withSuperclass(PackageInformation packageInformation, String superclassName,
	                                                       Consumer<ClassBuilder<?, ?>> superClassBuilder) {
		var superClassbuilder = newClassBuilder(packageInformation, superclassName);
		superClassBuilder.accept(superClassbuilder);
		return withSuperclass(superClassbuilder.build());
	}

	@Override
	public SyntheticClassInformationBuilder<T> withSuperclass(String qualifiedSuperclassName,
	                                                       Consumer<ClassBuilder<?, ?>> superClassBuilder) {
		int classNameSeparator = qualifiedSuperclassName.lastIndexOf(".");
		// TODO handle missing dot.
		var packageName = qualifiedSuperclassName.substring(0, classNameSeparator);
		var className = qualifiedSuperclassName.substring(classNameSeparator + 1);

		PackageInformation packageInformation =
				SyntheticPackageInformationBuilder.newPackageBuilder(packageName).build();

		SyntheticClassInformationBuilder<?> superClassBuilder2 =
				newClassBuilder(packageInformation, className);
		superClassBuilder.accept(superClassBuilder2);

		return withSuperclass((ClassInformation<? super T>) superClassBuilder2.build());
	}

	@Override
	public SyntheticClassInformationBuilder<T> withSuperclass(ClassInformation<? super T> superClass) {
		this.superClass = superClass;
		return this;
	}

	public SyntheticClassInformation<T> build() {
		return new SyntheticClassInformation<>(packageInformation, className, superClass);
	}

	public static <T> SyntheticClassInformationBuilder<T> newClassBuilder(PackageInformation packageInformation, String className) {
		return new SyntheticClassInformationBuilder<>(packageInformation, className);
	}

	public static <T> SyntheticClassInformationBuilder<T> newClassBuilder(String packageName, String className,
	                                                               Consumer<PackageBuilder<?>> packageBuilderConsumer) {
		var packageBuilder = SyntheticPackageInformationBuilder.newPackageBuilder(packageName);
		packageBuilderConsumer.accept(packageBuilder);
		return newClassBuilder(packageBuilder.build(), className);
	}
}
