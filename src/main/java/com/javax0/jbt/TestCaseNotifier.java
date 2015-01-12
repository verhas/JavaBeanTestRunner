package com.javax0.jbt;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

/**
 * 
 * Send notification to the JUNIT framework from a test. The class contains the
 * JUNIT notifier and the description on which the notification is sent.
 * 
 * @author Peter Verhas <peter@verhas.com>
 *
 */
class TestCaseNotifier {
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

	TestCaseNotifier(RunNotifier notifier, Description testDescription) {
		super();
		this.notifier = notifier;
		this.testDescription = testDescription;
	}

	void start() {
		notifier.fireTestRunStarted(testDescription);
	}

	void failure(Throwable throwable) {
		notifier.fireTestFailure(new Failure(testDescription, throwable));
	}

	void failure(String message) {
		notifier.fireTestFailure(new Failure(testDescription,
				new NotificationException(message)));
	}

	void finish() {
		notifier.fireTestFinished(testDescription);
	}
}
