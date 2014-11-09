package com.javax0.jbt;

import java.lang.reflect.Method;

public class Getter extends BeanMethod {
	private final Setter setter;

	public Setter getSetter() {
		return setter;
	}

	public Getter(Setter setter, Method getterMethod, Object bean) {
		super(getterMethod, bean);
		this.setter = setter;
	}

}
