package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgument;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.element.BooleanLatticeElement;
import dk.au.cs.tapas.lattice.element.ValueLatticeElementImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Randi on 31-05-2015.
 */
public class ArrayKeyExistsLibraryFunctionGraphImpl extends LibraryFunctionGraphImpl {
    public ArrayKeyExistsLibraryFunctionGraphImpl() {
        super(new boolean[]{ false, false }, false);
    }

    @NotNull
    @Override
    public AnalysisLatticeElement analyse(@NotNull ResultNode resultNode, @NotNull Context context, @NotNull AnalysisLatticeElement analysisLatticeElement, @NotNull AnalysisAnnotator annotator) {
        return setResultValue(resultNode, context, new ValueLatticeElementImpl(BooleanLatticeElement.top), analysisLatticeElement);
    }
}
