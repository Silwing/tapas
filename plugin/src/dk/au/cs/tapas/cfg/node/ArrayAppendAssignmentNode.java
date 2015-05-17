package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 5/13/15.
 */
public interface ArrayAppendAssignmentNode extends AssignmentNode {



    Set<HeapLocation> getVariableLocationSet() ;
}
