package dk.au.cs.tapas.cfg.node;

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

    public ArrayAppendReferenceAssignmentNodeImpl(Node successor, Set<HeapLocation> variableLocationSet, Set<HeapLocation> valueLocationSet, TemporaryVariableName targetName) {
        super(successor);
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
}