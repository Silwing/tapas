package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public class BinaryOperationNodeImpl extends NodeImpl implements BinaryOperationNode{


    protected final TemporaryVariableName leftOperandName;
    protected final String operator;
    protected final TemporaryVariableName rightOperandName;
    protected final TemporaryVariableName targetName;

    public BinaryOperationNodeImpl(Node successor, TemporaryVariableName leftOperandName, String operator, TemporaryVariableName rightOperandName, TemporaryVariableName targetName) {
        super(successor);
        this.leftOperandName = leftOperandName;
        this.operator = operator;
        this.rightOperandName = rightOperandName;
        this.targetName = targetName;
    }

    @Override
    public TemporaryVariableName getLeftOperandName() {
        return leftOperandName;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public TemporaryVariableName getRightOperandName() {
        return rightOperandName;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }

    @Override
    public String toString() {
        return "bop("+operator+", "+leftOperandName+", "+rightOperandName+", "+targetName+")";
    }
}
