package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 */
public class ArrayAppendStackOperationNodeImpl extends NodeImpl implements ArrayAppendStackOperationNode {
    private final TemporaryVariableName valueName;
    private final TemporaryVariableName targetName;

    public ArrayAppendStackOperationNodeImpl(Node entryNode, TemporaryVariableName valueName, TemporaryVariableName target, PsiElement psiElement) {
        super(entryNode, psiElement);
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
        return "array_append(" + valueName + ", " + targetName + ")";
    }
}
