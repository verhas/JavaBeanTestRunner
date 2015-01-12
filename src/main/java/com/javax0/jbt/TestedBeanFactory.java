package com.javax0.jbt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.javax0.jbt.exception.BadBeanFactoryMethod;

public class TestedBeanFactory {
	private final Class<?> testClass;
	private final Object testObject;
	private final Class<?> beanClass;

	public TestedBeanFactory(Object testObject, Class<?> beanClass) {
		this.testObject = testObject;
		this.testClass = testObject.getClass();
		this.beanClass = beanClass;
	}

	public Object getBean() throws InstantiationException,
			IllegalAccessException, BadBeanFactoryMethod {
		for (Method method : testClass.getDeclaredMethods()) {
			if (method
					.getAnnotation(com.javax0.jbt.annotations.BeanFactory.class) != null) {
				try {
					return method.invoke(testObject, new Object[0]);
				} catch (IllegalArgumentException | InvocationTargetException e) {
					throw new BadBeanFactoryMethod(method, e);
				}
			}
		}
		return beanClass.newInstance();
	}
}
