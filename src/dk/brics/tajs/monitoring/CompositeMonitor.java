/*
 * Copyright 2009-2020 Aarhus University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dk.brics.tajs.monitoring;

import dk.brics.tajs.analysis.Solver;
import dk.brics.tajs.flowgraph.AbstractNode;
import dk.brics.tajs.flowgraph.BasicBlock;
import dk.brics.tajs.flowgraph.Function;
import dk.brics.tajs.flowgraph.SourceLocation;
import dk.brics.tajs.flowgraph.jsnodes.IfNode;
import dk.brics.tajs.flowgraph.jsnodes.Node;
import dk.brics.tajs.flowgraph.jsnodes.ReadPropertyNode;
import dk.brics.tajs.flowgraph.jsnodes.ReadVariableNode;
import dk.brics.tajs.lattice.Context;
import dk.brics.tajs.lattice.HostObject;
import dk.brics.tajs.lattice.ObjectLabel;
import dk.brics.tajs.lattice.StringOrSymbol;
import dk.brics.tajs.lattice.State;
import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.solver.BlockAndContext;
import dk.brics.tajs.solver.ISolverMonitoring;
import dk.brics.tajs.solver.Message;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static dk.brics.tajs.util.Collections.newList;

/**
 * Composite, delegating, implementation of IAnalysisMonitoring.
 * Enables multiple, independent IAnalysisMonitoring implementations to be used together.
 */
public class CompositeMonitor implements IAnalysisMonitoring {

    private final List<IAnalysisMonitoring> monitors;

    private CompositeMonitor() {
        monitors = newList();
    }

    public static CompositeMonitor make(List<IAnalysisMonitoring> monitors) {
        CompositeMonitor c = new CompositeMonitor();
        for (IAnalysisMonitoring m : monitors)
            if (m instanceof CompositeMonitor)
                c.monitors.addAll(((CompositeMonitor)m).monitors);
            else
                c.monitors.add(m);
        return c;
    }

    public static CompositeMonitor make(IAnalysisMonitoring... monitors) {
        return make(Arrays.asList(monitors));
    }

    @Override
    public void addMessage(AbstractNode n, Message.Severity severity, String msg) {
        monitors.forEach(m -> m.addMessage(n, severity, msg));
    }

    @Override
    public void addMessage(AbstractNode n, Message.Severity severity, String key, String msg) {
        monitors.forEach(m -> m.addMessage(n, severity, key, msg));
    }

    @Override
    public void addMessageInfo(AbstractNode n, Message.Severity severity, String msg) {
        monitors.forEach(m -> m.addMessageInfo(n, severity, msg));
    }

    @Override
    public boolean allowNextIteration() {
        return  monitors.stream().allMatch(ISolverMonitoring::allowNextIteration);
    }

    @Override
    public void visitPhasePre(AnalysisPhase phase) {
        monitors.forEach(m -> m.visitPhasePre(phase));
    }

    @Override
    public void visitPhasePost(AnalysisPhase phase) {
        monitors.forEach(m -> m.visitPhasePost(phase));
    }

    @Override
    public void setSolverInterface(Solver.SolverInterface c) {
        monitors.forEach(m -> m.setSolverInterface(c));
    }

    @Override
    public void visitBlockTransferPre(BasicBlock b, State s) {
        monitors.forEach(m -> m.visitBlockTransferPre(b, s));
    }

    @Override
    public void visitCall(AbstractNode n, Value funval) {
        monitors.forEach(m -> m.visitCall(n, funval));
    }

    @Override
    public void visitEvalCall(AbstractNode n, Value v) {
        monitors.forEach(m -> m.visitEvalCall(n, v));
    }

    @Override
    public void visitFunction(Function f, Collection<State> entry_states) {
        monitors.forEach(m -> m.visitFunction(f, entry_states));
    }

    @Override
    public void visitIf(IfNode n, Value v) {
        monitors.forEach(m -> m.visitIf(n, v));
    }

    @Override
    public void visitIn(AbstractNode n, boolean maybe_v2_object, boolean maybe_v2_nonobject) {
        monitors.forEach(m -> m.visitIn(n, maybe_v2_object, maybe_v2_nonobject));
    }

    @Override
    public void visitInnerHTMLWrite(Node n, Value v) {
        monitors.forEach(m -> m.visitInnerHTMLWrite(n, v));
    }

    @Override
    public void visitInstanceof(AbstractNode n, boolean maybe_v2_non_function, boolean maybe_v2_function, boolean maybe_v2_prototype_primitive, boolean maybe_v2_prototype_nonprimitive) {
        monitors.forEach(m -> m.visitInstanceof(n, maybe_v2_non_function, maybe_v2_function, maybe_v2_prototype_primitive, maybe_v2_prototype_nonprimitive));
    }

    @Override
    public void visitJoin(long ms) {
        monitors.forEach(m -> m.visitJoin(ms));
    }

    @Override
    public void visitBlockTransferPost(BasicBlock b, State state) {
        monitors.forEach(m -> m.visitBlockTransferPost(b, state));
    }

    @Override
    public void visitNativeFunctionCall(AbstractNode n, HostObject hostobject, boolean num_actuals_unknown, int num_actuals, int min, int max) {
        monitors.forEach(m -> m.visitNativeFunctionCall(n, hostobject, num_actuals_unknown, num_actuals, min, max));
    }

    @Override
    public void visitNewFlow(BasicBlock b, Context c, State s, String diff, String info) {
        monitors.forEach(m -> m.visitNewFlow(b, c, s, diff, info));
    }

    @Override
    public void visitNodeTransferPre(AbstractNode n, State s) {
        monitors.forEach(m -> m.visitNodeTransferPre(n, s));
    }

    @Override
    public void visitNodeTransferPost(AbstractNode n, State s) {
        monitors.forEach(m -> m.visitNodeTransferPost(n, s));
    }

    @Override
    public void visitPropertyAccess(Node n, Value baseval) {
        monitors.forEach(m -> m.visitPropertyAccess(n, baseval));
    }

    @Override
    public void visitPropertyRead(AbstractNode n, Set<ObjectLabel> objs, StringOrSymbol propertyname, State state, boolean check_unknown) {
        monitors.forEach(m -> m.visitPropertyRead(n, objs, propertyname, state, check_unknown));
    }

    @Override
    public void visitPropertyWrite(Node n, Set<ObjectLabel> objs, StringOrSymbol propertyname) {
        monitors.forEach(m -> m.visitPropertyWrite(n, objs, propertyname));
    }

    @Override
    public void visitRead(Node n, Value v, State state) {
        monitors.forEach(m -> m.visitRead(n, v, state));
    }

    @Override
    public void visitReadNonThisVariable(ReadVariableNode n, Value v) {
        monitors.forEach(m -> m.visitReadNonThisVariable(n, v));
    }

    @Override
    public void visitReadProperty(ReadPropertyNode n, Set<ObjectLabel> objlabels, StringOrSymbol propertyname, boolean maybe, State state, Value v, ObjectLabel global_obj) {
        monitors.forEach(m -> m.visitReadProperty(n, objlabels, propertyname, maybe, state, v, global_obj));
    }

    @Override
    public void visitReadThis(ReadVariableNode n, Value v, State state, ObjectLabel global_obj) {
        monitors.forEach(m -> m.visitReadThis(n, v, state, global_obj));
    }

    @Override
    public void visitReadVariable(ReadVariableNode n, Value v, State state) {
        monitors.forEach(m -> m.visitReadVariable(n, v, state));
    }

    @Override
    public void visitRecoveryGraph(AbstractNode node, int size) {
        monitors.forEach(m -> m.visitRecoveryGraph(node, size));
    }

    @Override
    public void visitUnknownValueResolve(AbstractNode node, boolean partial, boolean scanning) {
        monitors.forEach(m -> m.visitUnknownValueResolve(node, partial, scanning));
    }

    @Override
    public void visitUserFunctionCall(Function f, AbstractNode call, boolean constructor) {
        monitors.forEach(m -> m.visitUserFunctionCall(f, call, constructor));
    }

    @Override
    public void visitVariableAsRead(AbstractNode n, String varname, Value v, State state) {
        monitors.forEach(m -> m.visitVariableAsRead(n, varname, v, state));
    }

    @Override
    public void visitVariableOrProperty(AbstractNode node, String var, SourceLocation loc, Value value, Context context, State state) {
        monitors.forEach(m -> m.visitVariableOrProperty(node, var, loc, value, context, state));
    }

    @Override
    public void visitNativeFunctionReturn(AbstractNode node, HostObject hostObject, Value result) {
        monitors.forEach(m -> m.visitNativeFunctionReturn(node, hostObject, result));
    }

    @Override
    public void visitEventHandlerRegistration(AbstractNode node, Context context, Value handler) {
        monitors.forEach(m -> m.visitEventHandlerRegistration(node, context, handler));
    }

    @Override
    public void visitPropagationPre(BlockAndContext<Context> from, BlockAndContext<Context> to) {
        monitors.forEach(m -> m.visitPropagationPre(from, to));
    }

    @Override
    public void visitPropagationPost(BlockAndContext<Context> from, BlockAndContext<Context> to, boolean changed) {
        monitors.forEach(m -> m.visitPropagationPost(from, to, changed));
    }

    @Override
    public void visitNewObject(AbstractNode node, ObjectLabel label, State s) {
        monitors.forEach(m -> m.visitNewObject(node, label, s));
    }

    @Override
    public void visitRenameObject(AbstractNode node, ObjectLabel from, ObjectLabel to, State s) {
        monitors.forEach(m -> m.visitRenameObject(node, from, to, s));
    }

    @Override
    public void visitIterationDone(String terminatedEarlyMsg) {
        monitors.forEach(m -> m.visitIterationDone(terminatedEarlyMsg));
    }

    @Override
    public void visitSoundnessTestingDone(int numSoundnessChecks) {
        monitors.forEach(m -> m.visitSoundnessTestingDone(numSoundnessChecks));
    }
}
