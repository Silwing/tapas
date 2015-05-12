package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.cfg.BinaryOperator;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public class BinaryOperationNodeImpl extends NodeImpl implements BinaryOperationNode{


    protected final TemporaryVariableName leftOperandName;
    protected final BinaryOperator operator;
    protected final TemporaryVariableName rightOperandName;
    protected final TemporaryVariableName targetName;

    public BinaryOperationNodeImpl(Node successor, TemporaryVariableName leftOperandName, BinaryOperator operator, TemporaryVariableName rightOperandName, TemporaryVariableName targetName, PsiElement psiElement) {
        super(successor, psiElement);
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
    public BinaryOperator getOperator() {
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
