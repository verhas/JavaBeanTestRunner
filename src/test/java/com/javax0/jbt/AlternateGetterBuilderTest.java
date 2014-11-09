package com.javax0.jbt;

import org.junit.Assert;
import org.junit.Test;

public class AlternateGetterBuilderTest {
	public static class BeanClass {
		private boolean primitiveField;
		private Boolean objectField;

		public boolean isPrimitiveField() {
			return primitiveField;
		}

		public Boolean isObjectField() {
			return objectField;
		}

		private Object object;
		private int integer;

		public Object isObject() {
			return object;
		}

		public int isInteger() {
			return integer;
		}

		private Boolean objectWithoutAlternateGetter;
		private boolean primitiveWithoutAlternateGetter;

		public Boolean getObjectWithoutAlternateGetter() {
			return objectWithoutAlternateGetter;
		}

		public boolean getPrimitiveWithoutAlternateGetter() {
			return primitiveWithoutAlternateGetter;
		}

	}

	@Test
	public void getsAlternateGetterForPrimitiveField() {
		Getter getter = new AlternateGetterBuilder("primitiveField",
				new BeanClass(), null).build();
		Assert.assertNotNull(getter);
	}

	@Test
	public void getsAlternateGetterForObjectField() {
		Getter getter = new AlternateGetterBuilder("objectField",
				new BeanClass(), null).build();
		Assert.assertNotNull(getter);
	}

	@Test
	public void returnsNullForNonBooleanObjectField() {
		Getter getter = new AlternateGetterBuilder("object", new BeanClass(),
				null).build();
		Assert.assertNull(getter);
	}

	@Test
	public void returnsNullForNonBooleanPrimitiveField() {
		Getter getter = new AlternateGetterBuilder("integer", new BeanClass(),
				null).build();
		Assert.assertNull(getter);
	}

	@Test
	public void returnsNullForBooleanObjectFieldWithoutAlternateGetter() {
		Getter getter = new AlternateGetterBuilder(
				"objectWithoutAlternateGetter", new BeanClass(), null).build();
		Assert.assertNull(getter);
	}

	@Test
	public void returnsNullForBooleanPrimitiveFieldWithoutAlternateGetter() {
		Getter getter = new AlternateGetterBuilder(
				"primitiveWithoutAlternateGetter", new BeanClass(), null)
				.build();
		Assert.assertNull(getter);
	}

}
