package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;

/**
 * Created by Silwing on 28-04-2015.
 */
public interface Analysis {

    AnalysisLatticeElement getEmptyLattice();
    AnalysisLatticeElement getStartLattice();
    AnalysisLatticeElement analyse(ContextNodePair n, AnalysisLatticeElement l);
}
