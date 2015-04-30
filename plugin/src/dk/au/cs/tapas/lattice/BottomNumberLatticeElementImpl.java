package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomNumberLatticeElementImpl implements NumberLatticeElement {
    @Override
    public NumberLatticeElement meet(NumberLatticeElement other) {
        return this;
    }

    @Override
    public NumberLatticeElement join(NumberLatticeElement other) {
        return other;
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BottomNumberLatticeElementImpl;
    }
}
