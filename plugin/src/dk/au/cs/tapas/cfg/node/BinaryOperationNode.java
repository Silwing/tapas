package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.cfg.BinaryOperator;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public interface BinaryOperationNode extends StackOperationNode {


    TemporaryVariableName getLeftOperandName();

    BinaryOperator getOperator();

    TemporaryVariableName getRightOperandName();

}
