package com.javax0.jbt;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BeanFieldsCollector {
	private final Class<?> testClass;
	private final Class<?> beanClass;
	final Collection<String> ignoredFields;

	public BeanFieldsCollector(Class<?> testClass, Class<?> beanClass) {
		super();
		this.testClass = testClass;
		this.beanClass = beanClass;
		this.ignoredFields = getIgnoredFields();
	}

	Collection<String> collect() {
		final Field[] fields = beanClass.getDeclaredFields();
		final Set<String> fieldNames = new HashSet<>();

		for (final Field field : fields) {
			if (!ignore(field)) {
				fieldNames.add(field.getName());
			}
		}
		return fieldNames;
	}

	private boolean ignore(final Field field) {
		return ignoredFields.contains(field.getName())
				|| Modifier.isStatic(field.getModifiers())
				|| Modifier.isFinal(field.getModifiers());
	}

	private Collection<String> getIgnoredFields() {
		final Set<String> fieldNames = new HashSet<>();
		final Bean bean = (Bean) testClass.getAnnotation(Bean.class);
		if (bean != null) {
			fieldNames.addAll(Arrays.asList(bean.ignore()));
		}
		return fieldNames;
	}

}
