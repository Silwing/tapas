package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class HeapMapLatticeElementImpl extends MapLatticeElementImpl<HeapLocation, ValueLatticeElement>{
    public HeapMapLatticeElementImpl() {
        super((HeapLocation location) -> new ValueLatticeElementImpl());
    }
}
