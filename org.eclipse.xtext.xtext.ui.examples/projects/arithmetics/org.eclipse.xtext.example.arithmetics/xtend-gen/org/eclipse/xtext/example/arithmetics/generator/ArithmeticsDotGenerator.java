/**
 * Copyright (c) 2015, 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.example.arithmetics.generator;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.example.arithmetics.arithmetics.Definition;
import org.eclipse.xtext.example.arithmetics.arithmetics.Div;
import org.eclipse.xtext.example.arithmetics.arithmetics.Evaluation;
import org.eclipse.xtext.example.arithmetics.arithmetics.Expression;
import org.eclipse.xtext.example.arithmetics.arithmetics.FunctionCall;
import org.eclipse.xtext.example.arithmetics.arithmetics.Minus;
import org.eclipse.xtext.example.arithmetics.arithmetics.Multi;
import org.eclipse.xtext.example.arithmetics.arithmetics.NumberLiteral;
import org.eclipse.xtext.example.arithmetics.arithmetics.Plus;
import org.eclipse.xtext.example.arithmetics.interpreter.Calculator;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class ArithmeticsDotGenerator extends AbstractGenerator {
  @Inject
  @Extension
  private ISerializer _iSerializer;
  
  @Extension
  private Calculator _calculator = new Calculator();
  
  private int nodeId = 1;
  
  private final Map<Expression, Integer> nodeToIdMapper = CollectionLiterals.<Expression, Integer>newHashMap();
  
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    this.nodeId = 1;
    EObject _head = IterableExtensions.<EObject>head(input.getContents());
    fsa.generateFile(this.fileName(input), this.toDot(((org.eclipse.xtext.example.arithmetics.arithmetics.Module) _head)));
  }
  
  public CharSequence toDot(final org.eclipse.xtext.example.arithmetics.arithmetics.Module it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("digraph {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("node[shape=square style=\"bold, filled\" fillcolor=\"orange\"]");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _isEmpty = IterableExtensions.isEmpty(this.getExpressions(it));
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append("// nodes");
        _builder.newLine();
        {
          Iterable<Expression> _expressions = this.getExpressions(it);
          for(final Expression expression : _expressions) {
            _builder.append("\t");
            String _generateNode = this.generateNode(expression);
            _builder.append(_generateNode, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("// forward edges");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("edge[arrowhead=vee]");
        _builder.newLine();
        {
          Iterable<Expression> _expressions_1 = this.getExpressions(it);
          for(final Expression expression_1 : _expressions_1) {
            _builder.append("\t");
            String _generateForwardEdge = this.generateForwardEdge(expression_1);
            _builder.append(_generateForwardEdge, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("// backward edges");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("edge[arrowhead=normal color=green4 style=dashed]");
        _builder.newLine();
        {
          Iterable<Expression> _expressions_2 = this.getExpressions(it);
          for(final Expression expression_2 : _expressions_2) {
            _builder.append("\t");
            _builder.append("\t");
            String _generateBackwardEdge = this.generateBackwardEdge(expression_2);
            _builder.append(_generateBackwardEdge, "\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _isEmpty_1 = IterableExtensions.isEmpty(this.getDefinitions(it));
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("\t");
        _builder.append("// subgraphs");
        _builder.newLine();
        {
          Iterable<Definition> _definitions = this.getDefinitions(it);
          for(final Definition definition : _definitions) {
            _builder.append("\t");
            CharSequence _generateSubgraph = this.generateSubgraph(definition);
            _builder.append(_generateSubgraph, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private String _generateNode(final Plus it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it);
    _builder.append(_nodeId);
    _builder.append("[label=\"");
    CharSequence _generateNodeLabel = this.generateNodeLabel(it);
    _builder.append(_generateNodeLabel);
    _builder.append("\"");
    CharSequence _generateNodeTooltip = this.generateNodeTooltip(it);
    _builder.append(_generateNodeTooltip);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    String _generateNode = this.generateNode(it.getLeft());
    _builder.append(_generateNode);
    _builder.newLineIfNotEmpty();
    String _generateNode_1 = this.generateNode(it.getRight());
    _builder.append(_generateNode_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateNode(final Minus it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it);
    _builder.append(_nodeId);
    _builder.append("[label=\"");
    CharSequence _generateNodeLabel = this.generateNodeLabel(it);
    _builder.append(_generateNodeLabel);
    _builder.append("\"");
    CharSequence _generateNodeTooltip = this.generateNodeTooltip(it);
    _builder.append(_generateNodeTooltip);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    String _generateNode = this.generateNode(it.getLeft());
    _builder.append(_generateNode);
    _builder.newLineIfNotEmpty();
    String _generateNode_1 = this.generateNode(it.getRight());
    _builder.append(_generateNode_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateNode(final Multi it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it);
    _builder.append(_nodeId);
    _builder.append("[label=\"");
    CharSequence _generateNodeLabel = this.generateNodeLabel(it);
    _builder.append(_generateNodeLabel);
    _builder.append("\"");
    CharSequence _generateNodeTooltip = this.generateNodeTooltip(it);
    _builder.append(_generateNodeTooltip);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    String _generateNode = this.generateNode(it.getLeft());
    _builder.append(_generateNode);
    _builder.newLineIfNotEmpty();
    String _generateNode_1 = this.generateNode(it.getRight());
    _builder.append(_generateNode_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateNode(final Div it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it);
    _builder.append(_nodeId);
    _builder.append("[label=\"");
    CharSequence _generateNodeLabel = this.generateNodeLabel(it);
    _builder.append(_generateNodeLabel);
    _builder.append("\"");
    CharSequence _generateNodeTooltip = this.generateNodeTooltip(it);
    _builder.append(_generateNodeTooltip);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    String _generateNode = this.generateNode(it.getLeft());
    _builder.append(_generateNode);
    _builder.newLineIfNotEmpty();
    String _generateNode_1 = this.generateNode(it.getRight());
    _builder.append(_generateNode_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateNode(final NumberLiteral it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it);
    _builder.append(_nodeId);
    _builder.append("[label=\"");
    CharSequence _generateNodeLabel = this.generateNodeLabel(it);
    _builder.append(_generateNodeLabel);
    _builder.append("\"");
    CharSequence _generateNodeTooltip = this.generateNodeTooltip(it);
    _builder.append(_generateNodeTooltip);
    _builder.append(" shape=circle]");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateNode(final FunctionCall it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it);
    _builder.append(_nodeId);
    _builder.append("[label=\"");
    CharSequence _generateNodeLabel = this.generateNodeLabel(it);
    _builder.append(_generateNodeLabel);
    _builder.append("\"");
    CharSequence _generateNodeTooltip = this.generateNodeTooltip(it);
    _builder.append(_generateNodeTooltip);
    _builder.append(" shape=box]");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateNode(final Expression it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\'");
    String _name = it.getClass().getName();
    _builder.append(_name);
    _builder.append("\' expressions are not yet supported!");
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  private String _generateForwardEdge(final Plus it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it);
    _builder.append(_nodeId);
    _builder.append("->");
    Integer _nodeId_1 = this.getNodeId(it.getLeft());
    _builder.append(_nodeId_1);
    _builder.newLineIfNotEmpty();
    Integer _nodeId_2 = this.getNodeId(it);
    _builder.append(_nodeId_2);
    _builder.append("->");
    Integer _nodeId_3 = this.getNodeId(it.getRight());
    _builder.append(_nodeId_3);
    _builder.newLineIfNotEmpty();
    String _generateForwardEdge = this.generateForwardEdge(it.getLeft());
    _builder.append(_generateForwardEdge);
    _builder.newLineIfNotEmpty();
    String _generateForwardEdge_1 = this.generateForwardEdge(it.getRight());
    _builder.append(_generateForwardEdge_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateForwardEdge(final Minus it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it);
    _builder.append(_nodeId);
    _builder.append("->");
    Integer _nodeId_1 = this.getNodeId(it.getLeft());
    _builder.append(_nodeId_1);
    _builder.newLineIfNotEmpty();
    Integer _nodeId_2 = this.getNodeId(it);
    _builder.append(_nodeId_2);
    _builder.append("->");
    Integer _nodeId_3 = this.getNodeId(it.getRight());
    _builder.append(_nodeId_3);
    _builder.newLineIfNotEmpty();
    String _generateForwardEdge = this.generateForwardEdge(it.getLeft());
    _builder.append(_generateForwardEdge);
    _builder.newLineIfNotEmpty();
    String _generateForwardEdge_1 = this.generateForwardEdge(it.getRight());
    _builder.append(_generateForwardEdge_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateForwardEdge(final Multi it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it);
    _builder.append(_nodeId);
    _builder.append("->");
    Integer _nodeId_1 = this.getNodeId(it.getLeft());
    _builder.append(_nodeId_1);
    _builder.newLineIfNotEmpty();
    Integer _nodeId_2 = this.getNodeId(it);
    _builder.append(_nodeId_2);
    _builder.append("->");
    Integer _nodeId_3 = this.getNodeId(it.getRight());
    _builder.append(_nodeId_3);
    _builder.newLineIfNotEmpty();
    String _generateForwardEdge = this.generateForwardEdge(it.getLeft());
    _builder.append(_generateForwardEdge);
    _builder.newLineIfNotEmpty();
    String _generateForwardEdge_1 = this.generateForwardEdge(it.getRight());
    _builder.append(_generateForwardEdge_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateForwardEdge(final Div it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it);
    _builder.append(_nodeId);
    _builder.append("->");
    Integer _nodeId_1 = this.getNodeId(it.getLeft());
    _builder.append(_nodeId_1);
    _builder.newLineIfNotEmpty();
    Integer _nodeId_2 = this.getNodeId(it);
    _builder.append(_nodeId_2);
    _builder.append("->");
    Integer _nodeId_3 = this.getNodeId(it.getRight());
    _builder.append(_nodeId_3);
    _builder.newLineIfNotEmpty();
    String _generateForwardEdge = this.generateForwardEdge(it.getLeft());
    _builder.append(_generateForwardEdge);
    _builder.newLineIfNotEmpty();
    String _generateForwardEdge_1 = this.generateForwardEdge(it.getRight());
    _builder.append(_generateForwardEdge_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateForwardEdge(final NumberLiteral it) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  private String _generateForwardEdge(final FunctionCall it) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  private String _generateBackwardEdge(final Plus it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it.getLeft());
    _builder.append(_nodeId);
    _builder.append("->");
    Integer _nodeId_1 = this.getNodeId(it);
    _builder.append(_nodeId_1);
    _builder.append("[");
    CharSequence _generateBackwardEdgeLabel = this.generateBackwardEdgeLabel(it.getLeft());
    _builder.append(_generateBackwardEdgeLabel);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    Integer _nodeId_2 = this.getNodeId(it.getRight());
    _builder.append(_nodeId_2);
    _builder.append("->");
    Integer _nodeId_3 = this.getNodeId(it);
    _builder.append(_nodeId_3);
    _builder.append("[");
    CharSequence _generateBackwardEdgeLabel_1 = this.generateBackwardEdgeLabel(it.getRight());
    _builder.append(_generateBackwardEdgeLabel_1);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    String _generateBackwardEdge = this.generateBackwardEdge(it.getLeft());
    _builder.append(_generateBackwardEdge);
    _builder.newLineIfNotEmpty();
    String _generateBackwardEdge_1 = this.generateBackwardEdge(it.getRight());
    _builder.append(_generateBackwardEdge_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateBackwardEdge(final Minus it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it.getLeft());
    _builder.append(_nodeId);
    _builder.append("->");
    Integer _nodeId_1 = this.getNodeId(it);
    _builder.append(_nodeId_1);
    _builder.append("[");
    CharSequence _generateBackwardEdgeLabel = this.generateBackwardEdgeLabel(it.getLeft());
    _builder.append(_generateBackwardEdgeLabel);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    Integer _nodeId_2 = this.getNodeId(it.getRight());
    _builder.append(_nodeId_2);
    _builder.append("->");
    Integer _nodeId_3 = this.getNodeId(it);
    _builder.append(_nodeId_3);
    _builder.append("[");
    CharSequence _generateBackwardEdgeLabel_1 = this.generateBackwardEdgeLabel(it.getRight());
    _builder.append(_generateBackwardEdgeLabel_1);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    String _generateBackwardEdge = this.generateBackwardEdge(it.getLeft());
    _builder.append(_generateBackwardEdge);
    _builder.newLineIfNotEmpty();
    String _generateBackwardEdge_1 = this.generateBackwardEdge(it.getRight());
    _builder.append(_generateBackwardEdge_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateBackwardEdge(final Multi it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it.getLeft());
    _builder.append(_nodeId);
    _builder.append("->");
    Integer _nodeId_1 = this.getNodeId(it);
    _builder.append(_nodeId_1);
    _builder.append("[");
    CharSequence _generateBackwardEdgeLabel = this.generateBackwardEdgeLabel(it.getLeft());
    _builder.append(_generateBackwardEdgeLabel);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    Integer _nodeId_2 = this.getNodeId(it.getRight());
    _builder.append(_nodeId_2);
    _builder.append("->");
    Integer _nodeId_3 = this.getNodeId(it);
    _builder.append(_nodeId_3);
    _builder.append("[");
    CharSequence _generateBackwardEdgeLabel_1 = this.generateBackwardEdgeLabel(it.getRight());
    _builder.append(_generateBackwardEdgeLabel_1);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    String _generateBackwardEdge = this.generateBackwardEdge(it.getLeft());
    _builder.append(_generateBackwardEdge);
    _builder.newLineIfNotEmpty();
    String _generateBackwardEdge_1 = this.generateBackwardEdge(it.getRight());
    _builder.append(_generateBackwardEdge_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateBackwardEdge(final Div it) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _nodeId = this.getNodeId(it.getLeft());
    _builder.append(_nodeId);
    _builder.append("->");
    Integer _nodeId_1 = this.getNodeId(it);
    _builder.append(_nodeId_1);
    _builder.append("[");
    CharSequence _generateBackwardEdgeLabel = this.generateBackwardEdgeLabel(it.getLeft());
    _builder.append(_generateBackwardEdgeLabel);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    Integer _nodeId_2 = this.getNodeId(it.getRight());
    _builder.append(_nodeId_2);
    _builder.append("->");
    Integer _nodeId_3 = this.getNodeId(it);
    _builder.append(_nodeId_3);
    _builder.append("[");
    CharSequence _generateBackwardEdgeLabel_1 = this.generateBackwardEdgeLabel(it.getRight());
    _builder.append(_generateBackwardEdgeLabel_1);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    String _generateBackwardEdge = this.generateBackwardEdge(it.getLeft());
    _builder.append(_generateBackwardEdge);
    _builder.newLineIfNotEmpty();
    String _generateBackwardEdge_1 = this.generateBackwardEdge(it.getRight());
    _builder.append(_generateBackwardEdge_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  private String _generateBackwardEdge(final NumberLiteral it) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  private String _generateBackwardEdge(final FunctionCall it) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  private String _generateForwardEdge(final Expression it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\'");
    String _name = it.getClass().getName();
    _builder.append(_name);
    _builder.append("\' expressions are not yet supported!");
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  private CharSequence generateNodeLabel(final Expression it) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (it instanceof Plus) {
      _matched=true;
      _switchResult = "+";
    }
    if (!_matched) {
      if (it instanceof Minus) {
        _matched=true;
        _switchResult = "-";
      }
    }
    if (!_matched) {
      if (it instanceof Multi) {
        _matched=true;
        _switchResult = "*";
      }
    }
    if (!_matched) {
      if (it instanceof Div) {
        _matched=true;
        _switchResult = "/";
      }
    }
    if (!_matched) {
      if (it instanceof NumberLiteral) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        BigDecimal _value = ((NumberLiteral)it).getValue();
        _builder.append(_value);
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (it instanceof FunctionCall) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _trim = this._iSerializer.serialize(it).trim();
        _builder.append(_trim);
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  private CharSequence generateNodeTooltip(final Expression it) {
    CharSequence _xblockexpression = null;
    {
      final CharSequence result = this.formattedResult(it);
      CharSequence _xifexpression = null;
      if ((result != null)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(" ");
        _builder.append("tooltip=");
        _builder.append(result, " ");
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private CharSequence generateBackwardEdgeLabel(final Expression it) {
    CharSequence _xblockexpression = null;
    {
      final CharSequence result = this.formattedResult(it);
      CharSequence _xifexpression = null;
      if ((result != null)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("label=");
        _builder.append(result);
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private CharSequence formattedResult(final Expression it) {
    CharSequence _xblockexpression = null;
    {
      BigDecimal _xtrycatchfinallyexpression = null;
      try {
        _xtrycatchfinallyexpression = this._calculator.evaluate(it);
      } catch (final Throwable _t) {
        if (_t instanceof NullPointerException) {
          _xtrycatchfinallyexpression = null;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      final BigDecimal result = _xtrycatchfinallyexpression;
      CharSequence _xifexpression = null;
      if ((result != null)) {
        StringConcatenation _builder = new StringConcatenation();
        String _format = this.format(result);
        _builder.append(_format);
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private CharSequence generateSubgraph(final Definition it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("subgraph cluster_");
    String _name = it.getName();
    _builder.append(_name);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("label=\"");
    String _name_1 = it.getName();
    _builder.append(_name_1, "\t");
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generateNode = this.generateNode(it.getExpr());
    _builder.append(_generateNode, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generateForwardEdge = this.generateForwardEdge(it.getExpr());
    _builder.append(_generateForwardEdge, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private Integer getNodeId(final Expression it) {
    Integer _xblockexpression = null;
    {
      boolean _containsKey = this.nodeToIdMapper.containsKey(it);
      boolean _not = (!_containsKey);
      if (_not) {
        int _plusPlus = this.nodeId++;
        this.nodeToIdMapper.put(it, Integer.valueOf(_plusPlus));
      }
      _xblockexpression = this.nodeToIdMapper.get(it);
    }
    return _xblockexpression;
  }
  
  private Iterable<Expression> getExpressions(final org.eclipse.xtext.example.arithmetics.arithmetics.Module it) {
    Iterable<Expression> _xifexpression = null;
    if ((it == null)) {
      _xifexpression = CollectionLiterals.<Expression>newArrayList();
    } else {
      final Function1<Evaluation, Expression> _function = (Evaluation it_1) -> {
        return it_1.getExpression();
      };
      _xifexpression = IterableExtensions.<Evaluation, Expression>map(Iterables.<Evaluation>filter(it.getStatements(), Evaluation.class), _function);
    }
    return _xifexpression;
  }
  
  private Iterable<Definition> getDefinitions(final org.eclipse.xtext.example.arithmetics.arithmetics.Module it) {
    Iterable<Definition> _xifexpression = null;
    if ((it == null)) {
      _xifexpression = CollectionLiterals.<Definition>newArrayList();
    } else {
      _xifexpression = Iterables.<Definition>filter(it.getStatements(), Definition.class);
    }
    return _xifexpression;
  }
  
  private String format(final BigDecimal it) {
    String _xblockexpression = null;
    {
      final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
      _xblockexpression = new DecimalFormat("#0.##", symbols).format(it);
    }
    return _xblockexpression;
  }
  
  private String fileName(final Resource res) {
    String _replace = IterableExtensions.join(IterableExtensions.<String>drop(((Iterable<String>)Conversions.doWrapArray(res.getURI().segments())), 3), "/").replace(".", "_");
    return (_replace + ".dot");
  }
  
  private String generateNode(final Expression it) {
    if (it instanceof Div) {
      return _generateNode((Div)it);
    } else if (it instanceof FunctionCall) {
      return _generateNode((FunctionCall)it);
    } else if (it instanceof Minus) {
      return _generateNode((Minus)it);
    } else if (it instanceof Multi) {
      return _generateNode((Multi)it);
    } else if (it instanceof NumberLiteral) {
      return _generateNode((NumberLiteral)it);
    } else if (it instanceof Plus) {
      return _generateNode((Plus)it);
    } else if (it != null) {
      return _generateNode(it);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(it).toString());
    }
  }
  
  private String generateForwardEdge(final Expression it) {
    if (it instanceof Div) {
      return _generateForwardEdge((Div)it);
    } else if (it instanceof FunctionCall) {
      return _generateForwardEdge((FunctionCall)it);
    } else if (it instanceof Minus) {
      return _generateForwardEdge((Minus)it);
    } else if (it instanceof Multi) {
      return _generateForwardEdge((Multi)it);
    } else if (it instanceof NumberLiteral) {
      return _generateForwardEdge((NumberLiteral)it);
    } else if (it instanceof Plus) {
      return _generateForwardEdge((Plus)it);
    } else if (it != null) {
      return _generateForwardEdge(it);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(it).toString());
    }
  }
  
  private String generateBackwardEdge(final Expression it) {
    if (it instanceof Div) {
      return _generateBackwardEdge((Div)it);
    } else if (it instanceof FunctionCall) {
      return _generateBackwardEdge((FunctionCall)it);
    } else if (it instanceof Minus) {
      return _generateBackwardEdge((Minus)it);
    } else if (it instanceof Multi) {
      return _generateBackwardEdge((Multi)it);
    } else if (it instanceof NumberLiteral) {
      return _generateBackwardEdge((NumberLiteral)it);
    } else if (it instanceof Plus) {
      return _generateBackwardEdge((Plus)it);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(it).toString());
    }
  }
}
