package com.javax0.jbt;

import org.junit.Assert;
import org.junit.Test;

public class GetterBuilderTest {
	@SuppressWarnings("unused")
	public static class BeanClass {
		private int primitiveField;
		private Integer objectField;

		public int getPrimitiveField() {
			return primitiveField;
		}

		public Integer getObjectField() {
			return objectField;
		}

		private Boolean objectWithoutGetter;
		private boolean primitiveWithoutGetter;

	}

	@Test
	public void getsGetterForPrimitiveField() {
		Getter getter = new GetterBuilder("primitiveField", new BeanClass(),
				null).build();
		Assert.assertNotNull(getter);
	}

	@Test
	public void getsGetterForObjectField() {
		Getter getter = new GetterBuilder("objectField", new BeanClass(), null)
				.build();
		Assert.assertNotNull(getter);
	}

	@Test
	public void returnsNullForBooleanObjectFieldWithoutGetter() {
		Getter getter = new GetterBuilder("objectWithoutGetter",
				new BeanClass(), null).build();
		Assert.assertNull(getter);
	}

	@Test
	public void returnsNullForBooleanPrimitiveFieldWithoutGetter() {
		Getter getter = new GetterBuilder("primitiveWithoutGetter",
				new BeanClass(), null).build();
		Assert.assertNull(getter);
	}

}
