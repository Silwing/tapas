package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.TemporaryHeapVariableCallArgument;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgument;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.element.*;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by budde on 5/12/15.
 */
public class ArrayPopLibraryFunctionGraphImpl extends LibraryFunctionGraphImpl{
    public ArrayPopLibraryFunctionGraphImpl() {
        super(new boolean[]{true}, false);
    }

    @NotNull
    @Override
    public AnalysisLatticeElement analyse(@NotNull ResultNode node, @NotNull Context context, @NotNull AnalysisLatticeElement lattice, @NotNull AnalysisAnnotator annotator) {
        Set<HeapLocation> argLocations = lattice.getHeapTempsValue(context, (TemporaryHeapVariableName) node.getCallNode().getCallArguments()[0].getArgument()).getLocations();
        TemporaryVariableName result = ((TemporaryVariableCallArgument) node.getCallArgument()).getArgument();

        ValueLatticeElement newVal = new ValueLatticeElementImpl();
        for (HeapLocation loc : argLocations) {
            ValueLatticeElement val = lattice.getHeapValue(context, loc);
            if (val.getArray() instanceof ListArrayLatticeElement) {
                newVal = newVal.join(lattice.getHeap(context).getValue(((ListArrayLatticeElement) val.getArray()).getLocations().getLocations(), LatticeElement::join));
            } else if (val.getArray() instanceof MapArrayLatticeElement) {
                annotator.error("array_pop on map");
            }
            newVal = newVal.join(new ValueLatticeElementImpl(NullLatticeElement.top));

        }

        return lattice.setTempsValue(context, result, newVal);
    }
}
