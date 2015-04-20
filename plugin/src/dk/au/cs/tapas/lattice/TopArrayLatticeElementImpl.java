package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopArrayLatticeElementImpl implements ArrayLatticeElement {
    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        return other;
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(ArrayLatticeElement other) {
        return equals(other);
    }

    public boolean equals(ArrayLatticeElement obj){
        return  obj instanceof TopArrayLatticeElementImpl;
    }

}
