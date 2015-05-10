package dk.au.cs.tapas.lattice;

import dk.au.cs.tapas.lattice.element.*;

/**
 * Created by budde on 5/5/15.
 */
public interface Coercible {

    BooleanLatticeElement toBoolean();
    NumberLatticeElement toNumber();
    IntegerLatticeElement toInteger();
    IndexLatticeElement toArrayIndex();

    StringLatticeElement toStringLattice();
}
