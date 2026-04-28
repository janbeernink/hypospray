package eu.jbeernink.hypospray.model.information.synthetic;

import java.util.List;

import org.jspecify.annotations.Nullable;

import eu.jbeernink.hypospray.model.TypeFactory;
import eu.jbeernink.hypospray.model.information.AnnotationInformation;
import eu.jbeernink.hypospray.model.information.ClassInformation;
import eu.jbeernink.hypospray.model.information.ConstructorInformation;
import eu.jbeernink.hypospray.model.information.FieldInformation;
import eu.jbeernink.hypospray.model.information.MethodInformation;
import eu.jbeernink.hypospray.model.information.PackageInformation;
import eu.jbeernink.hypospray.model.information.RecordComponentInformation;
import eu.jbeernink.hypospray.model.types.TypeInstance;
import eu.jbeernink.hypospray.model.types.TypeVariableInstance;

public record SyntheticClassInformation<T>(PackageInformation packageInformation, String simpleName,
                                           @Nullable ClassInformation<? super T> superClassDeclaration) implements
		ClassInformation<T> {

	@Override
	public Class<T> classInstance() {
		String name = name();
		try {
			@SuppressWarnings("unchecked") Class<T> classInstance = (Class<T>) Class.forName(name);
			return classInstance;
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Unable to load class: %s.".formatted(name), e);
		}
	}

	@Override
	public List<TypeVariableInstance> typeParameterInstances() {
		return List.of();
	}

	@Override
	public @Nullable TypeInstance superClass() {
		if (superClassDeclaration == null) {
			return null;
		}
		return superClassDeclaration.asType();
	}

	@Override
	public boolean isEnum() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public boolean isAnnotation() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public boolean isRecord() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public int modifiers() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public List<TypeInstance> superInterfaceTypes() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public List<ClassInformation<?>> superInterfaceInformation() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public String name() {
		return packageInformation().name() + "." + simpleName;
	}

	@Override
	public @Nullable PackageInformation packageInfo() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public List<MethodInformation> methodInformation() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public List<MethodInformation> allMethods() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public List<ConstructorInformation<T>> constructorInformation() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public List<FieldInformation> fieldInformation() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public List<RecordComponentInformation<T>> recordComponentInformation() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public TypeInstance asType() {
		return TypeFactory.getInstance().ofClass(this);
	}

	@Override
	public List<AnnotationInformation> annotationInformation() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
}
