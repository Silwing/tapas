package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.cfg.CallArgument;
import dk.au.cs.tapas.cfg.graph.FunctionGraph;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.Context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by budde on 4/27/15.
 */
public class ResultNodeImpl extends NodeImpl implements ResultNode{
    private CallNode callNode;
    private CallArgument targetName;
    private ExitNode exitNode;
    private FunctionGraph functionGraph;


    public ResultNodeImpl(Node successor, CallArgument targetName, PsiElement element) {
        this(successor, targetName, null, element);
    }

    public ResultNodeImpl(Node successor, CallArgument targetName, CallNode callNode, PsiElement element) {
        super(successor, element);
        this.callNode = callNode;
        this.targetName = targetName;
    }

    public void setFunctionGraph(FunctionGraph functionGraph) {
        this.functionGraph = functionGraph;
    }

    @Override
    public CallArgument getCallArgument() {

        return targetName;
    }

    @Override
    public CallNode getCallNode() {
        return callNode;
    }

    @Override
    public ExitNode getExitNode() {
        return exitNode;
    }

    @Override
    public FunctionGraph getFunctionGraph() {
        return functionGraph;
    }


    public void setCallNode(CallNode callNode) {
        this.callNode = callNode;
    }


    public void setExitNode(ExitNode exitNode) {
        this.exitNode = exitNode;
    }

    @Override
    public String toString() {
        return "result(" + targetName + ')';
    }
}
