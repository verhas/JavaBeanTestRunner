package com.javax0.jbt;

import java.util.Collection;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import com.javax0.jbt.exception.JavaBeanFaultyException;

public class JavaBeanTestRunner extends Runner {

	private final Class<?> testClass;
	private final Class<?> beanClass;
	private final Collection<String> beanFieldNames;
	private final Collection<Getter> getters;
	private final Exception exceptionDuringCollect;

	public JavaBeanTestRunner(Class<?> testClass) {
		super();
		this.testClass = testClass;
		beanClass = new BeanClassCalculator(testClass).calculate();
		beanFieldNames = new BeanFieldsCollector(testClass, beanClass)
				.collect();
		Exception exception = null;
		Collection<Getter> getters = null;
		try {
			getters = new GetterCollector(beanClass, beanFieldNames).collect();
		} catch (InstantiationException | IllegalAccessException
				| JavaBeanFaultyException e) {
			exception = e;
		}
		this.getters = getters;
		this.exceptionDuringCollect = exception;
	}

	@Override
	public Description getDescription() {
		Description suiteDescription = Description.createSuiteDescription(
				this.testClass.getName(), this.testClass.getAnnotations());
		for (final String fieldName : beanFieldNames) {
			final Description testDescription = Description
					.createTestDescription(testClass, fieldName);
			suiteDescription.addChild(testDescription);
		}
		return suiteDescription;
	}

	@Override
	public void run(RunNotifier notifier) {
		for (final String fieldName : beanFieldNames) {
			final Description testDescription = Description
					.createTestDescription(testClass, fieldName);
			notifier.fireTestRunStarted(testDescription);
			if( exceptionDuringCollect != null ){
				notifier.fireTestFailure(new Failure(testDescription, exceptionDuringCollect));
			}
			try {
				testField(fieldName);
				notifier.fireTestFinished(testDescription);
			} catch (Exception e) {
				notifier.fireTestFailure(new Failure(testDescription, e));
			}
			notifier.fireTestFinished(testDescription);
		}
		beanClass.toString();
	}

	private void testField(String fieldName) {

		// throw new RuntimeExceptio
	}

}
