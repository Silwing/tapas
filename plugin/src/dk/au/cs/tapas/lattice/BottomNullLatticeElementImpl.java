package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomNullLatticeElementImpl implements NullLatticeElement{

    @Override
    public NullLatticeElement meet(NullLatticeElement other) {
        return this;
    }

    @Override
    public NullLatticeElement join(NullLatticeElement other) {
        return other;
    }

    @Override
    public boolean containedIn(NullLatticeElement other) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BottomNullLatticeElementImpl;
    }
}
