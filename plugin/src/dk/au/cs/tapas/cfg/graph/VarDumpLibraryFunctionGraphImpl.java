package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.TemporaryHeapVariableCallArgument;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgument;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.PrintStreamLatticePrinter;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.element.ValueLatticeElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Silwing on 28-05-2015.
 */
public class VarDumpLibraryFunctionGraphImpl extends LibraryFunctionGraphImpl {
    public VarDumpLibraryFunctionGraphImpl() {
        super(new boolean[]{ false }, false);
    }

    @NotNull
    @Override
    public AnalysisLatticeElement analyse(@NotNull ResultNode resultNode, @NotNull Context context, @NotNull AnalysisLatticeElement analysisLatticeElement, @NotNull AnalysisAnnotator annotator) {
        TemporaryVariableName input = ((TemporaryVariableCallArgument)resultNode.getCallNode().getCallArguments()[0]).getArgument();
        ValueLatticeElement inputVal = analysisLatticeElement.getTempsValue(context, input);

        System.out.println(inputVal.getArray().getClass().getName());
        inputVal.print(new PrintStreamLatticePrinter(System.out));

        return analysisLatticeElement;
    }
}
