/**
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.example.homeautomation.tests.visualization;

import javax.inject.Inject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.example.homeautomation.tests.RuleEngineInjectorProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.testing.CompilationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(RuleEngineInjectorProvider.class)
@SuppressWarnings("all")
public class RuleEngineDotGeneratorTest {
  @Inject
  @Extension
  private CompilationTestHelper _compilationTestHelper;
  
  @Test
  public void test01() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("digraph {");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("node[shape=Mrecord style=\"bold, filled\" fillcolor=\"#3974B1\"]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// nodes");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// edges");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      this._compilationTestHelper.assertCompilesTo(_builder, _builder_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void test02() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Device Window can be open");
      _builder.newLine();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("digraph {");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("node[shape=Mrecord style=\"bold, filled\" fillcolor=\"#3974B1\"]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// nodes");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("subgraph cluster_Window {");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("label = \"Window\"");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.open\" [label=open]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// edges");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      this._compilationTestHelper.assertCompilesTo(_builder, _builder_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void test03() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Device Window can be open");
      _builder.newLine();
      _builder.newLine();
      _builder.append("Rule \"rule1\" when Window.open then");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("fire(Window.open)");
      _builder.newLine();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("digraph {");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("node[shape=Mrecord style=\"bold, filled\" fillcolor=\"#3974B1\"]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// nodes");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("subgraph cluster_Window {");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("label = \"Window\"");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.open\" [label=open]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// edges");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("\"Window.open\" -> \"Window.open\" [color=gold]");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      this._compilationTestHelper.assertCompilesTo(_builder, _builder_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void test04() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Device Window can be open, closed");
      _builder.newLine();
      _builder.append("Device Heater can be on, off, error");
      _builder.newLine();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("digraph {");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("node[shape=Mrecord style=\"bold, filled\" fillcolor=\"#3974B1\"]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// nodes");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("subgraph cluster_Window {");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("label = \"Window\"");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.open\" [label=open]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.closed\" [label=closed]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("// invisible edges to influence the layouting");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.open\" -> \"Window.closed\" [style=invis]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("subgraph cluster_Heater {");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("label = \"Heater\"");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.on\" [label=on]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.off\" [label=off]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.error\" [label=error]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("// invisible edges to influence the layouting");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.on\" -> \"Heater.off\" -> \"Heater.error\" [style=invis]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// edges");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      this._compilationTestHelper.assertCompilesTo(_builder, _builder_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void test05() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Device Window can be open, closed");
      _builder.newLine();
      _builder.append("Device Heater can be on, off, error");
      _builder.newLine();
      _builder.newLine();
      _builder.append("Rule \"rule1\" when Window.open then");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("fire(Heater.off)");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("Rule \'rule2\' when Heater.on then");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("fire(Window.closed)");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("Rule \'rule3\' when Window.closed then");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("fire(Heater.on)");
      _builder.newLine();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("digraph {");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("node[shape=Mrecord style=\"bold, filled\" fillcolor=\"#3974B1\"]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// nodes");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("subgraph cluster_Window {");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("label = \"Window\"");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.open\" [label=open]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.closed\" [label=closed]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("// invisible edges to influence the layouting");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.open\" -> \"Window.closed\" [style=invis]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("subgraph cluster_Heater {");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("label = \"Heater\"");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.on\" [label=on]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.off\" [label=off]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.error\" [label=error]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("// invisible edges to influence the layouting");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.on\" -> \"Heater.off\" -> \"Heater.error\" [style=invis]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// edges");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("\"Window.open\" -> \"Heater.off\" [arrowhead=onormal]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("\"Heater.on\" -> \"Window.closed\" [arrowhead=onormal]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("\"Window.closed\" -> \"Heater.on\" [arrowhead=onormal]");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      this._compilationTestHelper.assertCompilesTo(_builder, _builder_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void test06() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Device Window can be open, closed");
      _builder.newLine();
      _builder.append("Device Heater can be on, off, error");
      _builder.newLine();
      _builder.newLine();
      _builder.append("Rule \"rule1\" when Window.open then");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("fire(Heater.off)");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("Rule \'rule2\' when Heater.on then");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("fire(Window.closed)");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("Rule \'rule3\' when Window.closed then");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("fire(Heater.on)");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("Rule \'rule3\' when Window.open then");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("fire(Window.open)");
      _builder.newLine();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("digraph {");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("node[shape=Mrecord style=\"bold, filled\" fillcolor=\"#3974B1\"]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// nodes");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("subgraph cluster_Window {");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("label = \"Window\"");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.open\" [label=open]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.closed\" [label=closed]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("// invisible edges to influence the layouting");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Window.open\" -> \"Window.closed\" [style=invis]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("subgraph cluster_Heater {");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("label = \"Heater\"");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.on\" [label=on]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.off\" [label=off]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.error\" [label=error]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("// invisible edges to influence the layouting");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("\"Heater.on\" -> \"Heater.off\" -> \"Heater.error\" [style=invis]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// edges");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("\"Window.open\" -> \"Heater.off\" [arrowhead=onormal]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("\"Heater.on\" -> \"Window.closed\" [arrowhead=onormal]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("\"Window.closed\" -> \"Heater.on\" [arrowhead=onormal]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("\"Window.open\" -> \"Window.open\" [color=gold]");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      this._compilationTestHelper.assertCompilesTo(_builder, _builder_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
