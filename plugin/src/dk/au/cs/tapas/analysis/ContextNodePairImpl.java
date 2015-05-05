package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.CallNode;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.ContextImpl;

import java.util.LinkedList;

/**
 * Created by budde on 4/29/15.
 */
public class ContextNodePairImpl extends PairImpl<Context, Node> implements ContextNodePair {
    public ContextNodePairImpl(Context left, Node right) {
        super(left, right);
    }

    public ContextNodePairImpl(Node node) {
        this(new ContextImpl(new LinkedList<>()), node);
    }

    @Override
    public Node getNode() {
        return getRight();
    }

    @Override
    public Context getContext() {
        return getLeft();
    }




    @Override
    public String toString() {
        return super.toString();
    }
}
