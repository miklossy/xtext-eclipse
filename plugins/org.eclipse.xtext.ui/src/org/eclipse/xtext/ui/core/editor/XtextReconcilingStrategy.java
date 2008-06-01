/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.xtext.ui.core.editor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.xtext.parser.IParseError;
import org.eclipse.xtext.ui.core.internal.XtextMarkerManager;

/**
 * @author Dennis H�bner - Initial contribution and API
 * 
 */
public class XtextReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {

	private IDocument document;
	private IProgressMonitor monitor;
	private BaseTextEditor editor;

	public XtextReconcilingStrategy(BaseTextEditor editor) {
		this.editor = editor;
	}

	public void reconcile(IRegion partition) {
		IResource resource = (IResource) editor.getEditorInput().getAdapter(IFile.class);
		XtextMarkerManager.clearXtextMarker(resource, monitor);
		if (editor.getModel().hasErrors()) {
			for (IParseError error : editor.getModel().getErrors()) {
				XtextMarkerManager.createMarker(resource, collectMarkerAttributes(error), monitor);
			}
		}
	}

	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		reconcile(dirtyRegion);
	}

	public void setDocument(IDocument document) {
		Assert.isLegal(document != null);
		this.document = document;
	}

	public void initialReconcile() {
		reconcile(new Region(0, document.getLength()));
	}

	public void setProgressMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	private Map<String, Object> collectMarkerAttributes(IParseError error) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IMarker.SEVERITY, Integer.valueOf(IMarker.SEVERITY_ERROR));
		map.put(IMarker.LINE_NUMBER, Integer.valueOf(error.getLine()));
		map.put(IMarker.CHAR_START, Integer.valueOf(error.getOffset()));
		map.put(IMarker.CHAR_END, Integer.valueOf(error.getOffset() + error.getLength()));
		map.put(IMarker.MESSAGE, error.getMessage());
		map.put(IMarker.PRIORITY, Integer.valueOf(IMarker.PRIORITY_LOW));
		return map;

	}
}
