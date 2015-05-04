package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface ValueLatticeElement extends LatticeElement<ValueLatticeElement, HeapMapLatticeElement>{

    ArrayLatticeElement getArray();

    StringLatticeElement getString();

    NumberLatticeElement getNumber();

    BooleanLatticeElement getBoolean();

    NullLatticeElement getNull();

}
