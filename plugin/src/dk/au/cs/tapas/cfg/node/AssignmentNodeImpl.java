package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/22/15.
 *
 */
public class AssignmentNodeImpl extends NodeImpl implements AssignmentNode {

    private final TemporaryVariableName valueName;
    private final TemporaryVariableName targetName;
    private final Set<HeapLocation> variableLocations;

    public AssignmentNodeImpl(Node node, TemporaryVariableName targetName, Set<HeapLocation> locations, TemporaryVariableName valueName) {
        super(node);
        this.targetName = targetName;
        this.variableLocations = locations;
        this.valueName = valueName;
    }

    @Override
    public TemporaryVariableName getValueName() {
        return valueName;
    }

    @Override
    public Set<HeapLocation> getVariableLocations() {
        return new HashSet<>(variableLocations);
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }


    @Override
    public String toString() {
        return "assign("+valueName+", "+variableLocations+", "+targetName+")";
    }
}
