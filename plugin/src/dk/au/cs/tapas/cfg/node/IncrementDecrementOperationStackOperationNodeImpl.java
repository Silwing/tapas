package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.cfg.UnaryOperator;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class IncrementDecrementOperationStackOperationNodeImpl extends NodeImpl implements IncrementDecrementOperationStackOperationNode {
    private Set<HeapLocation> locationSet;
    private UnaryOperator operation;
    private TemporaryVariableName targetName;

    public IncrementDecrementOperationStackOperationNodeImpl(Node successor, Set<HeapLocation> locations, UnaryOperator operation, TemporaryVariableName targetName, PsiElement psiElement) {
        super(successor, psiElement);
        locationSet = locations;
        this.operation = operation;
        this.targetName = targetName;
    }

    @Override
    public Set<HeapLocation> getHeapLocationSet() {
        return locationSet;
    }

    @Override
    public UnaryOperator getOperation() {
        return operation;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }

    @Override
    public String toString() {
        return "inc_dec_op(" + operation + ", " + locationSet + ", " + targetName + ')';
    }
}
