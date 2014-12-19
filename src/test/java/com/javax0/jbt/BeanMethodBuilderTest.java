package com.javax0.jbt;

import static com.javax0.jbt.BeanMethodBuilder.alternateGetter;
import static com.javax0.jbt.BeanMethodBuilder.getter;
import static com.javax0.jbt.BeanMethodBuilder.setter;

import org.junit.Assert;
import org.junit.Test;

public class BeanMethodBuilderTest {
	@SuppressWarnings("unused")
	public static class BeanClass {
		private int primitiveField;
		private Integer objectField;
		private Boolean objectWithoutGetter;
		private boolean primitiveWithoutGetter;
		private Boolean objectWithoutSetter;
		private boolean primitiveWithoutSetter;
		private boolean primitiveBooleanField;
		private Boolean booleanField;
		private Object object;
		private int integer;

		public int getPrimitiveField() {
			return primitiveField;
		}

		public Integer getObjectField() {
			return objectField;
		}

		public void setPrimitiveField(int primitiveField) {
		}

		public void setObjectField(Integer objectField) {
		}

		public boolean isPrimitiveField() {
			return primitiveBooleanField;
		}

		public Boolean isObjectField() {
			return booleanField;
		}

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
	public void getsGetterForPrimitiveField() {
		BeanMethod getter = getter("primitiveField").forBean(new BeanClass());
		Assert.assertNotNull(getter);
	}

	@Test
	public void getsGetterForObjectField() {
		BeanMethod getter = getter("objectField").forBean(new BeanClass());
		Assert.assertNotNull(getter);
	}

	@Test
	public void returnsNullForBooleanObjectFieldWithoutGetter() {
		BeanMethod getter = getter("objectWithoutGetter").forBean(
				new BeanClass());
		Assert.assertNull(getter);
	}

	@Test
	public void returnsNullForBooleanPrimitiveFieldWithoutGetter() {
		BeanMethod getter = getter("primitiveWithoutGetter").forBean(
				new BeanClass());
		Assert.assertNull(getter);
	}

	@Test
	public void getsSetterForPrimitiveField() {
		BeanMethod setter = setter("primitiveField").forBean(new BeanClass());
		Assert.assertNotNull(setter);
	}

	@Test
	public void getsSetterForObjectField() {
		BeanMethod setter = setter("objectField").forBean(new BeanClass());
		Assert.assertNotNull(setter);
	}

	@Test
	public void returnsNullForBooleanObjectFieldWithoutSetter() {
		BeanMethod setter = setter("objectWithoutSetter").forBean(
				new BeanClass());
		Assert.assertNull(setter);
	}

	@Test
	public void returnsNullForBooleanPrimitiveFieldWithoutSetter() {
		BeanMethod setter = setter("primitiveWithoutsetter").forBean(
				new BeanClass());
		Assert.assertNull(setter);
	}

	@Test
	public void getsAlternateGetterForPrimitiveField() {
		BeanMethod getter = alternateGetter("primitiveField").forBean(
				new BeanClass());
		Assert.assertNull(getter);
	}

	@Test
	public void getsAlternateGetterForObjectField() {
		BeanMethod getter = alternateGetter("objectField").forBean(
				new BeanClass());
		Assert.assertNull(getter);
	}

	@Test
	public void returnsNullForNonBooleanObjectField() {
		BeanMethod getter = alternateGetter("object")
				.forBean(new BeanClass());
		Assert.assertNull(getter);
	}

	@Test
	public void returnsNullForNonBooleanPrimitiveField() {
		BeanMethod getter = alternateGetter("integer").forBean(
				new BeanClass());
		Assert.assertNull(getter);
	}

	@Test
	public void returnsNullForBooleanObjectFieldWithoutAlternateGetter() {
		BeanMethod getter = alternateGetter("objectWithoutAlternateGetter")
				.forBean(new BeanClass());
		Assert.assertNull(getter);
	}

	@Test
	public void returnsNullForBooleanPrimitiveFieldWithoutAlternateGetter() {
		BeanMethod getter = alternateGetter("primitiveWithoutAlternateGetter")
				.forBean(new BeanClass());
		Assert.assertNull(getter);
	}
}
