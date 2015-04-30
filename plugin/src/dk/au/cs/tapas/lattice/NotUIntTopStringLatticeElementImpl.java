package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class NotUIntTopStringLatticeElementImpl extends MiddleLatticeElementImpl<StringLatticeElement> implements StringLatticeElement {


    public NotUIntTopStringLatticeElementImpl() {
        super(
                (StringLatticeElement s) -> bottom,
                (StringLatticeElement s) -> top
        );
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return other.equals(this) || other.equals(top);
    }

    public boolean equals(Object other) {
        return other instanceof NotUIntTopStringLatticeElementImpl;
    }
}
