package com.javax0.jbt;

import static com.javax0.jbt.Capitalizer.capitalize;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AlternateGetterBuilder extends AbstractGetterSetterBuilder<Getter> {

	public AlternateGetterBuilder(String fieldName, Object bean, Setter setter) {
		super(fieldName, bean, setter);
	}

	protected Method getMethod(String methodName) {
		Field field;
		try {
			field = beanClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e1) {
			return null;
		}
		Class<?> fieldType = field.getType();
		if (fieldType == Boolean.class || fieldType.getName().equals("boolean")) {
			return super.getMethod(methodName);
		} else {
			return null;
		}
	}

	protected Getter newBeanMethod(Method method) {
		return new Getter(setter, method, bean);
	}

	protected String getMethodName(String fieldName) {
		return "is" + capitalize(fieldName);
	}

	@Override
	protected Class<?>[] argumentTypes() {
		return NO_ARGS;
	}
}
