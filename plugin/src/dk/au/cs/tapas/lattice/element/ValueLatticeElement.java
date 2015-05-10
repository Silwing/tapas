package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.Coercible;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface ValueLatticeElement extends LatticeElement<ValueLatticeElement>, Coercible {

    ValueLatticeElement top = new ValueLatticeElementImpl(
            ArrayLatticeElement.top,
            StringLatticeElement.top,
            NumberLatticeElement.top,
            BooleanLatticeElement.top,
            NullLatticeElement.top
    );

    ArrayLatticeElement getArray();

    StringLatticeElement getString();

    NumberLatticeElement getNumber();

    BooleanLatticeElement getBoolean();

    NullLatticeElement getNull();

    BooleanLatticeElement equalOperation(ValueLatticeElement rightValue);

    BooleanLatticeElement identical(ValueLatticeElement rightValue);

    BooleanLatticeElement notEqualOperation(ValueLatticeElement rightValue);

    BooleanLatticeElement notIdentical(ValueLatticeElement rightValue);

    ValueLatticeElement setArray(ArrayLatticeElement array);


}