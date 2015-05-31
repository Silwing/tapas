package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgument;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.element.*;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Silwing on 28-05-2015.
 */
public class EmptyLibraryFunctionGraphImpl extends LibraryFunctionGraphImpl {
    public EmptyLibraryFunctionGraphImpl() {
        super(new boolean[] { false }, false);
    }

    @NotNull
    @Override
    public AnalysisLatticeElement analyse(@NotNull ResultNode resultNode, @NotNull Context context, @NotNull AnalysisLatticeElement analysisLatticeElement, @NotNull AnalysisAnnotator annotator) {
        TemporaryVariableName input = ((TemporaryVariableCallArgument)resultNode.getCallNode().getCallArguments()[0]).getArgument();

        ValueLatticeElement resultVal = new ValueLatticeElementImpl();
        ValueLatticeElement inputVal = analysisLatticeElement.getTempsValue(context, input);

        if(!inputVal.getArray().equals(ArrayLatticeElement.bottom)) {
            if(inputVal.getArray().equals(ArrayLatticeElement.emptyArray)) {
                resultVal = resultVal.join(new ValueLatticeElementImpl(BooleanLatticeElement.boolTrue));
            } else {
                resultVal = resultVal.join(new ValueLatticeElementImpl(BooleanLatticeElement.top));
            }
        }

        if(!inputVal.getBoolean().equals(BooleanLatticeElement.bottom)) {
            resultVal = resultVal.join(new ValueLatticeElementImpl(inputVal.getBoolean()));
        }

        if(!inputVal.getNumber().equals(NumberLatticeElement.bottom)) {
            if(inputVal.getNumber() instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) inputVal.getNumber()).getNumber() == 0) {
                resultVal = resultVal.join(new ValueLatticeElementImpl(BooleanLatticeElement.boolTrue));
            } else {
                resultVal = resultVal.join(new ValueLatticeElementImpl(BooleanLatticeElement.top));
            }
        }

        if(!inputVal.getString().equals(StringLatticeElement.bottom)) {
            if(inputVal.getString() instanceof UIntStringLatticeElement && ((UIntStringLatticeElement) inputVal.getString()).getString().equals("0") ||
                    inputVal.getString() instanceof NotUIntStringLatticeElement && ((NotUIntStringLatticeElement) inputVal.getString()).getString().isEmpty()) {
                resultVal = resultVal.join(new ValueLatticeElementImpl(BooleanLatticeElement.boolTrue));
            } else {
                resultVal = resultVal.join(new ValueLatticeElementImpl(BooleanLatticeElement.top));
            }
        }

        if(!inputVal.getNull().equals(NullLatticeElement.bottom)) {
            resultVal = resultVal.join(new ValueLatticeElementImpl(BooleanLatticeElement.boolTrue));
        }

        return setResultValue(resultNode, context, resultVal, analysisLatticeElement);
    }
}
