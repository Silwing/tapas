package dk.au.cs.tapas.lattice;

import dk.au.cs.tapas.cfg.node.CallNode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by budde on 4/28/15.
 */
public class ContextImpl implements Context{
    private final LinkedList<CallNode> nodes;
    private static final int limit = 3;


    public ContextImpl(List<CallNode> nodes) {
        this.nodes = new LinkedList<>(nodes);

    }

    public ContextImpl() {
        this(new LinkedList<>());
    }

    @Override
    public List<CallNode> getNodes() {
        return new LinkedList<>(getView());
    }

    @Override
    public Context addNode(CallNode node) {
        LinkedList<CallNode> newList = new LinkedList<>(nodes);
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
        return other instanceof ContextImpl && ((ContextImpl) other).getView().equals(getView());
    }

    @Override
    public String toString() {
        return "context"+getView();
    }

    @Override
    public int hashCode() {
        return getView().hashCode();
    }

    private List<CallNode> getView(){
        return nodes.subList(Math.max(0, nodes.size()-limit), nodes.size());
    }
}
