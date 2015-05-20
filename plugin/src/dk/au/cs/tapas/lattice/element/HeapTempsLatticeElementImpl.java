package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;

/**
 * Created by budde on 5/20/15.
 */
public class HeapTempsLatticeElementImpl extends MapLatticeElementImpl<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> {
    public HeapTempsLatticeElementImpl() {
        super((t) -> new HeapLocationPowerSetLatticeElementImpl());
    }
}
