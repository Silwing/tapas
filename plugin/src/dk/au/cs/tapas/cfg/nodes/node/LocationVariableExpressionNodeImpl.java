package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class LocationVariableExpressionNodeImpl extends NodeImpl implements LocationVariableExpressionNode{
    private Set<HeapLocation> locations;
    private String variableName;

    public LocationVariableExpressionNodeImpl(Node successor, String variableName, Set<HeapLocation> locations) {
        super(successor);
        this.locations = locations;
        this.variableName = variableName;
    }

    @Override
    public Set<HeapLocation> getTargetLocationSet() {
        return locations;
    }


    @Override
    public String toString() {
        return "loc("+ variableName + ", "+ locations + ')';
    }

    @Override
    public String getVariableName() {
        return variableName;
    }
}
