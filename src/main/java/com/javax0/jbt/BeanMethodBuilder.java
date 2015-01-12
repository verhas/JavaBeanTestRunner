package com.javax0.jbt;

import static com.javax0.jbt.Capitalizer.capitalize;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Build an object that represents a bean method (a getter or setter) along with
 * an instance of the bean.
 * <p>
 * The sample use of the class should be like:
 * 
 * <pre>
 * BeanMethod bm = BeanMethodBuilder.getter | alternateGetter
 * 		| setter().forBean(bean);
 * </pre>
 * 
 * @author Peter Verhas <peter@verhas.com>
 *
 */
class BeanMethodBuilder {
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

	/**
	 * Create a builder for a getter for the field named in the argument.
	 * 
	 * @param fieldName
	 * @return
	 */
	static BeanMethodBuilder getter(String fieldName) {
		BeanMethodBuilder bmb = bean("get", fieldName);
		bmb.isSetter = false;
		bmb.fieldTypeShouldBeBoolean = false;
		return bmb;
	}

	/**
	 * Create a builder for an alternate getter (the one that starts with 'is'
	 * in case of a boolean field).
	 * 
	 * @param fieldName
	 * @return
	 */
	static BeanMethodBuilder alternateGetter(String fieldName) {
		BeanMethodBuilder bmb = bean("is", fieldName);
		bmb.isSetter = false;
		bmb.fieldTypeShouldBeBoolean = true;
		return bmb;
	}

	/**
	 * Create a builder for a setter.
	 * 
	 * @param fieldName
	 * @return
	 */
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

	/**
	 * Specify the bean that the bean method should operate on.
	 * 
	 * @param bean
	 *            the instance of the bean that the {@link BeanMethod} should
	 *            work on.
	 * @return the {@link BeanMethod} built.
	 */
	BeanMethod forBean(Object bean) {
		final BeanMethod beanMethod;
		this.beanClass = bean.getClass();
		this.bean = bean;
		if (fieldTypeShouldBeBoolean && isNotBoolean(fieldType())) {
			beanMethod = null;
		} else {
			beanMethod = build();
		}
		return beanMethod;
	}

	private boolean isNotBoolean(Class<?> fieldType) {
		return Boolean.class != fieldType && boolean.class != fieldType;
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

	Method getMethod(String methodName) {
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
