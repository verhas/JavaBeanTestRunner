package com.javax0.jbt;

import org.junit.Assert;
import org.junit.Test;

public class BeanClassCalculatorTest {

	public static class NonBean {
	}

	@Bean(BeanClass.class)
	public static class BeanClass {
	}

	public static class BeanClassTest {
	}

	@Test
	public void calculatesClassFromTestClassNameRemovingTest() {
		final Class<?> actual = new BeanClassCalculator(BeanClassTest.class)
				.calculate();
		Assert.assertEquals(BeanClass.class, actual);
	}

	public void calculatesClassFromTestClassAnnotation() {
		final Class<?> actual = new BeanClassCalculator(BeanClass.class)
				.calculate();
		Assert.assertEquals(BeanClass.class, actual);
	}

	public void returnNullIfCanNotBeCalculated() {

		final Class<?> actual = new BeanClassCalculator(NonBean.class)
				.calculate();
		Assert.assertEquals(null, actual);
	}
}
