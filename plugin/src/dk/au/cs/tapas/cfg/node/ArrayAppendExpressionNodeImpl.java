package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 */
public class ArrayAppendExpressionNodeImpl extends NodeImpl implements ArrayAppendExpressionNode{
    private final TemporaryVariableName valueName;
    private final TemporaryVariableName targetName;

    public ArrayAppendExpressionNodeImpl(Node entryNode, TemporaryVariableName valueName, TemporaryVariableName target) {
        super(entryNode);
        this.valueName = valueName;
        this.targetName = target;

    }

    @Override
    public TemporaryVariableName getValueName() {
        return valueName;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }


    @Override
    public String toString() {
        return "a_append(" + valueName + ", " + targetName + ")";
    }
}
