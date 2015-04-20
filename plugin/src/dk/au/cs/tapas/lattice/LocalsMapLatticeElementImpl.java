package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public class LocalsMapLatticeElementImpl extends MapLatticeElementImpl<VariableName, PowerSetLatticeElement<HeapLocation>> {
    public LocalsMapLatticeElementImpl() {
        super((VariableName name) -> new PowerSetLatticeElementImpl<>());
    }

}
