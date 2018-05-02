/**
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.example.fowlerdsl.tests;

import javax.inject.Inject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.example.fowlerdsl.tests.StatemachineInjectorProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.testing.CompilationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(StatemachineInjectorProvider.class)
@SuppressWarnings("all")
public class StatemachineDotGeneratorTest {
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
      _builder_1.append("node[shape=Mrecord width=1.5 style=filled fillcolor=green2]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// states (nodes)");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// transitions (edges)");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// reset events");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("resetEvents[shape=record style=\"bold, dashed, filled\" fillcolor=moccasin label=\"Reset Events:\\n\"]");
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
      _builder.append("events");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("doorClosed\t\tD1CL");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("drawerOpened\tD2OP");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("lightOn\t\t\tL1ON");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("doorOpened\t\tD1OP");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("panelClosed \tPNCL");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      _builder.newLine();
      _builder.append("resetEvents");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("doorOpened");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      _builder.newLine();
      _builder.append("commands");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("unlockPanel PNUL");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("lockPanel\tNLK");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("lockDoor\tD1LK");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("unlockDoor\tD1UL");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      _builder.newLine();
      _builder.append("state idle");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("actions {unlockDoor lockPanel}");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("doorClosed => active");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      _builder.newLine();
      _builder.append("state active");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("drawerOpened => waitingForLight");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("lightOn\t\t => waitingForDrawer");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      _builder.newLine();
      _builder.append("state waitingForLight");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("lightOn => unlockedPanel");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      _builder.newLine();
      _builder.append("state waitingForDrawer");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("drawerOpened => unlockedPanel");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      _builder.newLine();
      _builder.append("state unlockedPanel");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("actions {unlockPanel lockDoor}");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("panelClosed => idle");
      _builder.newLine();
      _builder.append("end");
      _builder.newLine();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("digraph {");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("node[shape=Mrecord width=1.5 style=filled fillcolor=green2]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// states (nodes)");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("idle[label=\"{idle|unlockDoor\\llockPanel\\l}\"]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("active");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("waitingForLight");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("waitingForDrawer");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("unlockedPanel[label=\"{unlockedPanel|unlockPanel\\llockDoor\\l}\"]");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// transitions (edges)");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("idle -> active [label=doorClosed]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("active -> waitingForLight [label=drawerOpened]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("active -> waitingForDrawer [label=lightOn]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("waitingForLight -> unlockedPanel [label=lightOn]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("waitingForDrawer -> unlockedPanel [label=drawerOpened]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("unlockedPanel -> idle [label=panelClosed]");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("// reset events");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("resetEvents[shape=record style=\"bold, dashed, filled\" fillcolor=moccasin label=\"Reset Events:\\ndoorOpened\"]");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      this._compilationTestHelper.assertCompilesTo(_builder, _builder_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
