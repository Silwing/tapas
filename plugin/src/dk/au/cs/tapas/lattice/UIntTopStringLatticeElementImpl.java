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
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.top;
    }

    @Override
    public StringLatticeElement concat(StringLatticeElement other) {
        return StringLatticeElement.top;
    }
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.top;

    }

    @Override
    public StringLatticeElement toStringLattice() {
        return this;
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return null; //TODO implement
    }
}
