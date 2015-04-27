package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class ReferenceAssignmentNodeImpl extends NodeImpl implements ReferenceAssignemntNode {
    private final  TemporaryVariableName targetName;
    private final  Set<HeapLocation> valueLocationSet;
    private final  Set<HeapLocation> variableLocationSet;

    public ReferenceAssignmentNodeImpl(Node successor, TemporaryVariableName targetName, Set<HeapLocation> valueLocationSet, Set<HeapLocation> variableLocationSet) {
        super(successor);
        this.targetName = targetName;
        this.valueLocationSet = valueLocationSet;
        this.variableLocationSet = variableLocationSet;
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
        return "assign_ref("+ variableLocationSet +
                ", " + valueLocationSet +
                ", " + targetName +
                ')';
    }
}
