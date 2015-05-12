package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.annotator.Annotation;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Silwing on 28-04-2015.
 */
public interface Analyse {
    AnalysisLatticeElement getEntryLattice();
    AnalysisLatticeElement getExitLattice();
    AnalysisLatticeElement getLattice(AnalysisTarget analysisTarget);
    @NotNull
    List<Annotation> getAnnotations();
}
