package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public interface ReferenceAssignmentNode extends StackOperationNode {

    Set<HeapLocation> getValueLocationSet();

}
