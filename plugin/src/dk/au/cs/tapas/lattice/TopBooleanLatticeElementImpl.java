package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopBooleanLatticeElementImpl implements BooleanLatticeElement {
    @Override
    public BooleanLatticeElement meet(BooleanLatticeElement other) {
        return other;
    }

    @Override
    public BooleanLatticeElement join(BooleanLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(BooleanLatticeElement other) {
        return equals(other);
    }

    public boolean equals(BooleanLatticeElement obj){
        return  obj instanceof TopBooleanLatticeElementImpl;
    }


}
