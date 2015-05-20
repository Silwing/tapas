package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 5/6/15.
 */
public class ArrayAppendReferenceAssignmentNodeImpl extends NodeImpl implements ArrayAppendReferenceAssignmentNode{
    final private TemporaryHeapVariableName variableLocationSet;
    final private TemporaryHeapVariableName valueLocationSet;
    final private TemporaryVariableName targetName;

    public ArrayAppendReferenceAssignmentNodeImpl(Node successor, TemporaryHeapVariableName variableLocationSet, TemporaryHeapVariableName valueLocationSet, TemporaryVariableName targetName, PsiElement element) {
        super(successor, element);
        this.variableLocationSet = variableLocationSet;
        this.valueLocationSet = valueLocationSet;
        this.targetName = targetName;
    }

    @Override
    public TemporaryHeapVariableName getVariableTempHeapName() {
        return variableLocationSet;
    }

    @Override
    public TemporaryHeapVariableName getValueTempHeapName() {
        return valueLocationSet;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }

    @Override
    public String toString() {
        return "array_append(" + variableLocationSet + ", " + valueLocationSet + ", " + targetName + ")";
    }

}
