/**
 * Copyright (c) 2011, 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.example.domainmodel;

import org.eclipse.xtext.example.domainmodel.AbstractDomainmodelRuntimeModule;
import org.eclipse.xtext.example.domainmodel.generator.DomainmodelDotGenerator;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.resource.persistence.IResourceStorageFacade;
import org.eclipse.xtext.xbase.resource.BatchLinkableResourceStorageFacade;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
@SuppressWarnings("all")
public class DomainmodelRuntimeModule extends AbstractDomainmodelRuntimeModule {
  public Class<? extends IResourceStorageFacade> bindResourceStorageFacade() {
    return BatchLinkableResourceStorageFacade.class;
  }
  
  @Override
  public Class<? extends IGenerator> bindIGenerator() {
    return DomainmodelDotGenerator.class;
  }
}
