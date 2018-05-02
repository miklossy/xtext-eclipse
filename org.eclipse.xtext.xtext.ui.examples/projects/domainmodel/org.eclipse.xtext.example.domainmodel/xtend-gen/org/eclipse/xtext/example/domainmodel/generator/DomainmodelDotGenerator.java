/**
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.example.domainmodel.generator;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmParameterizedTypeReference;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.example.domainmodel.domainmodel.DomainModel;
import org.eclipse.xtext.example.domainmodel.domainmodel.Entity;
import org.eclipse.xtext.example.domainmodel.domainmodel.Operation;
import org.eclipse.xtext.example.domainmodel.domainmodel.Property;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class DomainmodelDotGenerator extends JvmModelGenerator {
  @Inject
  @Extension
  private ISerializer _iSerializer;
  
  @Inject
  @Extension
  private IJvmModelAssociations _iJvmModelAssociations;
  
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    EObject _head = IterableExtensions.<EObject>head(input.getContents());
    fsa.generateFile(this.fileName(input), this.toDot(((DomainModel) _head)));
  }
  
  public CharSequence toDot(final DomainModel it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("digraph {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// layout=sfdp");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("nodesep=1.2");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("rankdir=BT");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _generateEntities = this.generateEntities(it);
    _builder.append(_generateEntities, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _generateInheritanceConnections = this.generateInheritanceConnections(it);
    _builder.append(_generateInheritanceConnections, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _generateAssociationConnections = this.generateAssociationConnections(it);
    _builder.append(_generateAssociationConnections, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence generateEntities(final DomainModel it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("node [shape=record style=filled fillcolor=\"#FAEAC1\"]");
    _builder.newLine();
    _builder.newLine();
    _builder.append("// nodes");
    _builder.newLine();
    {
      Iterable<Entity> _entities = this.entities(it);
      for(final Entity entity : _entities) {
        CharSequence _generate = this.generate(entity);
        _builder.append(_generate);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence generate(final Entity it) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = it.getName();
    _builder.append(_name);
    _builder.append(" [");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("label = \"{");
    _builder.newLine();
    _builder.append("\t\t");
    String _name_1 = it.getName();
    _builder.append(_name_1, "\t\t");
    _builder.append("|");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    CharSequence _generateProperties = this.generateProperties(it);
    _builder.append(_generateProperties, "\t\t");
    _builder.append("|");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    CharSequence _generateOperations = this.generateOperations(it);
    _builder.append(_generateOperations, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}\"");
    _builder.newLine();
    _builder.append("]");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence generateProperties(final Entity it) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Iterable<Property> _memberProperties = this.memberProperties(it);
      for(final Property memberProperty : _memberProperties) {
        CharSequence _memberLabel = this.memberLabel(memberProperty);
        _builder.append(_memberLabel);
        _builder.append("\\l");
      }
    }
    return _builder;
  }
  
  private CharSequence generateOperations(final Entity it) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Iterable<Operation> _operations = this.operations(it);
      for(final Operation operation : _operations) {
        CharSequence _operationLabel = this.operationLabel(operation);
        _builder.append(_operationLabel);
        _builder.append("\\l");
      }
    }
    return _builder;
  }
  
  private CharSequence generateInheritanceConnections(final DomainModel it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// inheritance edges");
    _builder.newLine();
    _builder.append("edge[arrowhead=onormal]");
    _builder.newLine();
    {
      Iterable<Entity> _entities = this.entities(it);
      for(final Entity entity : _entities) {
        {
          JvmParameterizedTypeReference _superType = entity.getSuperType();
          boolean _tripleNotEquals = (_superType != null);
          if (_tripleNotEquals) {
            String _name = entity.getName();
            _builder.append(_name);
            _builder.append(" -> ");
            String _simpleName = entity.getSuperType().getSimpleName();
            _builder.append(_simpleName);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  private CharSequence generateAssociationConnections(final DomainModel it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// association edges");
    _builder.newLine();
    _builder.append("edge[arrowhead=normal arrowtail=diamond dir=both constraint=false]");
    _builder.newLine();
    {
      Iterable<Entity> _entities = this.entities(it);
      for(final Entity entity : _entities) {
        {
          Iterable<Property> _associationProperties = this.associationProperties(entity);
          for(final Property property : _associationProperties) {
            String _name = entity.getName();
            _builder.append(_name);
            _builder.append(" -> ");
            String _simpleName = this.determineType(property.getType()).getSimpleName();
            _builder.append(_simpleName);
            _builder.append(" [headlabel=\"");
            CharSequence _associationLabel = this.associationLabel(property);
            _builder.append(_associationLabel);
            _builder.append("\"]");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  private Iterable<Entity> entities(final DomainModel it) {
    Iterable<Entity> _xifexpression = null;
    if ((it == null)) {
      _xifexpression = CollectionLiterals.<Entity>newArrayList();
    } else {
      _xifexpression = Iterables.<Entity>filter(it.getElements(), Entity.class);
    }
    return _xifexpression;
  }
  
  private Iterable<Property> properties(final Entity it) {
    return Iterables.<Property>filter(it.getFeatures(), Property.class);
  }
  
  private Iterable<Operation> operations(final Entity it) {
    return Iterables.<Operation>filter(it.getFeatures(), Operation.class);
  }
  
  private Iterable<Property> memberProperties(final Entity it) {
    final Function1<Property, Boolean> _function = (Property it_1) -> {
      boolean _hasEntityType = this.hasEntityType(it_1);
      return Boolean.valueOf((!_hasEntityType));
    };
    return IterableExtensions.<Property>filter(this.properties(it), _function);
  }
  
  private Iterable<Property> associationProperties(final Entity it) {
    final Function1<Property, Boolean> _function = (Property it_1) -> {
      return Boolean.valueOf(this.hasEntityType(it_1));
    };
    return IterableExtensions.<Property>filter(this.properties(it), _function);
  }
  
  private boolean hasEntityType(final Property it) {
    EObject _primarySourceElement = this._iJvmModelAssociations.getPrimarySourceElement(this.determineType(it.getType()).getType());
    return (_primarySourceElement instanceof Entity);
  }
  
  private CharSequence memberLabel(final Property it) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = it.getName();
    _builder.append(_name);
    _builder.append(" : ");
    String _name_1 = this.name(it.getType());
    _builder.append(_name_1);
    return _builder;
  }
  
  private CharSequence associationLabel(final Property it) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = it.getName();
    _builder.append(_name);
    _builder.append("\\n");
    CharSequence _cardinality = this.cardinality(it.getType());
    _builder.append(_cardinality);
    return _builder;
  }
  
  private CharSequence operationLabel(final Operation it) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = it.getName();
    _builder.append(_name);
    _builder.append("(");
    CharSequence _parameters = this.parameters(it);
    _builder.append(_parameters);
    _builder.append(") : ");
    String _name_1 = this.name(it.getType());
    _builder.append(_name_1);
    return _builder;
  }
  
  private String name(final JvmTypeReference it) {
    return this.mask(it.getSimpleName());
  }
  
  private CharSequence parameters(final Operation it) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<JvmFormalParameter> _params = it.getParams();
      boolean _hasElements = false;
      for(final JvmFormalParameter param : _params) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "");
        }
        String _mask = this.mask(this._iSerializer.serialize(param));
        _builder.append(_mask);
      }
    }
    return _builder;
  }
  
  private String mask(final String text) {
    return text.replaceAll("\\<", "\\\\<").replaceAll("\\>", "\\\\>");
  }
  
  private CharSequence cardinality(final JvmTypeReference it) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _many = this.many(it);
      if (_many) {
        _builder.append("[0..*]");
      } else {
        _builder.append(1);
      }
    }
    return _builder;
  }
  
  private JvmTypeReference determineType(final JvmTypeReference it) {
    JvmTypeReference _xblockexpression = null;
    {
      final JvmParameterizedTypeReference jvmParameterizedTypeReference = ((JvmParameterizedTypeReference) it);
      JvmTypeReference _xifexpression = null;
      boolean _isEmpty = jvmParameterizedTypeReference.getArguments().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _xifexpression = IterableExtensions.<JvmTypeReference>head(jvmParameterizedTypeReference.getArguments());
      } else {
        _xifexpression = jvmParameterizedTypeReference;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private boolean many(final JvmTypeReference it) {
    String _identifier = it.getType().getIdentifier();
    return Objects.equal(_identifier, "java.util.List");
  }
  
  private String fileName(final Resource res) {
    String _replace = IterableExtensions.join(IterableExtensions.<String>drop(((Iterable<String>)Conversions.doWrapArray(res.getURI().segments())), 3), "/").replace(".", "_");
    return (_replace + ".dot");
  }
}
