package dk.au.cs.tapas.cfg.node;

/**
 * Created by budde on 4/22/15.
 *
 */
public class StartNodeImpl extends NodeImpl implements StartNode {

    public StartNodeImpl(Node successor) {
        super(successor);
    }

    public StartNodeImpl(Node[] successors) {
        super(successors);
    }

    @Override
    public String toString() {
        return "[start]";
    }
}
