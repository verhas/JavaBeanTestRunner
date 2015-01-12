package com.javax0.jbt;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import com.javax0.jbt.TestCaseNotifier.NotificationException;
import com.javax0.jbt.exception.JavaBeanFaultyException;
import com.javax0.jbt.exception.JavaBeanTestFaultyException;

/**
 * Implements a JUNIT Runner that checks that the tested JavaBean conforms
 * (practically) to the JavaBean conventions. It checks that the
 * 
 * @author Peter Verhas <peter@verhas.com>
 *
 */
public class JavaBeanTestRunner extends Runner {

	private final Class<?> testClass;
	private final Class<?> beanClass;
	private final Object testObject;
	private Map<String, BeanField> beanFields;
	private Exception exceptionDuringCollect;

	public JavaBeanTestRunner(final Class<?> testClass) {
		super();
		this.testClass = testClass;
		beanClass = new BeanClassCalculator(testClass).calculate();
		testObject = instantiateTestObject();
		collectBeanFields(testObject);
		suiteDescription = Description.createSuiteDescription(
				this.testClass.getName(), this.testClass.getAnnotations());
	}

	private Object instantiateTestObject() {
		try {
			return testClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			exceptionDuringCollect = e;
			return null;
		}
	}

	private void collectBeanFields(final Object testObject) {
		this.beanFields = null;
		this.exceptionDuringCollect = null;
		try {
			final BeanFieldsCollector collector = new BeanFieldsCollector(
					testObject, beanClass);
			this.beanFields = collector.map();
		} catch (JavaBeanFaultyException | JavaBeanTestFaultyException e) {
			this.exceptionDuringCollect = e;
		}
	}

	private final Description suiteDescription;
	private boolean suiteDescriptionNeedsInitialization = true;

	@Override
	public Description getDescription() {
		if (suiteDescriptionNeedsInitialization && beanFields != null) {
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
		if (exceptionDuringCollect == null) {
			for (final String fieldName : beanFields.keySet()) {
				final Description testDescription = Description
						.createTestDescription(testClass, fieldName);
				final TestCaseNotifier notifier = new TestCaseNotifier(
						runNotifier, testDescription);
				notifier.start();
				try {
					testField(fieldName, notifier);
				} catch (final Exception e) {
					notifier.failure(e);
				}
				notifier.finish();
			}
		} else {
			final Description testDescription = Description
					.createTestDescription(testClass,
							testClass.getCanonicalName());
			final TestCaseNotifier notifier = new TestCaseNotifier(runNotifier,
					testDescription);
			notifier.start();
			runNotifier.fireTestFailure(new Failure(testDescription,
					exceptionDuringCollect));
			notifier.finish();
		}
	}

	private void testField(final String fieldName,
			final TestCaseNotifier notifier) throws NoSuchFieldException,
			SecurityException, JavaBeanFaultyException, NotificationException {
		final BeanField beanField = beanFields.get(fieldName);
		assertGetterExists(notifier, beanField);
		assertSetterExists(notifier, beanField);
		assertGetSetWorksInPair(notifier, beanField);
	}

	private void assertGetSetWorksInPair(final TestCaseNotifier notifier,
			final BeanField beanField) {
		setAllFieldsToNull();
		if (beanField.hasSetter()) {
			final Object actual = Mock.forClass(beanField.getType());
			try {
				beanField.set(actual);
				if (beanField.hasGetter()) {
					getterGivesBackTheSetValue(notifier, beanField, actual);
				}
				if (beanField.hasAlternateGetter()) {
					alternateGetterGivesBackTheSetValue(notifier, beanField,
							actual);
				}
				assertAllFieldsNull(notifier, beanField.getName());
			} catch (final Exception e) {
				notifier.failure(e);
			}
		}
	}

	private void assertAllFieldsNull(final TestCaseNotifier notifier,
			final String actual) {
		for (final Entry<String, BeanField> field : beanFields.entrySet()) {
			if (!field.getKey().equals(actual)) {
				try {
					final Object object = field.getValue().directGet();
					if (object != null
							&& !object.equals(Mock.nullForClass(field
									.getValue().getType()))) {
						notifier.failure("Setting field '" + actual
								+ "' altered '" + field.getKey() + "'");
					}
				} catch (IllegalAccessException | IllegalArgumentException
						| NoSuchFieldException | SecurityException e) {
					throw new RuntimeException("SNAFU");
				}
			}
		}
	}

	private void setAllFieldsToNull() {
		for (final Entry<String, BeanField> field : beanFields.entrySet()) {
			try {
				field.getValue().directSet(
						Mock.nullForClass(field.getValue().getType()));
			} catch (IllegalAccessException | IllegalArgumentException
					| NoSuchFieldException | SecurityException e) {
				throw new RuntimeException("SNAFU", e);
			}
		}
	}

	private void alternateGetterGivesBackTheSetValue(
			final TestCaseNotifier notifier, final BeanField beanField,
			final Object actual) throws IllegalAccessException,
			InvocationTargetException {
		final Object alternateGetterResult = beanField.alternateGet();
		if (differ(beanField.getType(), alternateGetterResult, actual)) {
			notifier.failure("alternate getter returned "
					+ alternateGetterResult + " after set(" + actual + ")");
		}
	}

	private void getterGivesBackTheSetValue(final TestCaseNotifier notifier,
			final BeanField beanField, final Object actual)
			throws IllegalAccessException, InvocationTargetException {
		final Object getterResult = beanField.get();
		if (differ(beanField.getType(), getterResult, actual)) {
			notifier.failure("getter returned " + getterResult + " after set("
					+ actual + ")");
		}
	}

	private boolean differ(Class<?> type, Object a, Object b) {
		if (type.isPrimitive()) {
			return !equalAutoboxedPrimitives(a, b);
		} else {
			return a != b;
		}
	}

	private boolean equalAutoboxedPrimitives(Object a, Object b) {
		return (a == null && b == null) || (a != null && a.equals(b));
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
