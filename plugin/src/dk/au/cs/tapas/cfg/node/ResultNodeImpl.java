package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/27/15.
 */
public class ResultNodeImpl extends NodeImpl implements ResultNode{
    private CallNode callNode;
    private TemporaryVariableName targetName;



    public ResultNodeImpl(Node successor, TemporaryVariableName targetName) {
        this(successor, targetName, null);
    }

    public ResultNodeImpl(Node successor, TemporaryVariableName targetName, CallNode callNode) {
        super(successor);
        this.callNode = callNode;
        this.targetName = targetName;
    }

    @Override
    public TemporaryVariableName getTargetName() {

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
