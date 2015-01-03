package com.javax0.jbt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

public class BeanMethodTest {
	static class Bean {
		private Object field;

		public Object getField() {
			return field;
		}

		public void setField(Object field) {
			this.field = field;
		}

		public boolean is(Object object) {
			return field == object;
		}
	}

	@Test
	public void canCallSetter() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Bean bean = new Bean();
		Method setter = Bean.class.getMethod("setField",
				new Class[] { Object.class });
		BeanMethod beanMethod = new BeanMethod(setter, bean);
		beanMethod.invoke(this);
		Assert.assertTrue(bean.is(this));
	}

	@Test
	public void canCallGetter() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Bean bean = new Bean();
		bean.setField(this);
		Method setter = Bean.class.getMethod("getField", new Class[0]);
		BeanMethod beanMethod = new BeanMethod(setter, bean);
		Object it = beanMethod.invoke();
		Assert.assertTrue(it == this);
	}
	
	@Test
	public void givesBackTheBean() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Bean bean = new Bean();
		BeanMethod beanMethod = new BeanMethod(null, bean);
		Assert.assertTrue(beanMethod.getBean() == bean);
	}
}
