package com.dtc.base.converter;

public interface BeanConverter<T> {
	
	T convert(Object srcBean);
	
	boolean isConvertable(Object srcBean);
	
}

