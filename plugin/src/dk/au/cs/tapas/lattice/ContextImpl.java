package dk.au.cs.tapas.lattice;

import dk.au.cs.tapas.cfg.node.CallNode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by budde on 4/28/15.
 */
public class ContextImpl implements Context{
    private final LinkedList<CallNode> nodes;
    private static final int limit = 5;


    public ContextImpl(List<CallNode> nodes) {
        this.nodes = new LinkedList<>(nodes);
    }

    @Override
    public List<CallNode> getNodes() {
        return new LinkedList<>(nodes);
    }

    @Override
    public Context addNode(CallNode node) {
        LinkedList<CallNode> newList = new LinkedList<>(nodes);
        if(newList.size() == limit){
            newList.removeFirst();
        }
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
    public CallNode getLastCallNode() {
        return nodes.getLast();
    }

    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }


    public boolean equals(Object other) {
        return other instanceof Context && other.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return "context"+nodes;
    }

    @Override
    public int hashCode() {
        return nodes.hashCode();
    }
}
