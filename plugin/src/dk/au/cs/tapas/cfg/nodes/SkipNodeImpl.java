package dk.au.cs.tapas.cfg.nodes;

/**
 * Created by budde on 4/22/15.
 */
public class SkipNodeImpl extends NodeImpl implements SkipNode {

    public SkipNodeImpl(Node successor) {
        super(successor);
    }

    public SkipNodeImpl(Node[] successors) {
        super(successors);
    }

    public String toString(){
        return "[nop]";
    }

}
