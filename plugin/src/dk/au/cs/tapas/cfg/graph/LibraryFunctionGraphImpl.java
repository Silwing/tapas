package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisTarget;
import dk.au.cs.tapas.cfg.CallArgument;
import dk.au.cs.tapas.cfg.TemporaryHeapVariableCallArgument;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgument;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.HeapLocationImpl;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.VariableName;
import dk.au.cs.tapas.lattice.element.ValueLatticeElement;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public abstract class LibraryFunctionGraphImpl implements LibraryFunctionGraph {
    private final boolean aliasReturn;
    private StartNode entryNode;
    private ExitNode exitNode;
    private boolean[] arguments;

    public LibraryFunctionGraphImpl(boolean[] arguments, boolean aliasReturn) {
        exitNode = new ExitNodeImpl(null);
        entryNode = new StartNodeImpl(exitNode, null);
        this.arguments = arguments;
        this.aliasReturn = aliasReturn;

    }

    @Override
    public boolean[] getArguments() {
        return arguments;
    }

    @Override
    public boolean isAliasReturn() {
        return aliasReturn;
    }

    @Override
    public VariableName[] getArgumentNames() {
        return new VariableName[0];
    }

    @NotNull
    @Override
    public ExitNode getExitNode() {
        return exitNode;
    }

    @NotNull
    @Override
    public StartNode getEntryNode() {
        return entryNode;
    }

    @Override
    public Set<AnalysisTarget> getFlow(AnalysisLatticeElement latticeElement, AnalysisTarget cn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<AnalysisTarget> getFlow(AnalysisTarget analysisTarget) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Node> getNodes() {
        Set<Node> set = new HashSet<>();
        set.add(entryNode);
        set.add(exitNode);
        return set;
    }

    protected AnalysisLatticeElement setResultValue(ResultNode resultNode, Context context, ValueLatticeElement resultVal, AnalysisLatticeElement analysisLatticeElm) {
        if(resultNode.getCallArgument() instanceof TemporaryHeapVariableCallArgument) {
            HeapLocation resultLoc = new HeapLocationImpl(context, resultNode);
            return analysisLatticeElm.setHeapValue(context, resultLoc, resultVal).setHeapTempsValue(context, ((TemporaryHeapVariableCallArgument) resultNode.getCallArgument()).getArgument(), resultLoc);
        } else if(resultNode.getCallArgument() instanceof TemporaryVariableCallArgument) {
            return analysisLatticeElm.setTempsValue(context, ((TemporaryVariableCallArgument) resultNode.getCallArgument()).getArgument(), resultVal);
        }

        return analysisLatticeElm;
    }
}
