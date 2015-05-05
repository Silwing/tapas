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

    @Override
    public void print(LatticePrinter printer) {
        printer.print("UIntString");
    }

    public boolean equals(Object object) {
        return object instanceof UIntTopStringLatticeElementImpl;
    }
    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.top;
    }

    @Override
<<<<<<< f4c0a8684b36ca38ed917ef1aab3e5e6939d0998
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.top;
    }

    @Override
    public NumberLatticeElement concat(StringLatticeElement other) {
        return null;
=======
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.top;
>>>>>>> 48787faedda6ae77521f1ee71f373e6bde9e04fe
    }
}
