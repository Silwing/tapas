package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;

/**
 * Created by budde on 4/29/15.
 */
public interface AnalysisTarget {

    Node getNode();

    CallLatticeContext getContext();

    AnalysisLatticeElement getCallLattice();

}
