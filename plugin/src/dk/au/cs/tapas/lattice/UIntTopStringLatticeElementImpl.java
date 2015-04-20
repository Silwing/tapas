package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class UIntTopStringLatticeElementImpl extends MiddleLatticeElementImpl<StringLatticeElement> implements StringLatticeElement {
    public UIntTopStringLatticeElementImpl() {
        super(
                (StringLatticeElement s) -> bottom,
                (StringLatticeElement s) -> top
        );
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return other.equals(this) || other.equals(top);
    }

    public boolean equals(StringLatticeElement object) {
        return object instanceof UIntTopStringLatticeElementImpl;
    }
}
