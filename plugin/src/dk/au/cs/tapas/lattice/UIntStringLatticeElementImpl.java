package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class UIntStringLatticeElementImpl extends MiddleLatticeElementImpl<StringLatticeElement> implements ValueStringLatticeElement {
    private final String string;

    public UIntStringLatticeElementImpl(String string) {
        super(
                (StringLatticeElement n) -> bottom,
                (StringLatticeElement n) -> n instanceof UIntStringLatticeElementImpl?uIntStringTop:top
        );
        this.string = string;
    }

    @Override
    public String getString() {
        return string;
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return other.equals(this) || uIntStringTop.containedIn(other);
    }

    public boolean equals(StringLatticeElement object) {
        return object instanceof UIntStringLatticeElementImpl && ((UIntStringLatticeElementImpl) object).getString().equals(getString());
    }
}