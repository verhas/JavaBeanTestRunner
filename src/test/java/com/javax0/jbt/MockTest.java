package com.javax0.jbt;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Test;

public class MockTest {

	private static final ClassCollector collector = new ClassCollector();

	@Test
	public void givesNullForRealObjects() {
		for (Class<?> klass : collector.getClasses()) {
			Object nullValue = Mock.nullForClass(klass);
			assertNull(nullValue);
		}
	}

	@Test
	public void givesObjectForRealObjects() {
		for (Class<?> klass : collector.getClasses()) {
			if (notMockitoClass(klass)) {
				Object value = Mock.forClass(klass);
				Assert.assertTrue(klass.isAssignableFrom(value.getClass()));
			}
		}
	}

	private boolean notMockitoClass(Class<?> klass) {
		final String className = klass.getCanonicalName();
		return !(className.startsWith("org.mockito.") || className
				.contains("EnhancerByMockitoWith"));
	}

	@Test
	public void givesNonNullForEnums() {
		for (Class<?> klass : collector.getEnums()) {
			Object pseudoNullValue = Mock.nullForClass(klass);
			assertNotNull(pseudoNullValue);
		}
	}

	private static final Class<?>[] PRIMITIVES = new Class<?>[] { byte.class,
			short.class, int.class, long.class, char.class, boolean.class,
			float.class, double.class };

	private static final Class<?>[] BOXING_CLASSES = new Class<?>[] {
			Byte.class, Short.class, Integer.class, Long.class,
			Character.class, Boolean.class, Float.class, Double.class };

	@Test
	public void givesNonNullForPrimitives() {
		for (Class<?> klass : PRIMITIVES) {
			Object nullValue = Mock.nullForClass(klass);
			assertNotNull(nullValue);
		}
	}

	@Test
	public void givesSamplesForPrimitives() {
		for (Class<?> klass : PRIMITIVES) {
			Object value = Mock.forClass(klass);
			Assert.assertNotNull(value);
		}
	}

	@Test
	public void givesSamplesForTheObjectPairsOfPrimitives()
			throws InstantiationException, IllegalAccessException {
		for (Class<?> klass : BOXING_CLASSES) {
			Object value = Mock.forClass(klass);
			Assert.assertNotNull(value);
			Assert.assertEquals(klass, value.getClass());
		}
	}

	enum A {
		q, p
	}

	@Test
	public void givesSamplesForEnum() {
		Object value = Mock.forClass(A.class);
		Assert.assertEquals(value, A.q);
	}

	final class Final {
	}

	@Test(expected = Exception.class)
	public void throwsExceptionForFinalClass() {
		Mock.forClass(Final.class);
	}

}
