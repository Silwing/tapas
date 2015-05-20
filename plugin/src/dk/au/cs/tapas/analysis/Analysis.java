package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;

/**
 * Created by Silwing on 28-04-2015.
 */
public interface Analysis {

    AnalysisLatticeElement getEmptyLattice();
    AnalysisLatticeElement getStartLattice(Graph graph);
    AnalysisLatticeElement analyse(AnalysisTarget n, AnalysisLatticeElement l);
}
