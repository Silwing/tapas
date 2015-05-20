package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayAppendLocationSetNodeImpl extends NodeImpl implements ArrayAppendLocationSetNode {
    private TemporaryHeapVariableName locations;
    private TemporaryHeapVariableName valueHeapLocationSet;

    public ArrayAppendLocationSetNodeImpl(Node successor, TemporaryHeapVariableName valueHeapLocationSet, TemporaryHeapVariableName locations, PsiElement psiElement) {
        super(successor, psiElement);
        this.valueHeapLocationSet = valueHeapLocationSet;
        this.locations = locations;

    }

    @Override
    public TemporaryHeapVariableName getTargetTempHeapName() {
        return locations;
    }


    @Override
    public String toString() {
        return "array_append(" + valueHeapLocationSet + ", " + locations + ')';
    }

    @Override
    public TemporaryHeapVariableName getValueTempHeapName() {
        return valueHeapLocationSet;
    }
}
