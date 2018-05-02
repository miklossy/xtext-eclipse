/**
 * generated by Xtext
 */
package org.eclipse.xtext.example.fowlerdsl.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.example.fowlerdsl.ui.AbstractStatemachineUiModule;
import org.eclipse.xtext.example.fowlerdsl.ui.editor.StatemachineEditor;
import org.eclipse.xtext.ui.editor.XtextEditor;

/**
 * Use this class to register components to be used within the IDE.
 */
@SuppressWarnings("all")
public class StatemachineUiModule extends AbstractStatemachineUiModule {
  public StatemachineUiModule(final AbstractUIPlugin plugin) {
    super(plugin);
  }
  
  public Class<? extends XtextEditor> bindXtextEditor() {
    return StatemachineEditor.class;
  }
}
