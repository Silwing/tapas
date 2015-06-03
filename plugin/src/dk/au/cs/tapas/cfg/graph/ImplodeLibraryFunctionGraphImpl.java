package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.element.StringLatticeElement;
import dk.au.cs.tapas.lattice.element.ValueLatticeElementImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Silwing on 03-06-2015.
 */
public class ImplodeLibraryFunctionGraphImpl extends LibraryFunctionGraphImpl {
    public ImplodeLibraryFunctionGraphImpl() {
        super(new boolean[]{ false, false }, false);
    }

    @NotNull
    @Override
    public AnalysisLatticeElement analyse(@NotNull ResultNode resultNode, @NotNull Context context, @NotNull AnalysisLatticeElement analysisLatticeElement, @NotNull AnalysisAnnotator annotator) {
        // Since the array map lattice does not have a notion of order like PHP arrays does it is not possible to get a precise implementation
        // It is possible to make it more precise by checking the join string and possible content of the map to see if notUInt or UInt can be assured
        return setResultValue(resultNode, context, new ValueLatticeElementImpl(StringLatticeElement.top), analysisLatticeElement);
    }
}
