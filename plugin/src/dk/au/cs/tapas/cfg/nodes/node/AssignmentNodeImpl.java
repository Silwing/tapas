package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/22/15.
 *
 */
public class AssignmentNodeImpl extends NodeImpl implements AssignmentNode {

    private final TemporaryVariableName valueName;
    private final TemporaryVariableName targetName;
    private final HeapLocation variableLocation;

    public AssignmentNodeImpl(Node node, TemporaryVariableName targetName, HeapLocation variableLocation, TemporaryVariableName valueName) {
        super(node);
        this.targetName = targetName;
        this.variableLocation = variableLocation;
        this.valueName = valueName;
    }

    @Override
    public TemporaryVariableName getValueName() {
        return valueName;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }

    @Override
    public HeapLocation getVariableLocation() {
        return variableLocation;
    }

    @Override
    public String toString() {
        return "assign("+valueName+", "+variableLocation+", "+targetName+")";
    }
}
