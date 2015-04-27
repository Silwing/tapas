package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.cfg.CallArgument;

/**
 * Created by budde on 4/27/15.
 */
public class ResultNodeImpl extends NodeImpl implements ResultNode{
    private CallNode callNode;
    private CallArgument targetName;



    public ResultNodeImpl(Node successor, CallArgument targetName) {
        this(successor, targetName, null);
    }

    public ResultNodeImpl(Node successor, CallArgument targetName, CallNode callNode) {
        super(successor);
        this.callNode = callNode;
        this.targetName = targetName;
    }

    @Override
    public CallArgument getTargetName() {

        return targetName;
    }

    @Override
    public CallNode getCallNode() {
        return callNode;
    }

    public void setCallNode(CallNode callNode) {
        this.callNode = callNode;
    }


    @Override
    public String toString() {
        return "result(" + targetName + ')';
    }
}
