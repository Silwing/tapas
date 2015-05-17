package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.VariableName;

/**
 * Created by budde on 4/26/15.
 */
public interface VariableReadNode extends StackOperationNode {


    VariableName getVariableName();

}
