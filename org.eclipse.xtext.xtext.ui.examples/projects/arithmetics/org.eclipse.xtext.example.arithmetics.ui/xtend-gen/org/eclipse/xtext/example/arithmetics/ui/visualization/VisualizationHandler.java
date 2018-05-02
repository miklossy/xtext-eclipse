/**
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.example.arithmetics.ui.visualization;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.builder.DerivedResourceMarkers;
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class VisualizationHandler extends AbstractHandler {
  @Inject
  @Extension
  private IGenerator2 _iGenerator2;
  
  @Inject
  @Extension
  private DerivedResourceMarkers _derivedResourceMarkers;
  
  @Inject
  private IResourceSetProvider resourceSetProvider;
  
  @Inject
  private Provider<EclipseResourceFileSystemAccess2> fileAccessProvider;
  
  @Override
  public Object execute(final ExecutionEvent event) throws ExecutionException {
    Object _xblockexpression = null;
    {
      final ISelection selection = HandlerUtil.getCurrentSelection(event);
      if ((selection instanceof IStructuredSelection)) {
        final Object file = ((IStructuredSelection)selection).getFirstElement();
        if ((file instanceof IFile)) {
          final IProject project = ((IFile)file).getProject();
          final String outputFolderName = "src-gen";
          final IFolder srcGenFolder = project.getFolder(outputFolderName);
          boolean _exists = srcGenFolder.exists();
          boolean _not = (!_exists);
          if (_not) {
            try {
              NullProgressMonitor _nullProgressMonitor = new NullProgressMonitor();
              srcGenFolder.create(true, true, _nullProgressMonitor);
            } catch (final Throwable _t) {
              if (_t instanceof CoreException) {
                return null;
              } else {
                throw Exceptions.sneakyThrow(_t);
              }
            }
          }
          final EclipseResourceFileSystemAccess2 fsa = this.fileAccessProvider.get();
          NullProgressMonitor _nullProgressMonitor_1 = new NullProgressMonitor();
          fsa.setMonitor(_nullProgressMonitor_1);
          fsa.setProject(project);
          fsa.setOutputPath(outputFolderName);
          final URI uri = URI.createPlatformResourceURI(((IFile)file).getFullPath().toString(), true);
          final Resource resource = this.resourceSetProvider.get(project).getResource(uri, true);
          this.addDerivedMarkerInstaller(fsa, uri.toString());
          this._iGenerator2.doGenerate(resource, fsa, null);
        }
      }
      _xblockexpression = null;
    }
    return _xblockexpression;
  }
  
  private void addDerivedMarkerInstaller(final EclipseResourceFileSystemAccess2 fsa, final String source) {
    fsa.setPostProcessor(new EclipseResourceFileSystemAccess2.IFileCallback() {
      @Override
      public void afterFileCreation(final IFile file) {
        this.afterFileUpdate(file);
      }
      
      @Override
      public void afterFileUpdate(final IFile file) {
        try {
          VisualizationHandler.this._derivedResourceMarkers.installMarker(file, source);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
      
      @Override
      public boolean beforeFileDeletion(final IFile file) {
        return false;
      }
    });
  }
}
