package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayReadExpressionNodeImpl extends NodeImpl implements ArrayReadExpressionNode{
    private TemporaryVariableName targetName;
    private TemporaryVariableName arrayName;
    private TemporaryVariableName indexName;

    public ArrayReadExpressionNodeImpl(Node successor, TemporaryVariableName arrayName, TemporaryVariableName indexName, TemporaryVariableName targetName) {
        super(successor);
        this.arrayName = arrayName;
        this.indexName = indexName;
        this.targetName = targetName;
    }

    @Override
    public TemporaryVariableName getArrayName() {
        return arrayName;
    }

    @Override
    public TemporaryVariableName getIndexName() {
        return indexName;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }


    @Override
    public String toString() {
        return "aread(" +
                 arrayName +
                ", " + indexName +
                ", " + targetName+ ')';
    }
}