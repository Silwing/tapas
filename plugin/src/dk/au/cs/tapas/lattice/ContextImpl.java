package dk.au.cs.tapas.lattice;

import dk.au.cs.tapas.cfg.node.CallNode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by budde on 4/28/15.
 */
public class ContextImpl implements Context{
    private final LinkedList<CallNode> nodes;

    public ContextImpl(List<CallNode> nodes) {
        this.nodes = new LinkedList<>(nodes);
    }

    @Override
    public List<CallNode> getNodes() {
        return new LinkedList<>(nodes);
    }

    @Override
    public Context addNode(CallNode node) {
        List<CallNode> newList = new LinkedList<>(nodes);
        newList.add(node);
        return new ContextImpl(newList);
    }

    @Override
    public Context popNode() {
        LinkedList<CallNode> newList = new LinkedList<>(nodes);
        newList.removeLast();
        return new ContextImpl(newList);
    }

    @Override
    public boolean equals(Context other) {
        return other.getNodes().equals(nodes);
    }

}
