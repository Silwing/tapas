package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public class ArrayInitStackOperationNodeImpl extends NodeImpl implements ArrayInitStackOperationNode {
    private final TemporaryVariableName targetName;

    public ArrayInitStackOperationNodeImpl(Node successor, TemporaryVariableName targetName, PsiElement psiElement) {
        super(successor, psiElement);
        this.targetName = targetName;
    }

    public TemporaryVariableName getTargetName() {
        return targetName;
    }


    @Override
    public String toString() {
        return "array_init(" +
                targetName +
                ')';
    }
}
