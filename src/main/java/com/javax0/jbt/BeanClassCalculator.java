package com.javax0.jbt;

import com.javax0.jbt.annotations.Bean;

/**
 * Get the class that has to be tested. The tested class is calculated from the
 * testing class and also can be controlled using annotations.
 * <p>
 * If the testing class is annotated with the annotation {@link Bean} then the
 * value of the annotation specifies the tested class. There is no default value
 * for the tested class if the annotation is present on the testing class.
 * <p>
 * If there is no {@link Bean} annotation on the testing class then this class
 * checks if the testing class name ends with "Test". If it does then it assumes
 * that the name of the tested class is the same as the testing class name
 * without the "Test" at the end. For example "MyBeanTest" unit test class will
 * test "MyBean".
 * <p>
 * If none of the above is true or there is no class with the calculated name
 * then this class will result null as tested class.
 * 
 * @author Peter Verhas <peter@verhas.com>
 *
 */
class BeanClassCalculator {
	private final Class<?> testingClass;
	private final String testingClassName;
	private static final String TEST_CLASS_NAME_POSTFIX = "Test";
	private static final int POSTFIX_LENGTH = TEST_CLASS_NAME_POSTFIX.length();

	/**
	 * Create a new tested bean class calculator based on the
	 * 
	 * @param testingClass
	 */
	BeanClassCalculator(Class<?> testingClass) {
		super();
		this.testingClass = testingClass;
		testingClassName = testingClass.getName();
	}

	/**
	 * Calculate the tested bean class as described in the class documentation
	 * and return the result.
	 * 
	 * @return the class to test or null if the class can not be identified.
	 */
	Class<?> calculate() {
		final Bean bean = (Bean) testingClass.getAnnotation(Bean.class);
		final Class<?> testedBeanClass;
		if (bean != null) {
			testedBeanClass = bean.value();
		} else if (testingClassName.endsWith(TEST_CLASS_NAME_POSTFIX)) {
			testedBeanClass = getBeanClassFromTestClassName();
		} else {
			testedBeanClass = null;
		}
		return testedBeanClass;
	}

	private Class<?> getBeanClassFromTestClassName() {
		final Class<?> testedBeanClass;
		final String beanClassName = testingClassName.substring(0,
				testingClassName.length() - POSTFIX_LENGTH);
		try {
			testedBeanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			return null;
		}
		return testedBeanClass;
	}

}
