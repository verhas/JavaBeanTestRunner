package com.javax0.jbt;

import org.junit.Assert;
import org.junit.Test;

import com.javax0.jbt.annotations.Bean;

public class BeanClassCalculatorTest {

	public static class NonBean {
	}

	@Bean(BeanClass.class)
	public static class BeanClass {
	}

	public static class BeanClassTest {
	}

	public static class NonExistingClassTest {
	}

	@Test
	public void calculatesClassFromTestClassNameRemovingTest() {
		final Class<?> actual = new BeanClassCalculator(BeanClassTest.class)
				.calculate();
		Assert.assertEquals(BeanClass.class, actual);
	}

	@Test
	public void calculatesNullFromNonExistingClassTest() {
		final Class<?> actual = new BeanClassCalculator(
				NonExistingClassTest.class).calculate();
		Assert.assertNull(actual);
	}

	@Test
	public void calculatesNullFromNonAnnotatedNonTestClass() {
		final Class<?> actual = new BeanClassCalculator(NonBean.class)
				.calculate();
		Assert.assertNull(actual);
	}

	@Test
	public void calculatesClassFromTestClassAnnotation() {
		final Class<?> actual = new BeanClassCalculator(BeanClass.class)
				.calculate();
		Assert.assertEquals(BeanClass.class, actual);
	}
}
