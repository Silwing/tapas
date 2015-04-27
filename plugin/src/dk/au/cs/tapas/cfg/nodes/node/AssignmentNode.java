package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 4/26/15.
 *
 */
public interface AssignmentNode extends ExpressionNode {
    TemporaryVariableName getValueName();


    Set<HeapLocation> getVariableLocations();
}
