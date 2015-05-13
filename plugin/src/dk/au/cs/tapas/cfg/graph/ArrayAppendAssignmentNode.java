package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.cfg.node.ExpressionNode;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 5/13/15.
 */
public interface ArrayAppendAssignmentNode extends ExpressionNode{

    TemporaryVariableName getTargetName();


    TemporaryVariableName getValueName() ;


    Set<HeapLocation> getVariableLocationSet() ;
}
