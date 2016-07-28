/*
 * generated by Xtext
 */
package org.eclipse.xtext.ui.tests.linking.ide;

import com.google.inject.Binder;
import com.google.inject.name.Names;
import org.eclipse.xtext.ide.DefaultIdeModule;
import org.eclipse.xtext.ide.LexerIdeBindings;
import org.eclipse.xtext.ide.editor.contentassist.antlr.IContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
import org.eclipse.xtext.ui.tests.linking.ide.contentassist.antlr.ImportUriUiTestLanguageParser;
import org.eclipse.xtext.ui.tests.linking.ide.contentassist.antlr.internal.InternalImportUriUiTestLanguageLexer;

/**
 * Manual modifications go to {@link ImportUriUiTestLanguageIdeModule}.
 */
@SuppressWarnings("all")
public abstract class AbstractImportUriUiTestLanguageIdeModule extends DefaultIdeModule {

	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public void configureContentAssistLexer(Binder binder) {
		binder.bind(Lexer.class)
			.annotatedWith(Names.named(LexerIdeBindings.CONTENT_ASSIST))
			.to(InternalImportUriUiTestLanguageLexer.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends IContentAssistParser> bindIContentAssistParser() {
		return ImportUriUiTestLanguageParser.class;
	}
	
}
