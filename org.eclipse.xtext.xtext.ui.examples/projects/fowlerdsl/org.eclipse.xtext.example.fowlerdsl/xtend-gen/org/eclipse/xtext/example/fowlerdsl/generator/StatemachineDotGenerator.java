/**
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.example.fowlerdsl.generator;

import com.google.common.collect.Iterables;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Command;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Event;
import org.eclipse.xtext.example.fowlerdsl.statemachine.State;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Transition;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class StatemachineDotGenerator extends AbstractGenerator {
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    EObject _head = IterableExtensions.<EObject>head(input.getContents());
    fsa.generateFile(this.fileName(input), this.toDot(((Statemachine) _head)));
  }
  
  private CharSequence toDot(final Statemachine it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("digraph {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("node[shape=Mrecord width=1.5 style=filled fillcolor=green2]");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// states (nodes)");
    _builder.newLine();
    {
      EList<State> _states = it.getStates();
      for(final State state : _states) {
        _builder.append("\t");
        String _name = state.getName();
        _builder.append(_name, "\t");
        {
          boolean _isEmpty = state.getActions().isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            CharSequence _label = this.label(state);
            _builder.append(_label, "\t");
          }
        }
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// transitions (edges)");
    _builder.newLine();
    {
      Iterable<Transition> _transitions = this.transitions(it);
      for(final Transition transition : _transitions) {
        _builder.append("\t");
        String _source = this.source(transition);
        _builder.append(_source, "\t");
        _builder.append(" -> ");
        String _target = this.target(transition);
        _builder.append(_target, "\t");
        _builder.append(" [label=");
        String _name_1 = transition.getEvent().getName();
        _builder.append(_name_1, "\t");
        _builder.append("]");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// reset events");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("resetEvents[shape=record style=\"bold, dashed, filled\" fillcolor=moccasin label=\"");
    CharSequence _resetEventsLabel = this.resetEventsLabel(it);
    _builder.append(_resetEventsLabel, "\t");
    _builder.append("\"]");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private Iterable<Transition> transitions(final Statemachine it) {
    final Function1<State, EList<Transition>> _function = (State it_1) -> {
      return it_1.getTransitions();
    };
    return Iterables.<Transition>concat(ListExtensions.<State, EList<Transition>>map(it.getStates(), _function));
  }
  
  private CharSequence label(final State it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("[label=\"{");
    String _name = it.getName();
    _builder.append(_name);
    _builder.append("|");
    {
      EList<Command> _actions = it.getActions();
      for(final Command action : _actions) {
        String _name_1 = action.getName();
        _builder.append(_name_1);
        _builder.append("\\l");
      }
    }
    _builder.append("}\"]");
    return _builder;
  }
  
  private String source(final Transition it) {
    EObject _eContainer = it.eContainer();
    return ((State) _eContainer).getName();
  }
  
  private String target(final Transition it) {
    return it.getState().getName();
  }
  
  private CharSequence resetEventsLabel(final Statemachine it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Reset Events:\\n");
    {
      EList<Event> _resetEvents = it.getResetEvents();
      boolean _hasElements = false;
      for(final Event event : _resetEvents) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("\\n", "");
        }
        String _name = event.getName();
        _builder.append(_name);
      }
    }
    return _builder;
  }
  
  private String fileName(final Resource res) {
    String _replace = IterableExtensions.join(IterableExtensions.<String>drop(((Iterable<String>)Conversions.doWrapArray(res.getURI().segments())), 3), "/").replace(".", "_");
    return (_replace + ".dot");
  }
}
