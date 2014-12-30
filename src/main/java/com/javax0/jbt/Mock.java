package com.javax0.jbt;

import java.util.Map;

import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import com.google.common.collect.ImmutableMap;

public class Mock {

	private static final Map<Class<?>, Object> mocks = new ImmutableMap.Builder<Class<?>, Object>()
			.put(Short.class, 1).put(Integer.class, 1).put(String.class, "")
			.put(Long.class, 1L).put(Boolean.class, true)
			.put(Character.class, 'A').put(Byte.class, 0xFF)
			.put(short.class, 1).put(int.class, 1).put(long.class, 1L)
			.put(boolean.class, true).put(char.class, 'A')
			.put(float.class, 0.1f).put(double.class, 0.1d)
			.put(byte.class, 0xFF).build();

	private static final Map<Class<?>, Object> primitiveNulls = new ImmutableMap.Builder<Class<?>, Object>()
			.put(short.class, 0).put(int.class, 0).put(long.class, 0L)
			.put(boolean.class, false).put(char.class, 'a')
			.put(float.class, 0.0f).put(double.class, 0.0d)
			.put(byte.class, 0x00).build();

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
