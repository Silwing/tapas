package dk.au.cs.tapas.cfg.nodes;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/22/15.
 */
public class IfNodeImpl extends NodeImpl{
    private final TemporaryVariableName condition;

    public IfNodeImpl(TemporaryVariableName condition, Node successor1, Node successor2) {
        super(new Node[]{successor1, successor2});
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "if("+condition+")";
    }
}
