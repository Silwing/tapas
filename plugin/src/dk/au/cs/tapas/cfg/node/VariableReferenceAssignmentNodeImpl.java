package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.VariableName;

import java.util.Set;

/**
 * Created by budde on 5/6/15.
 */
public class VariableReferenceAssignmentNodeImpl  extends NodeImpl implements VariableReferenceAssignmentNode {

    final private VariableName variableName;
    final private Set<HeapLocation> valueLocationSet;
    final private TemporaryVariableName targetName;

    public VariableReferenceAssignmentNodeImpl(Node successor, VariableName variableName, Set<HeapLocation> valueLocationSet, TemporaryVariableName targetName) {
        super(successor);
        this.variableName = variableName;
        this.valueLocationSet = valueLocationSet;
        this.targetName = targetName;
    }

    @Override
    public VariableName getVariableName() {
        return variableName;
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
        return "assign_ref("+ variableName+
                ", " + valueLocationSet +
                ", " + targetName +
                ')';
    }

}
