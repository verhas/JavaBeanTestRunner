package com.javax0.jbt;

import static com.javax0.jbt.BeanFieldBuilder.field;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.javax0.jbt.testedbeans.PrimitiveFields;

public class BeanFieldTest {

	@Test
	public void byteFieldCanBeSetDirectly() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BeanField beanField = field("byteField").forBean(new PrimitiveFields());
		beanField.set(Byte.valueOf((byte)53));
	}
}
