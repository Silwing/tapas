package dk.au.cs.tapas.lattice;

import javax.swing.*;

/**
 * Created by budde on 5/5/15.
 */
public interface Coercible {

    BooleanLatticeElement toBoolean();

    NumberLatticeElement toNumber();
}
