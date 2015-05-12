package dk.au.cs.tapas.cfg;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.graph.LibraryFunctionGraphImpl;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 5/12/15.
 */
public class CountLibraryFunctionGraphImpl extends LibraryFunctionGraphImpl{

    public CountLibraryFunctionGraphImpl() {
        super(new boolean[]{}, false);
    }

    @NotNull
    @Override
    public AnalysisLatticeElement analyse(@NotNull ResultNode resultNode, @NotNull Context context, @NotNull AnalysisLatticeElement analysisLatticeElement, @NotNull AnalysisAnnotator annotator) {
        return analysisLatticeElement;
    }
}
