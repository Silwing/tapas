package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.VariableName;

/**
 * Created by budde on 5/15/15.
 */
public interface VariableAssignmentNode extends AssignmentNode {

    VariableName getVariableName();

}
