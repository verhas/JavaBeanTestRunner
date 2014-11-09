package com.javax0.jbt;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.javax0.jbt.exception.JavaBeanFaultyException;
import com.javax0.jbt.exception.NoGetter;
import com.javax0.jbt.exception.NoSetter;

public class GetterCollector {
	private final Collection<String> beanFieldNames;

	private final Object bean;

	public GetterCollector(Class<?> beanClass, Collection<String> beanFieldNames)
			throws InstantiationException, IllegalAccessException {
		super();
		this.beanFieldNames = beanFieldNames;
		this.bean = beanClass.newInstance();
	}

	public Collection<Getter> collect() throws JavaBeanFaultyException {

		Set<Getter> getters = new HashSet<>();
		for (String fieldName : beanFieldNames) {
			Setter setter = new SetterBuilder(fieldName, bean).build();
			if (setter == null) {
				throw new NoSetter("Field '" + fieldName + "' has no setter.");
			}
			Getter getter = new GetterBuilder(fieldName, bean, setter).build();
			Getter alternateGetter = new AlternateGetterBuilder(fieldName,
					bean, setter).build();
			if (getter == null && alternateGetter == null) {
				throw new NoGetter("Field '" + fieldName + "' has no getter.");
			}
			if (getter != null) {
				getters.add(getter);

			}
			if (alternateGetter != null) {
				getters.add(alternateGetter);
			}
		}
		return getters;
	}
}
