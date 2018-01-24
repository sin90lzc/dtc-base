package com.dtc.base;

import java.lang.reflect.Field;

import com.dtc.base.exception.BaseErrorCode;
import com.dtc.base.exception.DTCCommonRuntimeException;

public class UnsafeWrapper {

	@SuppressWarnings("restriction")
	private static sun.misc.Unsafe UNSAFE = getUnsafe();
	
	@SuppressWarnings("restriction")
	private static sun.misc.Unsafe getUnsafe() {
		if(UNSAFE!=null) {
			return UNSAFE;
		}
		try {
			Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			UNSAFE = (sun.misc.Unsafe) f.get(null);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new DTCCommonRuntimeException(BaseErrorCode.CLASS_FIELD_RESOLVE_ERR,e);
		}
		return UNSAFE;
	}
	
	@SuppressWarnings({ "restriction", "unchecked" })
	public static <T> T newInstance(Class<T> clazz) {
		try {
			return (T)UNSAFE.allocateInstance(clazz);
		} catch (InstantiationException e) {
			throw new DTCCommonRuntimeException(BaseErrorCode.CLASS_FIELD_RESOLVE_ERR,e);
		}
	}
	
	
	
	
}
