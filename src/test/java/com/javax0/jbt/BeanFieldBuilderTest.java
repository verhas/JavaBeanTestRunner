package com.javax0.jbt;

import static com.javax0.jbt.BeanFieldBuilder.field;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class BeanFieldBuilderTest {
	static class BeanBasic {
		private Object field;

		public Object getField() {
			return field;
		}

		public void setField(Object field) {
			this.field = field;
		}

		private boolean booleanField;

		public boolean isBooleanField() {
			return booleanField;
		}

		public void setBooleanField(boolean booleanField) {
			this.booleanField = booleanField;
		}

		private boolean richBooleanField;

		public boolean isRichBooleanField() {
			return richBooleanField;
		}

		public boolean getRichBooleanField() {
			return richBooleanField;
		}

		public void setRichBooleanField(boolean richBooleanField) {
			this.richBooleanField = richBooleanField;
		}

	}

	@Test
	public void createsBeanFieldForObjectField() {
		BeanField beanField = field("field").forBean(new BeanBasic());
		assertNotNull(beanField.getGetter());
		assertNotNull(beanField.getSetter());
		assertNull(beanField.getAlternateGetter());
	}

	@Test
	public void createsBeanFieldForBooleanField() {
		BeanField beanField = field("booleanField").forBean(new BeanBasic());
		assertNull(beanField.getGetter());
		assertNotNull(beanField.getSetter());
		assertNotNull(beanField.getAlternateGetter());
	}

	@Test
	public void createsBeanFieldForRichBooleanField() {
		BeanField beanField = field("richBooleanField").forBean(new BeanBasic());
		assertNotNull(beanField.getGetter());
		assertNotNull(beanField.getSetter());
		assertNotNull(beanField.getAlternateGetter());
	}
}
