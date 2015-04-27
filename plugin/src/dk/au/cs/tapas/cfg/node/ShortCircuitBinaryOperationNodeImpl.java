package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 */
public class ShortCircuitBinaryOperationNodeImpl extends BinaryOperationNodeImpl implements ShortCircuitBinaryOperationNode{
    public ShortCircuitBinaryOperationNodeImpl(Node successor, TemporaryVariableName leftOperandName, String operator, TemporaryVariableName rightOperandName, TemporaryVariableName targetName) {
        super(successor, leftOperandName, operator, rightOperandName, targetName);
    }


    @Override
    public String toString() {
        return "sop("+operator+", "+leftOperandName+", "+rightOperandName+", "+targetName+")";
    }
}
