package com.javax0.jbt;

public interface Capitalizer {
	public static String capitalize(final String string) {
		if (string == null || string.isEmpty()) {
			return "";
		}
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
}
