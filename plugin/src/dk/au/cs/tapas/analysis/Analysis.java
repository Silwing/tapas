package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.AnalysisLatticeElementImpl;

/**
 * Created by Silwing on 28-04-2015.
 */
public interface Analysis {

    AnalysisLatticeElementImpl getEmptyLattice();
    AnalysisLatticeElementImpl getStartLattice();
    AnalysisLatticeElementImpl analyse(Node n, AnalysisLatticeElementImpl l);
}
