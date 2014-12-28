package com.javax0.jbt;

import java.lang.reflect.Field;
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

	public boolean hasGetter() {
		return getGetter() != null;
	}

	public BeanMethod getAlternateGetter() {
		return alternateGetter;
	}

	public boolean hasAlternateGetter() {
		return getAlternateGetter() != null;
	}

	public boolean hasSetter() {
		return getSetter() != null;
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

	public Object directGet() throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		final Object object = getBean();
		if (object != null) {
			Field field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(object);
		} else {
			return null;
		}

	}

	public void directSet(Object value) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		final Object object = getBean();
		if (object != null) {
			Field field = getDeclaredFieldAccessible(object);
			field.set(object, value);
		}
	}

	private Field getDeclaredFieldAccessible(final Object object)
			throws NoSuchFieldException {
		Field field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}

	private Object getBean() {
		final Object object;
		if (setter != null) {
			object = setter.getBean();
		} else {
			if (getter != null) {
				object = getter.getBean();
			} else {
				object = null;
			}
		}
		return object;
	}
}
