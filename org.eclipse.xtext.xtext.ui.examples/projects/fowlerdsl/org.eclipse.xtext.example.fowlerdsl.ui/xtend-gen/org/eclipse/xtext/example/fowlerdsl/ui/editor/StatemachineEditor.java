/**
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.example.fowlerdsl.ui.editor;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.io.File;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.generator.IDerivedResourceMarkers;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * The StatemachineEditor class implements:
 * - the IShowInTargetList interface to provide the list of views that
 * 		should occur in the 'Show In' context menu of the Statemachine Editor.
 * - the IShowInSource interface to customize the context that will be transfered
 * 		to the Show In Target View.
 */
@SuppressWarnings("all")
public class StatemachineEditor extends XtextEditor implements IShowInSource, IShowInTargetList {
  @Inject
  @Extension
  private IDerivedResourceMarkers _iDerivedResourceMarkers;
  
  @Override
  public ShowInContext getShowInContext() {
    IFile dotFile = this.getGeneratedDotFile();
    if ((dotFile != null)) {
      StringConcatenation _builder = new StringConcatenation();
      IPath _location = ResourcesPlugin.getWorkspace().getRoot().getLocation();
      _builder.append(_location);
      _builder.append("/");
      IPath _fullPath = dotFile.getFullPath();
      _builder.append(_fullPath);
      final String fullPath = _builder.toString();
      final File input = new File(fullPath);
      final Object selection = null;
      return new ShowInContext(input, ((ISelection)selection));
    }
    return this.getContext(this);
  }
  
  @Override
  public String[] getShowInTargetIds() {
    String[] _xblockexpression = null;
    {
      final IFile dotFile = this.getGeneratedDotFile();
      String[] _xifexpression = null;
      if ((dotFile != null)) {
        _xifexpression = new String[] { "org.eclipse.gef.dot.internal.ui.DotGraphView" };
      } else {
        _xifexpression = new String[] {};
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private IFile getGeneratedDotFile() {
    IFile _xblockexpression = null;
    {
      final IStorage storage = this.getStorage(this);
      final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
      final URI uri = URI.createPlatformResourceURI(storage.getFullPath().toString(), true);
      List<IFile> generatedResources = null;
      try {
        generatedResources = this._iDerivedResourceMarkers.findDerivedResources(root, uri.toString());
      } catch (final Throwable _t) {
        if (_t instanceof CoreException) {
          final CoreException e = (CoreException)_t;
          e.printStackTrace();
          return null;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      final Function1<IFile, Boolean> _function = (IFile it) -> {
        String _fileExtension = it.getFileExtension();
        return Boolean.valueOf(Objects.equal(_fileExtension, "dot"));
      };
      _xblockexpression = IterableExtensions.<IFile>findFirst(generatedResources, _function);
    }
    return _xblockexpression;
  }
  
  private IStorage getStorage(final IEditorPart editor) {
    try {
      final IEditorInput editorInput = editor.getEditorInput();
      if ((editorInput instanceof IStorageEditorInput)) {
        return ((IStorageEditorInput)editorInput).getStorage();
      }
      return null;
    } catch (final Throwable _t) {
      if (_t instanceof CoreException) {
        return null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  private ShowInContext getContext(final XtextEditor xtextEditor) {
    ShowInContext _xblockexpression = null;
    {
      IEditorInput input = xtextEditor.getEditorInput();
      ISelectionProvider selectionProvider = xtextEditor.getSite().getSelectionProvider();
      ISelection _xifexpression = null;
      if ((selectionProvider == null)) {
        _xifexpression = null;
      } else {
        _xifexpression = selectionProvider.getSelection();
      }
      ISelection selection = _xifexpression;
      _xblockexpression = new ShowInContext(input, selection);
    }
    return _xblockexpression;
  }
}
