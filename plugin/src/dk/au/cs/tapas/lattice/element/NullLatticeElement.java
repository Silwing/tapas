package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.Coercible;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface NullLatticeElement extends LatticeElement<NullLatticeElement>, Coercible {
    NullLatticeElement bottom = new BottomNullLatticeElementImpl();
    NullLatticeElement top= new TopNullLatticeElementImpl();

}
