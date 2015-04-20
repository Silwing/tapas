package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomIndexLatticeElementImpl implements IndexLatticeElement {
    @Override
    public IndexLatticeElement meet(IndexLatticeElement other) {
        return this;
    }

    @Override
    public IndexLatticeElement join(IndexLatticeElement other) {
        return other;
    }

    @Override
    public boolean containedIn(IndexLatticeElement other) {
        return true;
    }

    @Override
    public boolean equals(IndexLatticeElement other) {
        return other instanceof BottomIndexLatticeElementImpl;
    }
}