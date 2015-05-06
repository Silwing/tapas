package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 5/6/15.
 */
public interface ArrayWriteReferenceAssignmentNode extends ArrayReferenceAssignmentNode{


    TemporaryVariableName getWriteArgument();

}
