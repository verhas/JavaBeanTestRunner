package com.javax0.jbt;

import org.junit.Assert;
import org.junit.Test;

public class SetterBuilderTest {
	@SuppressWarnings("unused")
	public static class BeanClass {
		private int primitiveField;
		private Integer objectField;

		public void setPrimitiveField(int primitiveField) {
		}

		public void setObjectField(Integer objectField) {
		}

		private Boolean objectWithoutSetter;
		private boolean primitiveWithoutSetter;

	}

	@Test
	public void getsSetterForPrimitiveField() {
		Setter setter = new SetterBuilder("primitiveField", new BeanClass())
				.build();
		Assert.assertNotNull(setter);
	}

	@Test
	public void getsSetterForObjectField() {
		Setter setter = new SetterBuilder("objectField", new BeanClass())
				.build();
		Assert.assertNotNull(setter);
	}

	@Test
	public void returnsNullForBooleanObjectFieldWithoutSetter() {
		Setter setter = new SetterBuilder("objectWithoutSetter",
				new BeanClass()).build();
		Assert.assertNull(setter);
	}

	@Test
	public void returnsNullForBooleanPrimitiveFieldWithoutSetter() {
		Setter setter = new SetterBuilder("primitiveWithoutsetter",
				new BeanClass()).build();
		Assert.assertNull(setter);
	}

}
