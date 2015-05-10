package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.VariableName;

/**
 * Created by budde on 4/19/15.
 *
 */
public class LocalsMapLatticeElementImpl extends MapLatticeElementImpl<VariableName, HeapLocationPowerSetLatticeElement> {
    public LocalsMapLatticeElementImpl() {
        super((VariableName name) -> new HeapLocationPowerSetLatticeElementImpl());
    }

}
