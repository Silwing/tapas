package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.cfg.BinaryOperator;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 */
public class ShortCircuitBinaryOperationNodeImpl extends BinaryOperationNodeImpl implements ShortCircuitBinaryOperationNode{
    public ShortCircuitBinaryOperationNodeImpl(Node successor, TemporaryVariableName leftOperandName, BinaryOperator operator, TemporaryVariableName rightOperandName, TemporaryVariableName targetName, PsiElement psiElement) {
        super(successor, leftOperandName, operator, rightOperandName, targetName, psiElement);
    }


    @Override
    public String toString() {
        return "sop("+operator+", "+leftOperandName+", "+rightOperandName+", "+targetName+")";
    }
}
