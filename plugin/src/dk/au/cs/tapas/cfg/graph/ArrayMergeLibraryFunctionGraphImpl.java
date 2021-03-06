package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgument;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.element.*;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by Silwing on 27-05-2015.
 */
public class ArrayMergeLibraryFunctionGraphImpl extends LibraryFunctionGraphImpl {
    public ArrayMergeLibraryFunctionGraphImpl(boolean[] args) {
        super(args, false);
    }

    public static ArrayMergeLibraryFunctionGraphImpl TwoArgsGraph() {
        return new ArrayMergeLibraryFunctionGraphImpl(new boolean[] {false, false});
    }

    @NotNull
    @Override
    public AnalysisLatticeElement analyse(@NotNull ResultNode resultNode, @NotNull Context context, @NotNull AnalysisLatticeElement analysisLatticeElement, @NotNull AnalysisAnnotator annotator) {
        TemporaryVariableName[] arrays = Arrays.stream(resultNode.getCallNode().getCallArguments()).map(TemporaryVariableCallArgument.class::cast).map(TemporaryVariableCallArgument::getArgument).toArray(TemporaryVariableName[]::new);

        ValueLatticeElement resultVal = ValueLatticeElement.bottom;
        boolean seenMap = false, seenList = false;
        for(TemporaryVariableName array : arrays) {
            ValueLatticeElement arrayVal = analysisLatticeElement.getTempsValue(context, array);

            if(arrayVal.equals(ValueLatticeElement.bottom))
            {
                resultVal = ValueLatticeElement.bottom;
                break;
            }

            if(arrayVal.getArray().equals(ArrayLatticeElement.bottom)) {
                resultVal = new ValueLatticeElementImpl(NullLatticeElement.top);
                break;
            }

            if(resultVal.getNull().equals(NullLatticeElement.bottom) && (
                    !arrayVal.getNull().equals(NullLatticeElement.bottom) ||
                    !arrayVal.getNumber().equals(NumberLatticeElement.bottom) ||
                    !arrayVal.getBoolean().equals(BooleanLatticeElement.bottom) ||
                    !arrayVal.getString().equals(StringLatticeElement.bottom)
                )) {
                resultVal = resultVal.join(new ValueLatticeElementImpl(NullLatticeElement.top));
            }

            boolean isMap = arrayVal.getArray() instanceof MapArrayLatticeElement;
            boolean isList = arrayVal.getArray() instanceof ListArrayLatticeElement;
            if(isMap && !seenMap) {
                seenMap = true;
                if(seenList) {
                    annotator.error("Merging a list with a map");
                }
            } else if(isList && !seenList) {
                seenList = true;
                if(seenMap) {
                    annotator.error("Merging a map with a list");
                }
            }

            resultVal = resultVal.join(new ValueLatticeElementImpl(arrayVal.getArray()));
        }

        return setResultValue(resultNode, context, resultVal, analysisLatticeElement);
    }
}
