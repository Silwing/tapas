package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/27/15.
 */
public class ExitNodeImpl extends NodeImpl implements ExitNode{
    private final TemporaryVariableName targetName;

    public ExitNodeImpl( Node successor, TemporaryVariableName targetName) {
        super(successor);
        this.targetName = targetName;
    }


    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }
}
