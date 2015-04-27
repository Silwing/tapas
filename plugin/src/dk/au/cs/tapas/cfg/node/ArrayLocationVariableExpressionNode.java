package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public interface ArrayLocationVariableExpressionNode extends VariableExpressionNode{

    TemporaryVariableName getIndexName();

    Set<HeapLocation> getValueHeapLocationSet();

}
