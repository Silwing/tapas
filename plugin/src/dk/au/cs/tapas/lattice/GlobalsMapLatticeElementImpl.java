package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class GlobalsMapLatticeElementImpl extends MapLatticeElementImpl<VariableName, PowerSetLatticeElement<HeapLocation>>{
    public GlobalsMapLatticeElementImpl() {
        super((VariableName name) -> new PowerSetLatticeElementImpl<>());
    }
}
