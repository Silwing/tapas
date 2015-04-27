package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayAppendLocationVariableExpressionNodeImpl extends NodeImpl implements ArrayAppendLocationVariableExpressionNode{
    private Set<HeapLocation> locations;
    private Set<HeapLocation> valueHeapLocationSet;

    public ArrayAppendLocationVariableExpressionNodeImpl(Node successor, Set<HeapLocation> valueHeapLocationSet, Set<HeapLocation> locations) {
        super(successor);
        this.valueHeapLocationSet = valueHeapLocationSet;
        this.locations = locations;

    }

    @Override
    public Set<HeapLocation> getTargetLocationSet() {
        return locations;
    }


    @Override
    public String toString() {
        return "loc_append("+valueHeapLocationSet+", "+ locations + ')';
    }

    @Override
    public Set<HeapLocation> getValueHeapLocationSet() {
        return valueHeapLocationSet;
    }
}
