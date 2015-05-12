package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 5/6/15.
 */
public class ArrayAppendReferenceAssignmentNodeImpl extends NodeImpl implements ArrayAppendReferenceAssignmentNode{
    final private Set<HeapLocation> variableLocationSet;
    final private Set<HeapLocation> valueLocationSet;
    final private TemporaryVariableName targetName;

    public ArrayAppendReferenceAssignmentNodeImpl(Node successor, Set<HeapLocation> variableLocationSet, Set<HeapLocation> valueLocationSet, TemporaryVariableName targetName, PsiElement element) {
        super(successor, element);
        this.variableLocationSet = variableLocationSet;
        this.valueLocationSet = valueLocationSet;
        this.targetName = targetName;
    }

    @Override
    public Set<HeapLocation> getVariableLocationSet() {
        return variableLocationSet;
    }

    @Override
    public Set<HeapLocation> getValueLocationSet() {
        return valueLocationSet;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }

    @Override
    public String toString() {
        return "array_append_ref("+variableLocationSet+ ", "+ valueLocationSet+ ", "+targetName+")";
    }

}
