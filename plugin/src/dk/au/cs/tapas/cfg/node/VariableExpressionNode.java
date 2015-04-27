package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public interface VariableExpressionNode extends Node {

    Set<HeapLocation> getTargetLocationSet();

}
