package com.javax0.jbt;

import java.util.Map;

import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import com.google.common.collect.ImmutableMap;

public class Mock {

	private static final Map<Class<?>, Object> mocks = new ImmutableMap.Builder<Class<?>, Object>()
			.put(Short.class, Short.valueOf((short) 1))
			.put(Integer.class, Integer.valueOf(1)).put(String.class, "")
			.put(Long.class, Long.valueOf(1L)).put(Boolean.class, true)
			.put(Character.class, 'A')
			.put(Byte.class, Byte.valueOf((byte) 0xFF))
			.put(Float.class, Float.valueOf(0.1f))
			.put(Double.class, Double.valueOf(0.1f))
			.put(short.class, Short.valueOf((short) 1))
			.put(int.class, Integer.valueOf(1))
			.put(long.class, Long.valueOf(1L)).put(boolean.class, true)
			.put(char.class, 'A').put(float.class, Float.valueOf(0.1f))
			.put(double.class, Double.valueOf(0.1d))
			.put(byte.class, Byte.valueOf((byte) 0xFF)).build();

	private static final Map<Class<?>, Object> primitiveNulls = new ImmutableMap.Builder<Class<?>, Object>()
			.put(short.class, Short.valueOf((short) 0))
			.put(int.class, Integer.valueOf(0))
			.put(long.class, Long.valueOf(0L)).put(boolean.class, false)
			.put(char.class, 'a').put(float.class, Float.valueOf(0.0f))
			.put(double.class, Double.valueOf(0.0d))
			.put(byte.class, Byte.valueOf((byte) 0)).build();

	public static Object nullForClass(Class<?> klass) {
		if (primitiveNulls.containsKey(klass)) {
			return primitiveNulls.get(klass);
		}
		if (klass.isEnum()) {
			return klass.getEnumConstants()[0];
		}
		return null;
	}

	public static Object forClass(Class<?> klass) {
		if (mocks.containsKey(klass)) {
			return mocks.get(klass);
		}
		if (klass.isEnum()) {
			return klass.getEnumConstants()[0];
		}
		try {
			return Mockito.mock(klass);
		} catch (MockitoException me) {
			throw new RuntimeException(klass + " is problematic to mock", me);
		}
	}
}
