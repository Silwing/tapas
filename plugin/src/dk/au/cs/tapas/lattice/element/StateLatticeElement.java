package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.VariableName;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface StateLatticeElement extends LatticeElement<StateLatticeElement> {


    MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> getLocals();

    StateLatticeElement setLocals(MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> locals);

    MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> getGlobals();

    StateLatticeElement setGlobals(MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> globals);

    HeapMapLatticeElement getHeap();

    StateLatticeElement setHeap(HeapMapLatticeElement heap);

    MapLatticeElement<TemporaryVariableName, ValueLatticeElement> getStack();

    StateLatticeElement setStack(MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack);


}
