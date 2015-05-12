package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.cfg.UnaryOperator;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public class UnaryOperationNodeImpl extends NodeImpl implements UnaryOperationNode {

    private final TemporaryVariableName operandName;
    private final UnaryOperator operator;
    private final TemporaryVariableName targetName;

    public UnaryOperationNodeImpl(Node successor, TemporaryVariableName operandName, UnaryOperator operator, TemporaryVariableName target, PsiElement psiElement) {
        super(successor, psiElement);
        this.operandName = operandName;
        this.operator = operator;
        this.targetName = target;
    }


    @Override
    public TemporaryVariableName getOperandName() {
        return operandName;
    }

    @Override
    public UnaryOperator getOperator() {
        return operator;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }

    @Override
    public String toString() {
        return "unop(" + operator + ", " + operandName + ", " + targetName + ")";
    }
}
