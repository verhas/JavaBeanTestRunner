package com.javax0.jbt;

import static com.javax0.jbt.Capitalizer.capitalize;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SetterBuilder extends AbstractGetterSetterBuilder<Setter> {

	public SetterBuilder(String fieldName, Object bean) {
		super(fieldName, bean, null);
	}

	protected String getMethodName(String fieldName) {
		return "set" + capitalize(fieldName);
	}

	protected Setter newBeanMethod(Method method) {
		return new Setter(method, bean);
	}

	@Override
	protected Class<?>[] argumentTypes() {
		Field field;
		try {
			field = beanClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e1) {
			return null;
		}
		return new Class<?>[] { field.getType() };
	}
}
