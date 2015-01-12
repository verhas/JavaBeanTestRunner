package com.javax0.jbt.testedbeans;

import com.javax0.jbt.annotations.Bean;
import com.javax0.jbt.annotations.BeanFactory;

@Bean(NoDefaultConstructorBean.class)
public class TestWithFactoryMethod implements FakeTestClass {
	private static final int JUST_ANY_INT = 0;

	@BeanFactory
	public NoDefaultConstructorBean factory() {
		return new NoDefaultConstructorBean(JUST_ANY_INT);
	}

}
