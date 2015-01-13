package com.javax0.jbt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class TestCaseNotifierTest {
	private RunNotifier runNotifier;
	private Description description;
	private static final String FAILURE_STRING = "failure";

	@Before
	public void setupRunNotifierAndDescription() {
		runNotifier = Mockito.mock(RunNotifier.class);
		description = Mockito.mock(Description.class);
	}

	@Test
	public void firesTestRunStartedOnStart() {
		TestCaseNotifier notifier = new TestCaseNotifier(runNotifier,
				description);
		notifier.start();
		Mockito.verify(runNotifier).fireTestStarted(description);
	}

	static class IsFailureWithFailureString extends ArgumentMatcher<Failure> {

		@Override
		public boolean matches(Object argument) {
			final Failure failure = (Failure) argument;
			final Throwable throwable = failure.getException();
			final TestCaseNotifier.NotificationException exception = (TestCaseNotifier.NotificationException) throwable;
			final String message = exception.toString();
			return FAILURE_STRING.equals(message);
		}

	}

	@Test
	public void firesTestFailureOnFailure_String_() {
		TestCaseNotifier notifier = new TestCaseNotifier(runNotifier,
				description);
		notifier.failure(FAILURE_STRING);
		Mockito.verify(runNotifier).fireTestFailure(
				Matchers.argThat(new IsFailureWithFailureString()));
	}

	@Test
	public void firesTestFailureOnFailure_Throwable_() {
		TestCaseNotifier notifier = new TestCaseNotifier(runNotifier,
				description);
		notifier.failure(new Throwable());
		Mockito.verify(runNotifier).fireTestFailure(Matchers.<Failure>any());
	}

	@Test
	public void firesTestFinishedOnFinish() {
		TestCaseNotifier notifier = new TestCaseNotifier(runNotifier,
				description);
		notifier.finish();
		Mockito.verify(runNotifier).fireTestFinished(description);
	}
}
