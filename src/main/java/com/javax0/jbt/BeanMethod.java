package com.javax0.jbt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A class that contains a method and an object 'bean' that the method has to
 * work on. The method is a getter or a setter in the application but the
 * implementation does not restrict the use. It could just be any method.
 * 
 * @author Peter Verhas
 *
 */
public class BeanMethod {
	private final Method method;
	private final Object bean;

	public BeanMethod(Method method, Object bean) {
		super();
		this.method = method;
		this.bean = bean;
	}

	public Object getBean() {
		return bean;
	}

	public Object invoke() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return method.invoke(bean, new Object[] {});
	}

	public Object invoke(Object object) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return method.invoke(bean, new Object[] { object });
	}
}
