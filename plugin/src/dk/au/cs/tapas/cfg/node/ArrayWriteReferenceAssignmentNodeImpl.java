package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayWriteReferenceAssignmentNodeImpl extends NodeImpl implements ArrayWriteReferenceAssignmentNode {
    private final  TemporaryVariableName targetName;
    private final  Set<HeapLocation> valueLocationSet;
    private final  Set<HeapLocation> variableLocationSet;
    private final TemporaryVariableName writeArgument;

    public ArrayWriteReferenceAssignmentNodeImpl(Node successor, TemporaryVariableName targetName, Set<HeapLocation> valueLocationSet, Set<HeapLocation> variableLocationSet, TemporaryVariableName writeArgument, PsiElement psiElement) {
        super(successor, psiElement);
        this.targetName = targetName;
        this.valueLocationSet = valueLocationSet;
        this.variableLocationSet = variableLocationSet;
        this.writeArgument = writeArgument;
    }

    @Override
    public Set<HeapLocation> getValueLocationSet() {
        return valueLocationSet;
    }

    @Override
    public Set<HeapLocation> getVariableLocationSet() {
        return variableLocationSet;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }


    @Override
    public String toString() {
        return "array_write(" + variableLocationSet +
                ", " + valueLocationSet +
                ", " + targetName +
                ')';
    }

    @Override
    public TemporaryVariableName getWriteArgument() {
        return writeArgument;
    }
}
