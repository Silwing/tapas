package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.cfg.nodes.Constant;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public class ReadConstNodeImpl extends NodeImpl implements ReadConstNode{


    private Constant constant;
    private TemporaryVariableName targetName;

    public ReadConstNodeImpl(Node successor, Constant constant, TemporaryVariableName targetName) {
        super(successor);
        this.constant = constant;
        this.targetName = targetName;
    }

    @Override
    public Constant getConstant() {
        return constant;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }

    @Override
    public String toString() {
        return "read_c(" + constant + ", " + targetName + ')';
    }
}
