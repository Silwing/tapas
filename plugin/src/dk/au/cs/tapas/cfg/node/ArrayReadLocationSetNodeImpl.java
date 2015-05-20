package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayReadLocationSetNodeImpl extends NodeImpl implements ArrayReadLocationSetNode {

    private final TemporaryVariableName indexName;
    private final TemporaryHeapVariableName locations;
    private TemporaryHeapVariableName valueHeapLocationSet;

    public ArrayReadLocationSetNodeImpl(Node successor, TemporaryVariableName indexName, TemporaryHeapVariableName valueHeapLocationSet, TemporaryHeapVariableName targetLocations, PsiElement psiElement) {
        super(successor, psiElement);
        this.valueHeapLocationSet = valueHeapLocationSet;
        this.locations = targetLocations;
        this.indexName = indexName;

    }

    @Override
    public TemporaryVariableName getIndexName() {
        return indexName;
    }

    @Override
    public TemporaryHeapVariableName getValueTempHeapName() {
        return valueHeapLocationSet;
    }

    @Override
    public TemporaryHeapVariableName getTargetTempHeapName() {
        return locations;
    }

    @Override
    public String toString() {
        return "array_read(" + valueHeapLocationSet + ", " + locations + ", " + indexName + ')';
    }
}
