/*
 * generated by Xtext
 */
package org.eclipse.xtext.ui.tests.editor.contentassist.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.ui.tests.editor.contentassist.Bug309949TestLanguageRuntimeModule;
import org.eclipse.xtext.ui.tests.editor.contentassist.Bug309949TestLanguageStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class Bug309949TestLanguageIdeSetup extends Bug309949TestLanguageStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new Bug309949TestLanguageRuntimeModule(), new Bug309949TestLanguageIdeModule()));
	}
}
