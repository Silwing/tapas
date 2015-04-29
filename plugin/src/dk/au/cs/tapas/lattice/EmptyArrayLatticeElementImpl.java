package dk.au.cs.tapas.lattice;

/**
 * Created by Silwing on 29-04-2015.
 */
public class EmptyArrayLatticeElementImpl implements ArrayLatticeElement {
    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        return other.equals(bottom)?other:this;
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        return other.equals(bottom)?this:other;
    }

    @Override
    public boolean containedIn(ArrayLatticeElement other) {
        return !other.equals(bottom);
    }

    @Override
    public boolean equals(ArrayLatticeElement other) {
        return  other instanceof EmptyArrayLatticeElement;
    }
}
