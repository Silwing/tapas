package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Randi on 31-05-2015.
 */
public class SingleArgIdentityLibraryFunctionGraphImpl extends LibraryFunctionGraphImpl {
    public SingleArgIdentityLibraryFunctionGraphImpl() {
        super(new boolean[] { false }, false);
    }

    @NotNull
    @Override
    public AnalysisLatticeElement analyse(@NotNull ResultNode resultNode, @NotNull Context context, @NotNull AnalysisLatticeElement analysisLatticeElement, @NotNull AnalysisAnnotator annotator) {
        return analysisLatticeElement;
    }
}
