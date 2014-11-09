package com.javax0.jbt;

import java.lang.reflect.Method;

public abstract class BeanMethod {
	private final Method method;
	private final Object bean;
	public BeanMethod(Method method, Object bean) {
		super();
		this.method = method;
		this.bean = bean;
	}
}
