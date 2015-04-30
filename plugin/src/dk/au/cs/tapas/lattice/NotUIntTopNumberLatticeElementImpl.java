package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class NotUIntTopNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements NumberLatticeElement {

    public NotUIntTopNumberLatticeElementImpl() {
        super(
                (NumberLatticeElement) -> bottom,
                (NumberLatticeElement s) -> top);
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return other.equals(top) || other.equals(this);
    }


    public boolean equals(Object object) {
        return object instanceof NotUIntTopNumberLatticeElementImpl;
    }
}
