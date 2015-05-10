package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisTarget;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;

import java.util.Set;

/**
 * Created by budde on 4/28/15.
 */
public abstract class GraphImpl implements Graph {


    @Override
    public Set<AnalysisTarget> getFlow(AnalysisLatticeElement latticeElement, AnalysisTarget context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<AnalysisTarget> getFlow(AnalysisTarget context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Node> getNodes() {
        throw new UnsupportedOperationException();
    }
}
