package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.cfg.CallArgument;
import dk.au.cs.tapas.cfg.graph.FunctionGraph;

import java.util.Arrays;

/**
 * Created by budde on 4/27/15.
 */
public class CallNodeImpl extends NodeImpl implements CallNode{
    private String functionName;
    private CallArgument[] callArguments;
    private ResultNode resultNode;
    private FunctionGraph functionGraph;

    public CallNodeImpl(Node successor, String functionName, CallArgument[] callArguments, ResultNode resultNode) {
        super(successor);
        this.callArguments = callArguments;
        this.functionName = functionName;
        this.resultNode = resultNode;
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public FunctionGraph getFunctionGraph() {
        return functionGraph;
    }



    @Override
    public CallArgument[] getCallArguments() {
        return callArguments;
    }

    @Override
    public ResultNode getResultNode() {
        return resultNode;
    }

    @Override
    public String toString() {
        return "call(" + functionName + ", " + Arrays.toString(callArguments) + ")";
    }

    public void setFunctionGraph(FunctionGraph functionGraph) {
        this.functionGraph = functionGraph;
    }
}
