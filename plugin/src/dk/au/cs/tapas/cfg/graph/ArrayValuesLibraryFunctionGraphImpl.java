package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.AnalysisAnnotator;
import dk.au.cs.tapas.cfg.node.ResultNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.element.*;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 6/10/15.
 */
public class ArrayValuesLibraryFunctionGraphImpl extends LibraryFunctionGraphImpl {
    public ArrayValuesLibraryFunctionGraphImpl() {
        super(new boolean[]{false}, false);
    }

    @NotNull
    @Override
    public AnalysisLatticeElement analyse(@NotNull ResultNode node, @NotNull Context context, @NotNull AnalysisLatticeElement lattice, @NotNull AnalysisAnnotator annotator) {
        ValueLatticeElement argValue = lattice.getTempsValue(context, (TemporaryVariableName) node.getCallNode().getCallArguments()[0].getArgument());
        ArrayLatticeElement argArray = argValue.getArray();

        ValueLatticeElement newVal = new ValueLatticeElementImpl();

        if(argArray instanceof MapArrayLatticeElement){
            newVal.setArray(
                    ArrayLatticeElement.generateList(((MapArrayLatticeElement) argArray)
                            .getMap()
                            .getValues()
                            .stream()
                            .reduce(new HeapLocationPowerSetLatticeElementImpl(), LatticeElement::join)));
        } else {
            newVal.setArray(argArray);
        }


        return setResultValue(node, context, newVal, lattice);
    }
}
