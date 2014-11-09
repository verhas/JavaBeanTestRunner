package com.javax0.jbt;

public class BeanClassCalculator {
	private final Class<?> testClass;
	private final String testClassName;
	private static final String TEST_CLASS_NAME_POSTFIX = "Test";
	private static final int POSTFIX_LENGTH = TEST_CLASS_NAME_POSTFIX.length();

	public BeanClassCalculator(Class<?> testClass) {
		super();
		this.testClass = testClass;
		testClassName = testClass.getName();
	}

	public Class<?> calculate() {
		final Bean bean = (Bean) testClass.getAnnotation(Bean.class);
		final Class<?> testedBeanClass;
		if (bean != null) {
			testedBeanClass = bean.value();
		} else if (testClassName.endsWith(TEST_CLASS_NAME_POSTFIX)) {
			testedBeanClass = getBeanClassFromTestClassName();
		} else {
			testedBeanClass = null;
		}
		return testedBeanClass;
	}

	private Class<?> getBeanClassFromTestClassName() {
		final Class<?> testedBeanClass;
		final String beanClassName = testClassName.substring(0,
				testClassName.length() - POSTFIX_LENGTH);
		try {
			testedBeanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			return null;
		}
		return testedBeanClass;
	}

}
