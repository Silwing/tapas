package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayReadStackOperationNodeImpl extends NodeImpl implements ArrayReadStackOperationNode {
    private TemporaryVariableName targetName;
    private TemporaryVariableName arrayName;
    private TemporaryVariableName indexName;

    public ArrayReadStackOperationNodeImpl(Node successor, TemporaryVariableName arrayName, TemporaryVariableName indexName, TemporaryVariableName targetName, PsiElement psiElement) {
        super(successor, psiElement);
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
        return "array_read(" +
                 arrayName +
                ", " + indexName +
                ", " + targetName+ ')';
    }
}
