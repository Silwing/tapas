package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomArrayLatticeElementImpl implements ArrayLatticeElement{

    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        return this;
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        return other;
    }

    @Override
    public boolean containedIn(ArrayLatticeElement other) {
        return true;
    }

    @Override
    public boolean equals(ArrayLatticeElement obj) {
        return obj instanceof BottomArrayLatticeElementImpl;
    }
}
