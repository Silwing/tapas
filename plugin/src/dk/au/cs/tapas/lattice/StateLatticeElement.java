package dk.au.cs.tapas.lattice;

import org.w3c.dom.html.HTMLDListElement;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface StateLatticeElement extends LatticeElement<StateLatticeElement> {


    MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> getLocals();

    StateLatticeElement setLocals(MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> locals);

    MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> getGlobals();

    StateLatticeElement setGlobals(MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> globals);

    HeapMapLatticeElement getHeap();

    StateLatticeElement setHeap(HeapMapLatticeElement heap);

    MapLatticeElement<TemporaryVariableName, ValueLatticeElement> getStack();

    StateLatticeElement setStack(MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack);


}
