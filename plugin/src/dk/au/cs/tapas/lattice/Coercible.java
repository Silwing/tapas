package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 5/5/15.
 */
public interface Coercible {

    BooleanLatticeElement toBoolean();
    NumberLatticeElement toNumber();
    IntegerLatticeElement toInteger();
}
