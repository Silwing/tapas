package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopNumberLatticeElementImpl implements NumberLatticeElement {
    @Override
    public NumberLatticeElement meet(NumberLatticeElement other) {
        return other;
    }

    @Override
    public NumberLatticeElement join(NumberLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return equals(other);
    }

    public boolean equals(NumberLatticeElement obj){
        return  obj instanceof TopNumberLatticeElementImpl;
    }

}
