package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.VariableName;

/**
 * Created by budde on 5/7/15.
 */
public class GlobalNodeImpl extends NodeImpl implements GlobalNode{
    final private VariableName[] variableNames;

    public GlobalNodeImpl(Node successor, VariableName[] variableNames) {
        super(successor);
        this.variableNames = variableNames;
    }

    @Override
    public VariableName[] getVariableNames() {
        return variableNames;
    }
}
