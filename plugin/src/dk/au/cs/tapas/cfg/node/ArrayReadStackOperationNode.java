package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/27/15.
 */
public interface ArrayReadStackOperationNode extends StackOperationNode {


    TemporaryVariableName getArrayName();

    TemporaryVariableName getIndexName();


}
