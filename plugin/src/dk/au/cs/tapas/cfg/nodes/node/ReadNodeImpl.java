package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 */
public class ReadNodeImpl extends NodeImpl implements ReadNode {

    private final String variable;
    private final TemporaryVariableName target;

    public ReadNodeImpl(String variable, TemporaryVariableName target, Node successor) {
        super(successor);
        this.variable = variable;
        this.target = target;
    }


    @Override
    public String getVariable() {
        return variable;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return target;
    }


    @Override
    public String toString() {
        return "read("+variable + ", " + target +")";
    }
}
