package com.javax0.jbt;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

public class BeanFieldsCollectorTest {

	@Bean(value = BeanClass.class, ignore = { "stringField" })
	public static class TestClass {

	}

	public static class BeanClass {
		@SuppressWarnings("unused")
		private String stringField;
	}

	public static class BeanClassWithStaticField {
		@SuppressWarnings("unused")
		private static String staticField;
	}

	@Test
	public void collectsAllDeclaredFields() {
		Collection<String> fields = new BeanFieldsCollector(Object.class,
				BeanClass.class).collect();
		Assert.assertEquals(1, fields.size());
		Assert.assertEquals("stringField", fields.toArray()[0]);
	}

	@Test
	public void ignoresFieldsToBeIgnored() {
		Collection<String> fields = new BeanFieldsCollector(TestClass.class,
				BeanClass.class).collect();
		Assert.assertEquals(0, fields.size());
	}

	@Test
	public void ignoresStaticFields() {
		Collection<String> fields = new BeanFieldsCollector(TestClass.class,
				BeanClassWithStaticField.class).collect();
		Assert.assertEquals(0, fields.size());
	}

}
