package com.javax0.jbt;

import java.util.Map;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import com.javax0.jbt.TestCaseNotifier.NotificationException;
import com.javax0.jbt.exception.JavaBeanFaultyException;

public class JavaBeanTestRunner extends Runner {

	private final Class<?> testClass;
	private final Class<?> beanClass;
	private final Map<String, BeanField> beanFields;
	private final Exception exceptionDuringCollect;

	public JavaBeanTestRunner(final Class<?> testClass) {
		super();
		this.testClass = testClass;
		beanClass = new BeanClassCalculator(testClass).calculate();
		Exception exception = null;
		Map<String, BeanField> beanFields = null;
		try {
			final BeanFieldsCollector collector = new BeanFieldsCollector(
					testClass, beanClass);
			beanFields = collector.map();
		} catch (InstantiationException | IllegalAccessException e) {
			exception = e;
		}
		this.beanFields = beanFields;
		this.exceptionDuringCollect = exception;
		suiteDescription = Description.createSuiteDescription(
				this.testClass.getName(), this.testClass.getAnnotations());
	}

	private final Description suiteDescription;
	private boolean suiteDescriptionNeedsInitialization = true;

	@Override
	public Description getDescription() {
		if (suiteDescriptionNeedsInitialization) {
			for (final String fieldName : beanFields.keySet()) {
				final Description testDescription = Description
						.createTestDescription(testClass, fieldName);
				suiteDescription.addChild(testDescription);
			}
			suiteDescriptionNeedsInitialization = false;
		}
		return suiteDescription;
	}

	@Override
	public void run(final RunNotifier runNotifier) {
		for (final String fieldName : beanFields.keySet()) {
			final Description testDescription = Description
					.createTestDescription(testClass, fieldName);
			final TestCaseNotifier notifier = new TestCaseNotifier(runNotifier,
					testDescription);
			notifier.start();
			if (exceptionDuringCollect != null) {
				runNotifier.fireTestFailure(new Failure(testDescription,
						exceptionDuringCollect));
			} else {
				try {
					testField(fieldName, notifier);
				} catch (final Exception e) {
					notifier.failure(e);
				}
			}
			notifier.finish();
		}
		beanClass.toString();
	}

	private void testField(final String fieldName,
			final TestCaseNotifier notifier) throws NoSuchFieldException,
			SecurityException, JavaBeanFaultyException, NotificationException {
		final BeanField beanField = beanFields.get(fieldName);
		assertGetterExists(notifier, beanField);
		assertSetterExists(notifier, beanField);
		assertGetSet(notifier, beanField);
	}

	private void assertGetSet(final TestCaseNotifier notifier,
			final BeanField beanField) {
		if (beanField.getSetter() != null) {
			final Object actual = Mock.forClass(beanField.getType());
			final Object getterResult;
			final Object alternateGetterResult;
			try {
				beanField.set(actual);
				if (beanField.getGetter() != null) {
					getterResult = beanField.get();
					if (!equal(beanField.getType(), getterResult, actual)) {
						notifier.failure("getter returned " + getterResult
								+ " after set(" + actual + ")");
					}
				}
				if (beanField.getAlternateGetter() != null) {
					alternateGetterResult = beanField.alternateGet();
					if (!equal(beanField.getType(), alternateGetterResult,
							actual)) {
						notifier.failure("alternate getter returned "
								+ alternateGetterResult + " after set("
								+ actual + ")");
					}
				}
			} catch (final Exception e) {
				notifier.failure(e);
			}
		}
	}

	private boolean equal(Class<?> type, Object a, Object b) {
		if (type.isPrimitive()) {
			return (a == null && b == null) || (a != null && a.equals(b));
		} else {
			return a == b;
		}
	}

	private void assertSetterExists(final TestCaseNotifier notifier,
			final BeanField beanField) {
		if (beanField.getSetter() == null) {
			notifier.failure("no setter");
		}
	}

	private void assertGetterExists(final TestCaseNotifier notifier,
			final BeanField beanField) {
		if (beanField.getGetter() == null
				&& beanField.getAlternateGetter() == null) {
			notifier.failure("no getter");
		}
	}
}
