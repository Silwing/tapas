package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class FalseBooleanLatticeElementImpl extends MiddleLatticeElementImpl<BooleanLatticeElement> implements BooleanLatticeElement {
    public FalseBooleanLatticeElementImpl() {
        super((BooleanLatticeElement b1) -> bottom, (BooleanLatticeElement b1) -> top);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FalseBooleanLatticeElementImpl;
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("false");
    }

    @Override
    public boolean containedIn(BooleanLatticeElement other) {
        return other.equals(top) || other.equals(this);
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return this;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.generateNumberLatticeElement(0);
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.generateElement(0);
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.generateStringLatticeElement("");
    }


    @Override
    public BooleanLatticeElement negate() {
        return boolTrue;
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return null; //TODO implement
    }
}
