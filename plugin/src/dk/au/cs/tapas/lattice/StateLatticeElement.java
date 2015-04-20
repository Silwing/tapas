package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface StateLatticeElement extends LatticeElement<StateLatticeElement> {


    MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> getLocals();

    StateLatticeElement setLocals(MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> locals);

    MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> getGlobals();

    StateLatticeElement setGlobals(MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> globals);

    MapLatticeElement<HeapLocation, ValueLatticeElement> getHeap();

    StateLatticeElement setHeap(MapLatticeElement<HeapLocation, ValueLatticeElement> heap);

    MapLatticeElement<TemporaryVariableName, ValueLatticeElement> getStack();

    StateLatticeElement setStack(MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack);


}
