package com.dtc.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.dtc.base.reflect.ClassVistor;
import com.dtc.base.reflect.ClassWrapper;
import com.dtc.base.reflect.MethodWrapper;

/**
 * Bean复制工具类
 * 
 * 完成从srcBean到target对象的复制，通过getter，setter方法进行复制。
 * 如果srcBean和target没有对应的getter/setter方法(类型必须要严格一致)，，是不会进行数据复制的。
 * 
 * @author tim
 *
 */
public class BeanCopier {

	/**
	 * 将srcBean的数据复制至target
	 * 
	 * @param srcBean
	 * @param target
	 * @return
	 */
	public static <T> T copy(Object srcBean, T target) {
		return copy(srcBean, target, null);
	}
	


	/**
	 * 将srcBean的数据复制至由targetClass生成的实例对象中，targetClass必须带有无参构造函数
	 * 
	 * @param srcBean
	 * @param targetClass
	 * @return
	 */
	public static <T> T copy(Object srcBean, Class<T> targetClass) {
		Objects.requireNonNull(targetClass);
		ClassWrapper<T> classWrapper = ClassWrapper.wrap(targetClass);
		T target = classWrapper.newInstance();
		return copy(srcBean, target);
	}

	/**
	 * 按srcBeans的数组顺序复制数据至target,ajustFunction提供了一个在复制完成后，可对target进行调整的机会
	 * 
	 * @param srcBeans
	 * @param target
	 * @param ajustFunction
	 *            可对target进行调整的函数
	 * @return
	 */
	public static <T> T copy(Object srcBeans, T target, Function<T, T> ajustFunction) {
		T ret = target;
		if(srcBeans.getClass().isArray()) {
			Object[] sb=(Object[])srcBeans;
			if (srcBeans != null) {
				for (Object srcBean : sb) {
					ret = BeanCopier.copyOne(srcBean, ret);
				}
			}
		}else {
			ret=BeanCopier.copyOne(srcBeans, target);
		}
		final T fret=ret;
		return Optional.ofNullable(ajustFunction).map(f -> f.apply(fret)).orElse(fret);
	}

	/**
	 * 按srcBeans的数组顺序复制数据至由targetClass生成的实例, targetClass必须带有无参构造函数，
	 * ajustFunction提供了一个在复制完成后，可对target进行调整的机会
	 * 
	 * @param srcBeans
	 * @param targetClass
	 * @param ajustFunction
	 *            可对target进行调整的函数
	 * @return
	 */
	public static <T> T copy(Object srcBeans, Class<T> targetClass, Function<T, T> ajustFunction) {
		ClassWrapper<T> classWrapper = ClassWrapper.wrap(targetClass);
		T target = classWrapper.newInstance();
		return copy(srcBeans, target, ajustFunction);
	}
	
	
	private static <T> T copyOne(Object srcBean, T target) {
		return Optional.ofNullable(target).map(t -> {
			new BeanCopyVistor(srcBean, t);
			return t;
		}).orElse(null);
	}

	/**
	 * 使用访问者模式，去访问{@link Class}对象的{@link Field}或{@link Method}， 在访问过程中完成Bean的复制
	 * 
	 * @author tim
	 *
	 */
	private static class BeanCopyVistor implements ClassVistor {

		private Object target;

		private Object src;

		private ClassWrapper<?> targetWrapper;

		private ClassWrapper<?> srcWrapper;

		private BeanCopyVistor(Object src, Object target) {
			Objects.requireNonNull(target);
			Objects.requireNonNull(src);
			this.target = target;
			this.targetWrapper = ClassWrapper.wrap(target.getClass());
			this.srcWrapper = ClassWrapper.wrap(src.getClass());
			this.src = src;
			srcWrapper.accept(this);
		}

		@Override
		public void visteMethod(MethodWrapper methodWrapper) {
			if (methodWrapper.isGetter()) {
				Object getterResult = methodWrapper.invoke(this.src, new Object[0]);
				String setterMethodName = methodWrapper.getterToSetter();
				MethodWrapper setterMethod = this.targetWrapper.getMethod(setterMethodName,
						methodWrapper.getReturnType());
				if (setterMethod != null) {
					setterMethod.invoke(this.target, getterResult);
				}
			}
		}
	}

}
