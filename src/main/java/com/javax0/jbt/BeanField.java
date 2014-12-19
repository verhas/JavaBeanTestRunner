package com.javax0.jbt;

import java.lang.reflect.InvocationTargetException;

public class BeanField {
	private final Class<?> type;
	private final String name;
	private final BeanMethod getter;
	private final BeanMethod alternateGetter;
	private final BeanMethod setter;

	public BeanField(Class<?> type, String name, BeanMethod getter,
			BeanMethod alternateGetter, BeanMethod setter) {
		this.type = type;
		this.name = name;
		this.getter = getter;
		this.alternateGetter = alternateGetter;
		this.setter = setter;
	}

	public Class<?> getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public BeanMethod getGetter() {
		return getter;
	}

	public BeanMethod getAlternateGetter() {
		return alternateGetter;
	}

	public BeanMethod getSetter() {
		return setter;
	}

	public void set(Object object) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		setter.invoke(object);
	}

	public Object get() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return invokeGet(getter);
	}

	public Object alternateGet() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return invokeGet(alternateGetter);
	}

	private Object invokeGet(BeanMethod method) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return method.invoke();
	}
}
