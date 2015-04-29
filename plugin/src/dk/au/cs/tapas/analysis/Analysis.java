package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.AnalysisLatticeElementImpl;

/**
 * Created by Silwing on 28-04-2015.
 */
public interface Analysis {

    AnalysisLatticeElement getEmptyLattice();
    AnalysisLatticeElement getStartLattice();
    AnalysisLatticeElement analyse(ContextNodePair n, AnalysisLatticeElement l);
}
