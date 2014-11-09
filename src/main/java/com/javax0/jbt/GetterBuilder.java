package com.javax0.jbt;

import static com.javax0.jbt.Capitalizer.capitalize;

import java.lang.reflect.Method;


public class GetterBuilder extends AbstractGetterSetterBuilder<Getter> {

	public GetterBuilder(String fieldName, Object bean,
			Setter setter) {
		super(fieldName, bean, setter);
	}
	protected String getMethodName(String fieldName) {
		return "get" + capitalize(fieldName);
	}
	
	protected Getter newBeanMethod(Method method){
		return new Getter(setter, method, bean);
	}
	
	@Override
	protected Class<?>[] argumentTypes() {
		return NO_ARGS;
	}

}
