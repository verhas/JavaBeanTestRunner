package com.javax0.jbt;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * A simple class to get all classes into a set so that MockTest can have enough
 * classes to test what mock objects are produced for classes.
 * 
 * @author Peter Verhas
 *
 */
class ClassCollector {
	Field classesField;

	final Set<Class<?>> collectedClasses = new HashSet<Class<?>>();
	final Set<Class<?>> collectedEnums = new HashSet<Class<?>>();

	public Set<Class<?>> getClasses() {
		return collectedClasses;
	}

	public Set<Class<?>> getEnums() {
		return collectedEnums;
	}

	ClassCollector() {
		try {
			classesField = ClassLoader.class.getDeclaredField("classes");
			collect();
		} catch (Exception e) {
			throw new RuntimeException("SNAFU", e);
		}
	}

	private void collect() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		classesField.setAccessible(true);
		addClassesFrom(classLoader);
	}

	private void addClassesFrom(ClassLoader loader)
			throws IllegalAccessException {
		@SuppressWarnings("unchecked")
		Vector<Class<?>> classes = (Vector<Class<?>>) getClassesVector(loader)
				.clone();
		for (Class<?> klass : classes) {
			if (klass.isEnum()) {
				collectedEnums.add(klass);
			} else {
				int modifier = klass.getModifiers();
				if (!Modifier.isPrivate(modifier)
						&& !Modifier.isFinal(modifier)
						&& klass.getName().equals(klass.getCanonicalName())) {
					collectedClasses.add(klass);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Vector<Class<?>> getClassesVector(ClassLoader loader)
			throws IllegalAccessException {
		return (Vector<Class<?>>) classesField.get(loader);
	}
}
