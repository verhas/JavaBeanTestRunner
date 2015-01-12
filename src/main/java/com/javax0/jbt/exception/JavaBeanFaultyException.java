package com.javax0.jbt.exception;

public abstract class JavaBeanFaultyException extends Exception {
	private static final long serialVersionUID = 1L;

	public JavaBeanFaultyException(String message) {
		super(message);
	}

	public JavaBeanFaultyException(String message, Throwable cause) {
		super(message, cause);
	}
}
