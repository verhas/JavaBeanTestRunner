package com.javax0.jbt;

import static com.javax0.jbt.BeanFieldBuilder.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeanFieldsCollector {
	private final Class<?> testClass;
	private final Class<?> beanClass;
	final Collection<String> ignoredFields;
	private final Map<String, BeanField> beanFields;

	public BeanFieldsCollector(final Class<?> testClass,
			final Class<?> beanClass) throws InstantiationException,
			IllegalAccessException {
		super();
		this.testClass = testClass;
		this.beanClass = beanClass;
		this.ignoredFields = getIgnoredFields();
		beanFields = collect();
	}

	public Map<String, BeanField> map() {
		return beanFields;
	}

	private Map<String, BeanField> collect() throws InstantiationException,
			IllegalAccessException {
		final Field[] fields = beanClass.getDeclaredFields();
		final Object bean = beanClass.newInstance();
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

	private Collection<String> getIgnoredFields() {
		final Set<String> fieldNames = new HashSet<>();
		final Bean bean = (Bean) testClass.getAnnotation(Bean.class);
		if (bean != null) {
			fieldNames.addAll(Arrays.asList(bean.ignore()));
		}
		return fieldNames;
	}
}
