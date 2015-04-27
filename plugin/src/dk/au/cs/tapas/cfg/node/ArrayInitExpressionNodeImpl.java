package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public class ArrayInitExpressionNodeImpl extends NodeImpl implements ArrayInitExpressionNode {
    private final TemporaryVariableName targetName;

    public ArrayInitExpressionNodeImpl(Node successor, TemporaryVariableName targetName) {
        super(successor);
        this.targetName = targetName;
    }

    public TemporaryVariableName getTargetName() {
        return targetName;
    }


    @Override
    public String toString() {
        return "ainit(" +
                targetName +
                ')';
    }
}
