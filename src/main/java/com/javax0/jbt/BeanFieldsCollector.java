package com.javax0.jbt;

import static com.javax0.jbt.BeanFieldBuilder.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.javax0.jbt.annotations.Ignore;
import com.javax0.jbt.exception.CanNotInstantiate;
import com.javax0.jbt.exception.IgnoredFieldDoesNotExist;
import com.javax0.jbt.exception.IgnoredFieldHasWrongType;
import com.javax0.jbt.exception.JavaBeanFaultyException;
import com.javax0.jbt.exception.JavaBeanTestFaultyException;

class BeanFieldsCollector {
	private final Class<?> testClass;
	private final Object testObject;
	private final Class<?> beanClass;
	final Collection<String> ignoredFields;
	private final Map<String, BeanField> beanFields;

	BeanFieldsCollector(final Object testObject, final Class<?> beanClass)
			throws JavaBeanTestFaultyException, JavaBeanFaultyException {
		this.testObject = testObject;
		this.testClass = testObject.getClass();
		this.beanClass = beanClass;
		this.ignoredFields = getIgnoredFields();
		beanFields = collect();
	}

	Map<String, BeanField> map() {
		return beanFields;
	}

	private Map<String, BeanField> collect() throws JavaBeanFaultyException {
		final Field[] fields = beanClass.getDeclaredFields();
		final Object bean;
		try {
			bean = new TestedBeanFactory(testObject, beanClass).getBean();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException e) {
			throw new CanNotInstantiate(
					"Can not have an instance of the bean class '" + beanClass
							+ "'", e);
		}
		final Map<String, BeanField> beanFields = new HashMap<>();

		for (final Field field : fields) {
			if (!ignore(field)) {
				beanFields.put(field.getName(),
						field(field.getName()).forBean(bean));
			}
		}
		return beanFields;
	}

	private boolean ignore(final Field field) {
		return ignoredFields.contains(field.getName())
				|| Modifier.isStatic(field.getModifiers())
				|| Modifier.isFinal(field.getModifiers());
	}

	private Collection<String> getIgnoredFields()
			throws JavaBeanTestFaultyException {
		final Set<String> fieldNames = new HashSet<>();
		for (final Field field : testClass.getDeclaredFields()) {
			final Ignore ignored = (Ignore) field.getAnnotation(Ignore.class);
			if (ignored != null) {
				try {
					final Field testedField = beanClass.getDeclaredField(field
							.getName());
					assertFieldsHaveSameType(field, testedField);
					fieldNames.add(field.getName());
				} catch (NoSuchFieldException | SecurityException e) {
					throw new IgnoredFieldDoesNotExist("Ignored field '"
							+ field.getName() + "' does not exist in the bean");
				}
			}
		}
		return fieldNames;
	}

	private void assertFieldsHaveSameType(final Field field,
			final Field testedField) throws IgnoredFieldHasWrongType {
		if (testedField.getType() != field.getType()) {
			throw new IgnoredFieldHasWrongType("Field '" + field.getName()
					+ "' has different type in the test and in the bean");
		}
	}
}
