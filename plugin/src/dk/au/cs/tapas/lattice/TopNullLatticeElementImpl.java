package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopNullLatticeElementImpl implements NullLatticeElement {
    @Override
    public NullLatticeElement meet(NullLatticeElement other) {
        return other;
    }

    @Override
    public NullLatticeElement join(NullLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(NullLatticeElement other) {
        return equals(other);
    }

    public boolean equals(Object obj){
        return  obj instanceof TopNullLatticeElementImpl;
    }

}
