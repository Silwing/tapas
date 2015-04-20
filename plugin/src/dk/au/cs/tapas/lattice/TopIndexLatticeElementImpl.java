package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopIndexLatticeElementImpl implements IndexLatticeElement {
    @Override
    public IndexLatticeElement meet(IndexLatticeElement other) {
        return other;
    }

    @Override
    public IndexLatticeElement join(IndexLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(IndexLatticeElement other) {
        return equals(other);
    }

    @Override
    public boolean equals(IndexLatticeElement other) {
        return other instanceof TopIndexLatticeElementImpl;
    }
}
