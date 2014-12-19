package com.javax0.jbt;

import static com.javax0.jbt.Capitalizer.capitalize;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanMethodBuilder {
	private static final Class<?>[] NO_ARGS = new Class<?>[0];
	private Class<?> beanClass;
	private final String fieldName;
	private Object bean;
	private final String prefix;
	private boolean isSetter;
	private boolean fieldTypeShouldBeBoolean;

	private static BeanMethodBuilder bean(String prefix, String fieldName) {
		return new BeanMethodBuilder(prefix, fieldName);
	}

	public static BeanMethodBuilder getter(String fieldName) {
		BeanMethodBuilder bmb = bean("get", fieldName);
		bmb.isSetter = false;
		bmb.fieldTypeShouldBeBoolean = false;
		return bmb;
	}

	public static BeanMethodBuilder alternateGetter(String fieldName) {
		BeanMethodBuilder bmb = bean("is", fieldName);
		bmb.isSetter = false;
		bmb.fieldTypeShouldBeBoolean = true;
		return bmb;
	}

	public static BeanMethodBuilder setter(String fieldName) {
		BeanMethodBuilder bmb = bean("set", fieldName);
		bmb.isSetter = true;
		bmb.fieldTypeShouldBeBoolean = false;
		return bmb;
	}

	private BeanMethodBuilder(String prefix, String fieldName) {
		this.prefix = prefix;
		this.fieldName = fieldName;
	}

	public BeanMethod forBean(Object bean) {
		final BeanMethod beanMethod;
		this.beanClass = bean.getClass();
		this.bean = bean;
		if (fieldTypeShouldBeBoolean
				&& !Boolean.class.isAssignableFrom(fieldType())) {
			beanMethod = null;
		} else {
			beanMethod = build();
		}
		return beanMethod;
	}

	private BeanMethod build() {
		final String methodName = getMethodName(fieldName);
		final Method method = getMethod(methodName);
		final BeanMethod beanMethod;
		if (method == null) {
			beanMethod = null;
		} else {
			beanMethod = new BeanMethod(method, bean);
		}
		return beanMethod;
	}

	protected Method getMethod(String methodName) {
		Method method;
		try {
			method = beanClass.getMethod(methodName, argumentTypes());
		} catch (NoSuchMethodException | SecurityException e) {
			method = null;
		}
		return method;
	}

	private String getMethodName(String fieldName) {
		return prefix + capitalize(fieldName);
	}

	private Class<?>[] argumentTypes() {
		if (isSetter) {
			return setterArgumentTypes();
		} else {
			return getterArgumentTypes();
		}

	}

	private Class<?>[] getterArgumentTypes() {
		return NO_ARGS;
	}

	private Class<?>[] setterArgumentTypes() {
		return new Class<?>[] { fieldType() };
	}

	private Class<?> fieldTypeCalculated = null;

	private Class<?> fieldType() {
		if (fieldTypeCalculated == null) {
			Field field;
			try {
				field = beanClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException | SecurityException e1) {
				return null;
			}
			fieldTypeCalculated = field.getType();
		}
		return fieldTypeCalculated;
	}
}
