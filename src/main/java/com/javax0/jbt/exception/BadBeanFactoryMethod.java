package com.javax0.jbt.exception;

import java.lang.reflect.Method;

public class BadBeanFactoryMethod extends JavaBeanFaultyException {
	public BadBeanFactoryMethod(Method method, Throwable cause) {
		super(
				"The method '"
						+ method.getName()
						+ " should return the test bean, should be public and no argument.",
				cause);
	}

	private static final long serialVersionUID = 1L;

}
