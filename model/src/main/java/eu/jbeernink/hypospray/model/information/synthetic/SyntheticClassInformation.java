package eu.jbeernink.hypospray.model.information.synthetic;

import java.util.List;

import org.jspecify.annotations.Nullable;

import eu.jbeernink.hypospray.model.information.AnnotationInformation;
import eu.jbeernink.hypospray.model.information.ClassInformation;
import eu.jbeernink.hypospray.model.information.ConstructorInformation;
import eu.jbeernink.hypospray.model.information.FieldInformation;
import eu.jbeernink.hypospray.model.information.MethodInformation;
import eu.jbeernink.hypospray.model.information.PackageInformation;
import eu.jbeernink.hypospray.model.information.RecordComponentInformation;
import eu.jbeernink.hypospray.model.types.TypeInstance;
import eu.jbeernink.hypospray.model.types.TypeVariableInstance;

public record SyntheticClassInformation<T>(PackageInformation packageInformation, String simpleName, ClassInformation<? super T> superClassDeclaration) implements ClassInformation<T> {

	@Override
	public Class<T> classInstance() {
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@Override
	public List<TypeVariableInstance> typeParameterInstances() {
		return List.of();
	}

	@Override
	public @Nullable TypeInstance superClass() {
		return null;
	}

	@Override
	public boolean isEnum() {
		return false;
	}

	@Override
	public boolean isAnnotation() {
		return false;
	}

	@Override
	public boolean isRecord() {
		return false;
	}

	@Override
	public int modifiers() {
		return 0;
	}

	@Override
	public List<TypeInstance> superInterfaceTypes() {
		return List.of();
	}

	@Override
	public List<ClassInformation<?>> superInterfaceInformation() {
		return List.of();
	}

	@Override
	public String name() {
		return packageInformation().name() + "." + simpleName;
	}

	@Override
	public @Nullable PackageInformation packageInfo() {
		return null;
	}

	@Override
	public List<MethodInformation> methodInformation() {
		return List.of();
	}

	@Override
	public List<MethodInformation> allMethods() {
		return List.of();
	}

	@Override
	public List<ConstructorInformation<T>> constructorInformation() {
		return List.of();
	}

	@Override
	public List<FieldInformation> fieldInformation() {
		return List.of();
	}

	@Override
	public List<RecordComponentInformation<T>> recordComponentInformation() {
		return List.of();
	}

	@Override
	public TypeInstance asType() {
		return null;
	}

	@Override
	public List<AnnotationInformation> annotationInformation() {
		return List.of();
	}
}
