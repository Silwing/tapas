package dk.au.cs.tapas.cfg.nodes.node;

/**
 * Created by budde on 4/22/15.
 *
 */
public class EndNodeImpl extends NodeImpl {
    public EndNodeImpl() {
        super(new Node[0]);
    }

    @Override
    public String toString() {
        return "[end]";
    }

}
