package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.VariableName;

/**
 * Created by budde on 5/6/15.
 */
public interface VariableReferenceAssignmentNode extends ReferenceAssignmentNode{

    VariableName getVariableName();

}
