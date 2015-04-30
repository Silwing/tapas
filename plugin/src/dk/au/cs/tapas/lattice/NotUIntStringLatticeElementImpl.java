package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class NotUIntStringLatticeElementImpl extends MiddleLatticeElementImpl<StringLatticeElement> implements ValueStringLatticeElement{
    private final String string;

    public NotUIntStringLatticeElementImpl(String string) {
        super(
                (StringLatticeElement n) -> bottom,
                (StringLatticeElement n) -> n instanceof NotUIntStringLatticeElementImpl?notUIntStringTop:top
        );
        this.string = string;
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return other.equals(this) || notUIntStringTop.containedIn(other);
    }

    @Override
    public String getString() {
        return string;
    }

    public boolean equals(Object other){
        return other instanceof NotUIntStringLatticeElementImpl && ((NotUIntStringLatticeElementImpl) other).getString().equals(getString());
    }
}
