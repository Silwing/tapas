package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;

/**
 * Created by budde on 4/29/15.
 */
public class AnalysisTargetImpl implements AnalysisTarget {
    private final Node node;
    private final CallLatticeContext context;
    private final AnalysisLatticeElement callLattice;

    public AnalysisTargetImpl(CallLatticeContext context, Node node) {
        this(context, node, null);
    }

    public AnalysisTargetImpl(Node node) {
        this(new CallLatticeContextImpl(), node);
    }

    public AnalysisTargetImpl(CallLatticeContext context, Node node, AnalysisLatticeElement callLattice) {
        this.context = context;
        this.node = node;
        this.callLattice = callLattice;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public CallLatticeContext getContext() {
        return context;
    }

    @Override
    public AnalysisLatticeElement getCallLattice() {
        return callLattice;
    }

    @Override
    public boolean equals(Object other){
        return other instanceof AnalysisTargetImpl && ((AnalysisTargetImpl) other).context.equals(context) && ((AnalysisTargetImpl) other).node.equals(node);
    }

    @Override
    public int hashCode(){
        return context.hashCode() ^ node.hashCode();
    }

    @Override
    public String toString() {
        return "("+node+", "+ context + ", " + callLattice + ")";
    }
}
