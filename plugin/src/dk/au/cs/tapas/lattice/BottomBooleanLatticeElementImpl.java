package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomBooleanLatticeElementImpl implements BooleanLatticeElement {
    @Override
    public BooleanLatticeElement meet(BooleanLatticeElement other) {
        return this;
    }

    @Override
    public BooleanLatticeElement join(BooleanLatticeElement other) {
        return other;
    }

    @Override
    public boolean containedIn(BooleanLatticeElement other) {
        return true;
    }

    public boolean equals(BooleanLatticeElement other) {
        return other instanceof BottomBooleanLatticeElementImpl;
    }


}
