package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisTarget;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by budde on 4/22/15.
 *
 */
public interface Graph {

    @NotNull
    Node getExitNode();

    @NotNull
    Node getEntryNode();


    Set<AnalysisTarget> getFlow(AnalysisLatticeElement latticeElement, AnalysisTarget analysisTarget);

    Set<AnalysisTarget> getFlow(AnalysisTarget analysisTarget);

    Set<Node> getNodes();


}
