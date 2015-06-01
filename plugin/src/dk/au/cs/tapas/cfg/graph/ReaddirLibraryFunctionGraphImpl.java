package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgument;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.element.*;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Randi on 31-05-2015.
 */
public class ReaddirLibraryFunctionGraphImpl extends LibraryFunctionGraphImpl {
    public ReaddirLibraryFunctionGraphImpl() {
        super(new boolean[]{ false }, false);
    }

    @NotNull
    @Override
    public AnalysisLatticeElement analyse(@NotNull ResultNode resultNode, @NotNull Context context, @NotNull AnalysisLatticeElement analysisLatticeElement, @NotNull AnalysisAnnotator annotator) {
        ValueLatticeElement value = new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);
        value = value.join(new ValueLatticeElementImpl(StringLatticeElement.top));

        return setResultValue(resultNode, context, value, analysisLatticeElement);
    }
}
