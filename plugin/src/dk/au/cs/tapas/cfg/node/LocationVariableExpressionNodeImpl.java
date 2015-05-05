package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.VariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class LocationVariableExpressionNodeImpl extends NodeImpl implements LocationVariableExpressionNode{
    private Set<HeapLocation> locations;
    private VariableName variableName;

    public LocationVariableExpressionNodeImpl(Node successor, VariableName variableName, Set<HeapLocation> locations) {
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
        return "loc("+ variableName + ", "+ locations +')';
    }

    @Override
    public VariableName getVariableName() {
        return variableName;
    }
}
