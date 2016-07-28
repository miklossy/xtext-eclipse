/*
 * generated by Xtext
 */
package org.eclipse.xtext.ui.tests.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.ui.tests.TestLanguageRuntimeModule;
import org.eclipse.xtext.ui.tests.TestLanguageStandaloneSetup;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class TestLanguageIdeSetup extends TestLanguageStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(new TestLanguageRuntimeModule(), new TestLanguageIdeModule());
	}
}
