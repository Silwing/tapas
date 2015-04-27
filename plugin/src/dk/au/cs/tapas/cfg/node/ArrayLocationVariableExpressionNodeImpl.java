package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayLocationVariableExpressionNodeImpl extends NodeImpl implements ArrayLocationVariableExpressionNode {

    private final TemporaryVariableName indexName;
    private final Set<HeapLocation> locations;
    private Set<HeapLocation> valueHeapLocationSet;

    public ArrayLocationVariableExpressionNodeImpl(Node successor, TemporaryVariableName indexName, Set<HeapLocation> valueHeapLocationSet, Set<HeapLocation> targetLocations) {
        super(successor);
        this.valueHeapLocationSet = valueHeapLocationSet;
        this.locations = targetLocations;
        this.indexName = indexName;

    }

    @Override
    public TemporaryVariableName getIndexName() {
        return indexName;
    }

    @Override
    public Set<HeapLocation> getValueHeapLocationSet() {
        return valueHeapLocationSet;
    }

    @Override
    public Set<HeapLocation> getTargetLocationSet() {
        return locations;
    }

    @Override
    public String toString() {
        return "loca("+valueHeapLocationSet+", "+ locations + ", " + indexName + ')';
    }
}
