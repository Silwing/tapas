package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.ContextNodePair;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;

import java.util.Set;

/**
 * Created by budde on 4/28/15.
 */
public abstract class GraphImpl implements Graph {


    @Override
    public Set<ContextNodePair> getFlow(AnalysisLatticeElement latticeElement, ContextNodePair context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<ContextNodePair> getFlow(ContextNodePair context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Node> getNodes() {
        throw new UnsupportedOperationException();
    }
}
