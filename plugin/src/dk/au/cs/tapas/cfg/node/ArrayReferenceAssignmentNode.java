package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;

import java.util.Set;

/**
 * Created by budde on 5/6/15.
 */
public interface ArrayReferenceAssignmentNode extends ReferenceAssignmentNode{
    TemporaryHeapVariableName getVariableTempHeapName();

}
