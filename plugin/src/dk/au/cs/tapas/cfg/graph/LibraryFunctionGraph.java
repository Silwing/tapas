package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 4/27/15.
 */
public interface LibraryFunctionGraph extends FunctionGraph{

    @NotNull
    AnalysisLatticeElement analyse(
            @NotNull ResultNode resultNode,
            @NotNull Context context,
            @NotNull AnalysisLatticeElement analysisLatticeElement,
            @NotNull AnalysisAnnotator annotator);

}
