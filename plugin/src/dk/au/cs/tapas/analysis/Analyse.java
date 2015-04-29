package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.AnalysisLatticeElement;

/**
 * Created by Silwing on 28-04-2015.
 */
public interface Analyse {
    AnalysisLatticeElement getEntryLattice();
    AnalysisLatticeElement getExitLattice();
    AnalysisLatticeElement getLattice(ContextNodePair contextNodePair);
}
