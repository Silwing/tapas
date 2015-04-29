package dk.au.cs.tapas.lattice;

/**
 * Created by Silwing on 29-04-2015.
 */
public class EmptyArrayLatticeElementImpl implements ArrayLatticeElement {
    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        return null;
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        return null;
    }

    @Override
    public boolean containedIn(ArrayLatticeElement other) {
        return false;
    }

    @Override
    public boolean equals(ArrayLatticeElement other) {
        return false;
    }
}
