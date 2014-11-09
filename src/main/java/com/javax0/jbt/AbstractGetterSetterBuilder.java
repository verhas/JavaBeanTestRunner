package com.javax0.jbt;

import java.lang.reflect.Method;

public abstract class AbstractGetterSetterBuilder<BEAN_METHOD extends BeanMethod> {
	protected static final Class<?>[] NO_ARGS = new Class<?>[0];
	protected final Class<?> beanClass;
	protected final String fieldName;
	protected final Object bean;
	protected final Setter setter;

	public AbstractGetterSetterBuilder(String fieldName, Object bean,
			Setter setter) {
		super();
		this.beanClass = bean.getClass();
		this.fieldName = fieldName;
		this.bean = bean;
		this.setter = setter;
	}

	@SuppressWarnings("unchecked")
	public BEAN_METHOD build() {
		final String methodName = getMethodName(fieldName);
		final Method method = getMethod(methodName);
		final BeanMethod beanMethod;
		if (method == null) {
			beanMethod = null;
		} else {
			beanMethod = newBeanMethod(method);
		}
		return (BEAN_METHOD)beanMethod;
	}

	protected abstract BeanMethod newBeanMethod(Method method);
	
	protected Method getMethod(String methodName) {
		Method getterMethod;
		try {
			getterMethod = beanClass.getMethod(methodName, argumentTypes());
		} catch (NoSuchMethodException | SecurityException e) {
			getterMethod = null;
		}
		return getterMethod;
	}

	abstract protected String getMethodName(String fieldName);

	abstract protected Class<?>[] argumentTypes();
}
