package dk.au.cs.tapas.cfg.nodes;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/22/15.
 */
public class AssignmentNodeImpl extends NodeImpl{

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
    public String toString() {
        return "assign("+targetName+", "+variableLocation+", "+valueName+")";
    }
}
