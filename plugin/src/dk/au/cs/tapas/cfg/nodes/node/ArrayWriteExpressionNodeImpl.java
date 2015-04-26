package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public class ArrayWriteExpressionNodeImpl extends NodeImpl implements ArrayWriteExpressionNode{
    private final TemporaryVariableName targetName;
    private final TemporaryVariableName keyName;
    private final TemporaryVariableName valueName;

    public ArrayWriteExpressionNodeImpl(Node successor, TemporaryVariableName keyName, TemporaryVariableName valueName, TemporaryVariableName target) {
        super(successor);
        this.keyName = keyName;
        this.valueName = valueName;
        this.targetName = target;
    }

    @Override
    public TemporaryVariableName getKeyName() {
        return keyName;
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
        return "a_write(" + keyName + ", " + valueName + ", " + targetName + ")";
    }
}
