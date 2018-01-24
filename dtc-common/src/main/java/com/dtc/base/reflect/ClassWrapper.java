package com.dtc.base.reflect;

import java.lang.reflect.Method;
import java.util.Objects;

import com.dtc.base.exception.BaseErrorCode;
import com.dtc.base.exception.DTCCommonRuntimeException;

public class ClassWrapper<T> {

	private Class<T> classToWrap;

	private ClassWrapper(Class<T> classToWrappe) {
		Objects.requireNonNull(classToWrappe);
		this.classToWrap = classToWrappe;
	}

	public T newInstance() {
		try {
			return classToWrap.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new DTCCommonRuntimeException(BaseErrorCode.REFLECT_CLASS_NEW_INSTANCE_ERR, e);
		}
	}

	public void accept(ClassVistor vistor) {
		Method[] methods = classToWrap.getMethods();
		for (Method method : methods) {
			vistor.visteMethod(MethodWrapper.wrap(method));
		}
	}

	public MethodWrapper getMethod(String methodName, Class<?>... parameterTypes) {
		try {
			return MethodWrapper.wrap(classToWrap.getMethod(methodName, parameterTypes));
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	public boolean isAssignableFrom(ClassWrapper<?> classWrapper) {
		if (this.classToWrap != classWrapper.classToWrap) {
			return this.classToWrap.isAssignableFrom(classWrapper.classToWrap);
		}
		return true;

	}

	public static <T> ClassWrapper<T> wrap(Class<T> classToWrappe) {
		return new ClassWrapper<T>(classToWrappe);
	}

}
