package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.CallNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.ContextImpl;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by budde on 5/10/15.
 */
public class CallLatticeContextImpl implements CallLatticeContext {

    private final LinkedList<Pair<CallNode, AnalysisLatticeElement>> nodes;

    public CallLatticeContextImpl() {
        nodes = new LinkedList<>();
    }

    private CallLatticeContextImpl(LinkedList<Pair<CallNode, AnalysisLatticeElement>> nodes) {
        this.nodes = nodes;
    }


    @Override
    public List<Pair<CallNode, AnalysisLatticeElement>> getNodes() {
        return new LinkedList<>(nodes);
    }

    @Override
    public CallLatticeContext addNode(CallNode node, AnalysisLatticeElement analysis) {
        LinkedList<Pair<CallNode, AnalysisLatticeElement>> nodes = new LinkedList<>(this.nodes);
        nodes.addLast(new PairImpl<>(node, analysis));
        return new CallLatticeContextImpl(nodes);
    }

    @Override
    public CallLatticeContext popNode() {
        LinkedList<Pair<CallNode, AnalysisLatticeElement>> nodes = new LinkedList<>(this.nodes);
        nodes.removeLast();
        return new CallLatticeContextImpl(nodes);
    }

    @Override
    public Pair<CallNode, AnalysisLatticeElement> getLastCallNode() {
        return nodes.getLast();
    }

    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    @Override
    public Context toContext() {
        return new ContextImpl(this.nodes.stream().map(Pair::getLeft).collect(Collectors.toCollection(LinkedList::new)));
    }

    @Override
    public String toString(){
        return nodes.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CallLatticeContextImpl && ((CallLatticeContextImpl) o).toContext().equals(toContext());
    }

    @Override
    public int hashCode() {
        return toContext().hashCode();
    }
}
