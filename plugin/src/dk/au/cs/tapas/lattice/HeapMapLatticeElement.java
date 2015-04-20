package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class HeapMapLatticeElement extends MapLatticeElementImpl<HeapLocation, ValueLatticeElement>{
    public HeapMapLatticeElement() {
        super((HeapLocation location) -> new ValueLatticeElementImpl());
    }
}
