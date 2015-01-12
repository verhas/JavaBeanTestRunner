package com.javax0.jbt;

public class BeanFactory {
	private final Class<?> testClass;
	private final Class<?> beanClass;

	public BeanFactory(Class<?> testClass, Class<?> beanClass) {
		this.testClass = testClass;
		this.beanClass = beanClass;
	}

	public Object getBean() throws InstantiationException, IllegalAccessException {
			return  beanClass.newInstance();
	}
}
