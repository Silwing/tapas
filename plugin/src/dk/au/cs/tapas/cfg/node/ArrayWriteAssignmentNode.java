package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 5/12/15.
 */
public interface ArrayWriteAssignmentNode extends AssignmentNode {


    TemporaryVariableName getIndexName() ;

    TemporaryHeapVariableName getVariableTempHeapName() ;


}
