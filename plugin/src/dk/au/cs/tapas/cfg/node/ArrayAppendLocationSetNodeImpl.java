package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayAppendLocationSetNodeImpl extends NodeImpl implements ArrayAppendLocationSetNode {
    private Set<HeapLocation> locations;
    private Set<HeapLocation> valueHeapLocationSet;

    public ArrayAppendLocationSetNodeImpl(Node successor, Set<HeapLocation> valueHeapLocationSet, Set<HeapLocation> locations, PsiElement psiElement) {
        super(successor, psiElement);
        this.valueHeapLocationSet = valueHeapLocationSet;
        this.locations = locations;

    }

    @Override
    public Set<HeapLocation> getTargetLocationSet() {
        return locations;
    }


    @Override
    public String toString() {
        return "loc_append("+valueHeapLocationSet+", "+ locations + ')';
    }

    @Override
    public Set<HeapLocation> getValueHeapLocationSet() {
        return valueHeapLocationSet;
    }
}
