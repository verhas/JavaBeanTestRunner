package com.javax0.jbt.testedbeans;

import com.javax0.jbt.JavaBeanTestRunnerTest;

/**
 * The class (not an instance) is passed to the runner from
 * {@link JavaBeanTestRunnerTest}. Never instantiated, never used anywhere else,
 * only for the calculation of the bean.
 * <p>
 * Classes {@code implement} this interface for documentary purpose only. This
 * is also enforced by the unit test that uses these tests having generics
 * {@code Class<? extends FakeTestClass>} arguments in some methods.
 * 
 * @author Peter Verhas <peter@verhas.com>
 *
 */
public interface FakeTestClass {

}
