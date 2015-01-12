package com.javax0.jbt;

import static com.javax0.jbt.Mock.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.mockito.Mockito;

import com.javax0.jbt.testedbeans.AlternateGetterTest;
import com.javax0.jbt.testedbeans.AnnotatedTestingClass;
import com.javax0.jbt.testedbeans.CorrectBeanTest;
import com.javax0.jbt.testedbeans.EmptyTest;
import com.javax0.jbt.testedbeans.FakeTestClass;
import com.javax0.jbt.testedbeans.NoDefaultConstructorBeanTest;
import com.javax0.jbt.testedbeans.NoGetterTest;
import com.javax0.jbt.testedbeans.NoSetterTest;
import com.javax0.jbt.testedbeans.NonOrthogonalFieldsTest;
import com.javax0.jbt.testedbeans.ObjectFieldsTest;
import com.javax0.jbt.testedbeans.PrimitiveFieldsTest;
import com.javax0.jbt.testedbeans.TestWithArgumentFactoryMethod;
import com.javax0.jbt.testedbeans.TestWithFactoryMethod;
import com.javax0.jbt.testedbeans.TestWithPrivateFactoryMethod;

public class JavaBeanTestRunnerTest {

	private void assertRunWasSuccessful() {
		verify(notifier, Mockito.atLeast(1)).fireTestRunStarted(
				any(Description.class));
		verify(notifier, never()).fireTestFailure(any(Failure.class));
		verify(notifier, Mockito.atLeast(1)).fireTestFinished(
				any(Description.class));
	}

	private void assertRunsWereSuccessful() {
		verify(notifier, Mockito.atLeast(1)).fireTestRunStarted(
				any(Description.class));
		verify(notifier, never()).fireTestFailure(any(Failure.class));
		verify(notifier, Mockito.atLeast(1)).fireTestFinished(
				any(Description.class));
	}

	private void assertRunFailed() {
		verify(notifier).fireTestRunStarted(any(Description.class));
		verify(notifier).fireTestFailure(any(Failure.class));
		verify(notifier).fireTestFinished(any(Description.class));
	}

	private void assertRunFailed(int times) {
		verify(notifier, Mockito.atLeast(1)).fireTestRunStarted(
				any(Description.class));
		verify(notifier, Mockito.atLeast(times)).fireTestFailure(
				any(Failure.class));
		verify(notifier, Mockito.atLeast(1)).fireTestFinished(
				any(Description.class));
	}

	private void assertNoTestWasStarted() {
		verify(notifier, never()).fireTestRunStarted(any(Description.class));
		verify(notifier, never()).fireTestFailure(any(Failure.class));
		verify(notifier, never()).fireTestFinished(any(Description.class));
	}

	private Runner runner;
	private RunNotifier notifier;

	private void setupRunner(Class<? extends FakeTestClass> testClass) {
		runner = new JavaBeanTestRunner(testClass);
	}

	@Before
	public void setupNotifier() {
		notifier = (RunNotifier) forClass(RunNotifier.class);
	}

	@Test
	public void testsCorrectBean() {
		setupRunner(CorrectBeanTest.class);
		runner.run(notifier);
		assertRunWasSuccessful();
	}

	@Test
	public void alternateGetterBean() {
		setupRunner(AlternateGetterTest.class);
		runner.run(notifier);
		assertRunWasSuccessful();
	}

	@Test
	public void testCorrectBeanViaAnnotatedTestingClass() {
		setupRunner(AnnotatedTestingClass.class);
		runner.run(notifier);
		assertRunWasSuccessful();
	}

	@Test
	public void testRunsWithFactoryMethod() {
		setupRunner(TestWithFactoryMethod.class);
		runner.run(notifier);
		assertRunWasSuccessful();
	}

	@Test
	public void primitiveFields() {
		setupRunner(PrimitiveFieldsTest.class);
		runner.run(notifier);
		assertRunsWereSuccessful();
	}

	@Test
	public void objectFields() {
		setupRunner(ObjectFieldsTest.class);
		runner.run(notifier);
		assertRunsWereSuccessful();
	}

	@Test
	public void testsEmptyBean() {
		setupRunner(EmptyTest.class);
		runner.run(notifier);
		assertNoTestWasStarted();
	}

	@Test
	public void noGetterBeanFails() {
		setupRunner(NoGetterTest.class);
		runner.run(notifier);
		assertRunFailed();
	}

	@Test
	public void noSetterBeanFails() {
		setupRunner(NoSetterTest.class);
		runner.run(notifier);
		assertRunFailed();
	}

	@Test
	public void nonOrthogonalFieldsBeanFails() {
		setupRunner(NonOrthogonalFieldsTest.class);
		runner.run(notifier);
		assertRunFailed(NonOrthogonalFieldsTest.NUMBER_OF_NONORTHOGONAL_FIELDS);
	}

	@Test
	public void noDefaultConstructorBeanFails() {
		setupRunner(NoDefaultConstructorBeanTest.class);
		runner.run(notifier);
		assertRunFailed();
	}

	@Test
	public void testWithPrivateFactoryMethodFails() {
		setupRunner(TestWithPrivateFactoryMethod.class);
		runner.run(notifier);
		assertRunFailed();
	}

	@Test
	public void testWithArgumentFactoryMethodFails() {
		setupRunner(TestWithArgumentFactoryMethod.class);
		runner.run(notifier);
		assertRunFailed();
	}
}
