/**
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.example.homeautomation.generator;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.example.homeautomation.ruleEngine.Device;
import org.eclipse.xtext.example.homeautomation.ruleEngine.Model;
import org.eclipse.xtext.example.homeautomation.ruleEngine.Rule;
import org.eclipse.xtext.example.homeautomation.ruleEngine.State;
import org.eclipse.xtext.example.homeautomation.validation.RuleEngineValidator;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.xbase.XBlockExpression;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.XFeatureCall;
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class RuleEngineDotGenerator extends JvmModelGenerator {
  @Inject
  @Extension
  private ISerializer _iSerializer;
  
  @Inject
  @Extension
  private RuleEngineValidator _ruleEngineValidator;
  
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    EObject _head = IterableExtensions.<EObject>head(input.getContents());
    fsa.generateFile(this.fileName(input), this.toDot(((Model) _head)));
  }
  
  public CharSequence toDot(final Model it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("digraph {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("node[shape=Mrecord style=\"bold, filled\" fillcolor=\"#3974B1\"]");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// nodes");
    _builder.newLine();
    {
      Iterable<Device> _devices = this.devices(it);
      for(final Device device : _devices) {
        _builder.append("\t");
        CharSequence _generate = this.generate(device);
        _builder.append(_generate, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("// edges");
    _builder.newLine();
    {
      Iterable<Rule> _rules = this.rules(it);
      for(final Rule rule : _rules) {
        _builder.append("\t");
        CharSequence _generate_1 = this.generate(rule);
        _builder.append(_generate_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private Iterable<Device> devices(final Model it) {
    Iterable<Device> _xifexpression = null;
    if ((it == null)) {
      _xifexpression = CollectionLiterals.<Device>newArrayList();
    } else {
      _xifexpression = Iterables.<Device>filter(it.getDeclarations(), Device.class);
    }
    return _xifexpression;
  }
  
  private CharSequence generate(final Device it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("subgraph cluster_");
    String _name = it.getName();
    _builder.append(_name);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("label = \"");
    String _name_1 = it.getName();
    _builder.append(_name_1, "\t");
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    {
      EList<State> _states = it.getStates();
      for(final State state : _states) {
        _builder.append("\t");
        CharSequence _generate = this.generate(state);
        _builder.append(_generate, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _generateInvisibleEdges = this.generateInvisibleEdges(it);
    _builder.append(_generateInvisibleEdges, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence generate(final State it) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _nodeID = this.nodeID(it);
    _builder.append(_nodeID);
    _builder.append(" [label=");
    String _name = it.getName();
    _builder.append(_name);
    _builder.append("]");
    return _builder;
  }
  
  private CharSequence nodeID(final State it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"");
    EObject _eContainer = it.eContainer();
    String _name = ((Device) _eContainer).getName();
    _builder.append(_name);
    _builder.append(".");
    String _name_1 = it.getName();
    _builder.append(_name_1);
    _builder.append("\"");
    return _builder;
  }
  
  private CharSequence generateInvisibleEdges(final Device it) {
    StringConcatenation _builder = new StringConcatenation();
    {
      int _length = ((Object[])Conversions.unwrapArray(it.getStates(), Object.class)).length;
      boolean _greaterThan = (_length > 1);
      if (_greaterThan) {
        _builder.newLine();
        _builder.append("// invisible edges to influence the layouting");
        _builder.newLine();
        final Function1<State, CharSequence> _function = (State it_1) -> {
          return this.nodeID(it_1);
        };
        String _join = IterableExtensions.join(ListExtensions.<State, CharSequence>map(it.getStates(), _function), " -> ");
        _builder.append(_join);
        _builder.append(" [style=invis]");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private Iterable<Rule> rules(final Model it) {
    Iterable<Rule> _xifexpression = null;
    if ((it == null)) {
      _xifexpression = CollectionLiterals.<Rule>newArrayList();
    } else {
      _xifexpression = Iterables.<Rule>filter(it.getDeclarations(), Rule.class);
    }
    return _xifexpression;
  }
  
  private CharSequence generate(final Rule it) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _source = this.source(it);
    _builder.append(_source);
    _builder.append(" -> ");
    CharSequence _target = this.target(it);
    _builder.append(_target);
    _builder.append(" [");
    CharSequence _attributes = this.attributes(it);
    _builder.append(_attributes);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence source(final Rule it) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _nodeID = this.nodeID(it.getDeviceState());
    _builder.append(_nodeID);
    return _builder;
  }
  
  private CharSequence target(final Rule it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"");
    String _serialize = this._iSerializer.serialize(IterableExtensions.<XExpression>head(this.firstFeatureCall(it).getActualArguments()));
    _builder.append(_serialize);
    _builder.append("\"");
    return _builder;
  }
  
  private CharSequence attributes(final Rule it) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isRecursive = this._ruleEngineValidator.isRecursive(this.firstFeatureCall(it));
      if (_isRecursive) {
        _builder.append("color=gold");
      } else {
        _builder.append("arrowhead=onormal");
      }
    }
    return _builder;
  }
  
  private XFeatureCall firstFeatureCall(final Rule it) {
    XExpression _thenPart = it.getThenPart();
    XExpression _head = IterableExtensions.<XExpression>head(((XBlockExpression) _thenPart).getExpressions());
    return ((XFeatureCall) _head);
  }
  
  private String fileName(final Resource res) {
    String _replace = IterableExtensions.join(IterableExtensions.<String>drop(((Iterable<String>)Conversions.doWrapArray(res.getURI().segments())), 3), "/").replace(".", "_");
    return (_replace + ".dot");
  }
}
