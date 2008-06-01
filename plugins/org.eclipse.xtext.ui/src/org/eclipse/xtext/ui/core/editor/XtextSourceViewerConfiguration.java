/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.xtext.ui.core.editor;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.AbstractReusableInformationControlCreator;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.texteditor.HippieProposalProcessor;
import org.eclipse.xtext.service.ILanguageDescriptor;
import org.eclipse.xtext.service.ServiceRegistry;
import org.eclipse.xtext.ui.core.editor.codecompletion.XtextContentAssistProcessor;
import org.eclipse.xtext.ui.core.editor.model.XtextEditorModelReconcileStrategy;
import org.eclipse.xtext.ui.core.service.IProposalsProvider;

/**
 * @author Dennis H�bner - Initial contribution and API
 * 
 */
public class XtextSourceViewerConfiguration extends TextSourceViewerConfiguration {
	private final BaseTextEditor editor;
	private final ILanguageDescriptor languageDescriptor;

	public XtextSourceViewerConfiguration(ILanguageDescriptor languageDescriptor, IPreferenceStore preferenceStore,
			BaseTextEditor editor) {
		super(preferenceStore);
		this.languageDescriptor = languageDescriptor;
		this.editor = editor;
	}

	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		MonoReconciler reconciler = new MonoReconciler(new XtextEditorModelReconcileStrategy(editor), false);
		reconciler.setDelay(500);
		return reconciler;
//		 return new MonoReconciler(new XtextReconcilingStrategy(editor), false);
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		IProposalsProvider proposalsProvider = ServiceRegistry.getService(languageDescriptor, IProposalsProvider.class);
		IContentAssistProcessor processor;
		if (proposalsProvider != null) {
			processor = new XtextContentAssistProcessor(proposalsProvider);
		}
		else {
			processor = new HippieProposalProcessor();
		}
		ContentAssistant ca = new ContentAssistant();
		ca.enableColoredLabels(true);
		ca.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
		ca.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		ca.setProposalPopupOrientation(IContentAssistant.PROPOSAL_STACKED);
		ca.setInformationControlCreator(new AbstractReusableInformationControlCreator() {
			@Override
			protected IInformationControl doCreateInformationControl(Shell parent) {
				return new DefaultInformationControl(parent, false);
			}
		});
		return ca;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = (PresentationReconciler) super.getPresentationReconciler(sourceViewer);
		DefaultDamagerRepairer defDR = new DefaultDamagerRepairer(new XtextTokenScanner(languageDescriptor,
				fPreferenceStore));
		reconciler.setRepairer(defDR, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setDamager(defDR, IDocument.DEFAULT_CONTENT_TYPE);
		return reconciler;
	}

}
