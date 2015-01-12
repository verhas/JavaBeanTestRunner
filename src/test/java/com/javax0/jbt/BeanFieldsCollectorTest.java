package com.javax0.jbt;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.javax0.jbt.annotations.Bean;
import com.javax0.jbt.annotations.Ignore;
import com.javax0.jbt.exception.IgnoredFieldDoesNotExist;
import com.javax0.jbt.exception.IgnoredFieldHasWrongType;
import com.javax0.jbt.exception.JavaBeanFaultyException;
import com.javax0.jbt.exception.JavaBeanTestFaultyException;

public class BeanFieldsCollectorTest {

	@Bean(value = BeanClass.class)
	public static class TestClass {
		@Ignore
		private String stringField;
	}

	@Bean(value = BeanClass.class)
	public static class TestClassWrongType {
		@Ignore
		private Object stringField;
	}

	public static class BeanClass {
		@SuppressWarnings("unused")
		private String stringField;
	}

	@Bean(value = BeanClass.class)
	public static class TestClassNoIgnore {
	}

	public static class BeanClassWithFinalField {
		@SuppressWarnings("unused")
		private final String finalField = "";
	}

	public static class BeanClassWithStaticField {
		@SuppressWarnings("unused")
		private static String staticField;
	}

	@Test
	public void collectsAllDeclaredFields() throws JavaBeanFaultyException,
			JavaBeanTestFaultyException {
		Collection<String> fields = new BeanFieldsCollector(Object.class,
				BeanClass.class).map().keySet();
		Assert.assertEquals(1, fields.size());
		Assert.assertEquals("stringField", fields.toArray()[0]);
	}

	@Test
	public void ignoresFieldsToBeIgnored() throws JavaBeanFaultyException,
			JavaBeanTestFaultyException {
		Collection<String> fields = new BeanFieldsCollector(TestClass.class,
				BeanClass.class).map().keySet();
		Assert.assertEquals(0, fields.size());
	}

	@Test
	public void ignoresStaticFields() throws JavaBeanFaultyException,
			JavaBeanTestFaultyException {
		Collection<String> fields = new BeanFieldsCollector(
				TestClassNoIgnore.class, BeanClassWithStaticField.class).map()
				.keySet();
		Assert.assertEquals(0, fields.size());
	}

	@Test
	public void ignoresFinalFields() throws JavaBeanFaultyException,
			JavaBeanTestFaultyException {
		Collection<String> fields = new BeanFieldsCollector(
				TestClassNoIgnore.class, BeanClassWithFinalField.class).map()
				.keySet();
		Assert.assertEquals(0, fields.size());
	}

	@Test(expected = IgnoredFieldDoesNotExist.class)
	public void throwsExceptionWhenIgnoredFieldDoesNotExist()
			throws JavaBeanFaultyException, JavaBeanTestFaultyException {
		Collection<String> fields = new BeanFieldsCollector(TestClass.class,
				BeanClassWithFinalField.class).map().keySet();
		Assert.assertEquals(0, fields.size());
	}

	@Test(expected = IgnoredFieldHasWrongType.class)
	public void throwsExceptionWhenIgnoredFieldDoesMatchType()
			throws JavaBeanFaultyException, JavaBeanTestFaultyException {
		Collection<String> fields = new BeanFieldsCollector(
				TestClassWrongType.class, BeanClass.class).map().keySet();
		Assert.assertEquals(0, fields.size());
	}
}
