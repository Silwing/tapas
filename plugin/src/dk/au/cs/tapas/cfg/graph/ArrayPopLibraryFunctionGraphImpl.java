package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.HeapLocationSetCallArgument;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgument;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.HeapLocation;
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
        Set<HeapLocation> argLocations = ((HeapLocationSetCallArgument) node.getCallNode().getCallArguments()[0]).getArgument();
        TemporaryVariableName result = ((TemporaryVariableCallArgument) node.getCallArgument()).getArgument();

        ValueLatticeElement newVal = new ValueLatticeElementImpl();
        for (HeapLocation loc : argLocations) {
            ValueLatticeElement val = lattice.getHeapValue(context, loc);
            if (val.getArray() instanceof ListArrayLatticeElement) {
                newVal = newVal.join(lattice.getHeap(context).getValue(((ListArrayLatticeElement) val.getArray()).getLocations().getLocations(), LatticeElement::join));
            } else if (val.getArray() instanceof MapArrayLatticeElement) {
                annotator.error("array_pop on map");
            }
            if (!val.getNumber().equals(NumberLatticeElement.bottom)
                    || !val.getString().equals(StringLatticeElement.bottom)
                    || !val.getBoolean().equals(BooleanLatticeElement.bottom)
                    || !val.getNull().equals(NullLatticeElement.bottom)) {
                newVal = newVal.join(new ValueLatticeElementImpl(NullLatticeElement.top));
            }

        }

        return lattice.setStackValue(context, result, newVal);
    }
}
