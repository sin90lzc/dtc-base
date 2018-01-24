package com.dtc.base.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Predicate;

import com.dtc.base.exception.BaseErrorCode;
import com.dtc.base.exception.DTCCommonRuntimeException;

public class MethodWrapper {

	public static final String GETTER_METHOD_PREFIX = "get";

	public static final String BOOLEAN_GETTER_METHOD_PREFIX = "is";

	public static final String SETTER_METHOD_PREFIX = "set";

	private Method wrapMethod;

	private MethodWrapper(Method wrapMethod) {
		Objects.requireNonNull(wrapMethod);
		this.wrapMethod = wrapMethod;
	}

	public boolean isPublic() {
		return Modifier.isPublic(wrapMethod.getModifiers());
	}

	public boolean isStatic() {
		return Modifier.isStatic(wrapMethod.getModifiers());
	}

	public boolean isVoid() {
		return wrapMethod.getReturnType() == Void.TYPE;
	}

	public boolean match(Predicate<Method> predicate) {
		return predicate.test(wrapMethod);
	}

	public int getParameterCount() {
		return wrapMethod.getParameterCount();
	}

	public String getName() {
		return wrapMethod.getName();
	}

	public Class<?> getReturnType() {
		return wrapMethod.getReturnType();
	}

	public Object invoke(Object target, Object... parameters) {
		try {
			return wrapMethod.invoke(target, parameters);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new DTCCommonRuntimeException(BaseErrorCode.REFLECT_METHOD_INVOKE_ERR, e);
		}
	}

	/**
	 * 如果是getter方法，则生成其对应的setter方法名。否则，返回方法全名
	 * 
	 * @return
	 */
	public String getterToSetter() {
		String methodName = wrapMethod.getName();
		if (isGetter()) {
			if (methodName.indexOf(GETTER_METHOD_PREFIX) == 0) {
				return SETTER_METHOD_PREFIX + methodName.substring(GETTER_METHOD_PREFIX.length());
			}
			if (methodName.indexOf(BOOLEAN_GETTER_METHOD_PREFIX) == 0) {
				return BOOLEAN_GETTER_METHOD_PREFIX + methodName.substring(BOOLEAN_GETTER_METHOD_PREFIX.length());
			}
		}
		return methodName;
	}

	/**
	 * 如果是setter方法，则生成其对应的getter方法名。否则，返回方法全名
	 * 
	 * @return
	 */
	public String setterToGetter() {
		String methodName = wrapMethod.getName();
		if (isSetter()) {
			return GETTER_METHOD_PREFIX + methodName.substring(SETTER_METHOD_PREFIX.length());
		}
		return methodName;
	}

	public boolean isGetter() {
		return match(m -> {
			if (isPublic() && !isStatic() && !isVoid() && getParameterCount() == 0) {
				String methodName = m.getName();
				return methodName.indexOf(GETTER_METHOD_PREFIX) == 0
						|| methodName.indexOf(BOOLEAN_GETTER_METHOD_PREFIX) == 0;
			}
			return false;
		});
	}

	public boolean isSetter() {
		return match(m -> {
			if (isPublic() && !isStatic() && isVoid() && getParameterCount() == 1) {
				String methodName = m.getName();
				return methodName.indexOf(SETTER_METHOD_PREFIX) == 0;
			}
			return false;
		});
	}

	public static MethodWrapper wrap(Method method) {
		return new MethodWrapper(method);
	}

}
