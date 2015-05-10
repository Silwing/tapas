package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.VariableName;

/**
 * Created by budde on 4/20/15.
 *
 */
public class GlobalsMapLatticeElementImpl extends MapLatticeElementImpl<VariableName, HeapLocationPowerSetLatticeElement>{
    public GlobalsMapLatticeElementImpl() {
        super((VariableName name) -> new HeapLocationPowerSetLatticeElementImpl());
    }
}
