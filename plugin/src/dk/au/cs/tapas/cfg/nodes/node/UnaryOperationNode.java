package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public interface UnaryOperationNode extends ExpressionNode {

    TemporaryVariableName getOperandName();
    String getOperator();

}
