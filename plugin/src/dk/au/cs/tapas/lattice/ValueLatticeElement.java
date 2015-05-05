package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface ValueLatticeElement extends LatticeElement<ValueLatticeElement>, Coercible{

    ArrayLatticeElement getArray();

    StringLatticeElement getString();

    NumberLatticeElement getNumber();

    BooleanLatticeElement getBoolean();

    NullLatticeElement getNull();

    StringLatticeElement toStringLattice();

    BooleanLatticeElement equalOperation(ValueLatticeElement rightValue);

    BooleanLatticeElement identical(ValueLatticeElement rightValue);

    BooleanLatticeElement notEqualOperation(ValueLatticeElement rightValue);

    BooleanLatticeElement notIdentical(ValueLatticeElement rightValue);
}
