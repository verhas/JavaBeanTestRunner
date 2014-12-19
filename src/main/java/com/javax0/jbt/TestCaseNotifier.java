package com.javax0.jbt;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class TestCaseNotifier {
	@SuppressWarnings("serial")
	static final class NotificationException extends Exception {
		final String message;
		private static final Throwable noCauseException = null;
		private static final boolean enableSupression = true;
		private static final boolean writableStackTraceNone = false;

		public NotificationException(String message) {
			super(message, noCauseException, enableSupression,
					writableStackTraceNone);
			this.message = message;
		}

		@Override
		public String toString() {
			return message;
		}

	}

	final RunNotifier notifier;
	final Description testDescription;

	public TestCaseNotifier(RunNotifier notifier, Description testDescription) {
		super();
		this.notifier = notifier;
		this.testDescription = testDescription;
	}

	public void start() {
		notifier.fireTestRunStarted(testDescription);
	}

	public void failure(Throwable throwable) {
		notifier.fireTestFailure(new Failure(testDescription, throwable));
	}

	public void failure(String message) {
		notifier.fireTestAssumptionFailed(new Failure(testDescription,
				new NotificationException(message)));
	}

	public void finish() {
		notifier.fireTestFinished(testDescription);
	}
}