package com.javax0.jbt;

import static com.javax0.jbt.BeanMethodBuilder.alternateGetter;
import static com.javax0.jbt.BeanMethodBuilder.getter;
import static com.javax0.jbt.BeanMethodBuilder.setter;

import java.lang.reflect.Field;

public class BeanFieldBuilder {
	private final String fieldName;

	public static BeanFieldBuilder field(String fieldName) {
		return new BeanFieldBuilder(fieldName);
	}

	public BeanFieldBuilder(String fieldName) {
		super();
		this.fieldName = fieldName;
	}

	public BeanField forBean(Object bean) {
		BeanMethod getter = getter(fieldName).forBean(bean);
		BeanMethod alternateGetter = alternateGetter(fieldName).forBean(bean);
		BeanMethod setter = setter(fieldName).forBean(bean);
		Class<?> fieldType;
		try {
			Class<?> beanClass = bean.getClass();
			Field field = beanClass.getDeclaredField(fieldName);
			fieldType = field.getType();
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException("SNAFU", e);
		}
		return new BeanField(fieldType, fieldName, getter, alternateGetter,
				setter);
	}

}
