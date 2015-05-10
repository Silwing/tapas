package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/20/15.
 *
 */
public class StateMapLatticeElementImpl extends MapLatticeElementImpl<TemporaryVariableName, ValueLatticeElement>{
    public StateMapLatticeElementImpl() {
        super((TemporaryVariableName name) -> new ValueLatticeElementImpl());
    }
}
