package com.javax0.jbt;

import java.util.Map;

import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import com.google.common.collect.ImmutableMap;

public class Mock {

	private static final Map<Class<?>, Object> mocks = new ImmutableMap.Builder<Class<?>, Object>()
			.put(Integer.class, 1).put(String.class, "").put(Long.class, 1L)
			.put(Boolean.class, true).put(Character.class, 'A')
			.put(Byte.class, 0xFF).put(int.class, 1).put(long.class, 1L)
			.put(boolean.class, true).put(char.class, 'A')
			.put(byte.class, 0xFF).build();

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
