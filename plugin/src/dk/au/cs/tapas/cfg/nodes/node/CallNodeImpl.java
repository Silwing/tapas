package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.cfg.nodes.CallArgument;

import java.util.Arrays;

/**
 * Created by budde on 4/27/15.
 */
public class CallNodeImpl extends NodeImpl implements CallNode{
    private String functionName;
    private CallArgument[] callArguments;
    private ResultNode resultNode;

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
}
