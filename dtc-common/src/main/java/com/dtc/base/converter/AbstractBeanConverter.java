package com.dtc.base.converter;

import java.lang.reflect.ParameterizedType;

import com.dtc.base.reflect.ClassWrapper;

public abstract class AbstractBeanConverter<T> implements BeanConverter<T> {

	private ClassWrapper<T> targetClass;

	@SuppressWarnings("unchecked")
	public AbstractBeanConverter() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		targetClass = ClassWrapper.wrap((Class<T>) pt.getActualTypeArguments()[0]);
	}

	@Override
	public final T convert(Object srcBean) {
		if (srcBean == null) {
			return null;
		}
		if (isConvertable(srcBean)) {
			return doConvert(srcBean);
		}
		return null;
	}

	public abstract T doConvert(Object srcBean);

	@Override
	public boolean isConvertable(Object srcBean) {
		if (srcBean == null) {
			return true;
		}
		ClassWrapper<? extends Object> srcClass = ClassWrapper.wrap(srcBean.getClass());
		return targetClass.isAssignableFrom(srcClass);
	}

}
