package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TrueBooleanLatticeElement extends MiddleLatticeElementImpl<BooleanLatticeElement> implements BooleanLatticeElement{
    public TrueBooleanLatticeElement() {
        super((BooleanLatticeElement b1) -> bottom, (BooleanLatticeElement b1) -> top);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TrueBooleanLatticeElement;
    }

    public int hashCode(){
        return 791377415;
    }
    @Override
    public void print(LatticePrinter printer) {
        printer.print("true");
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
        return NumberLatticeElement.generateNumberLatticeElement(1);
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.generateElement(1);
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.generateStringLatticeElement("1");
    }


    @Override
    public BooleanLatticeElement negate() {
        return boolFalse;
    }

    @Override
    public BooleanLatticeElement equalOperation(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(this)) return boolTrue;

        return other;
    }

    @Override
    public BooleanLatticeElement notEqual(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(this)) return BooleanLatticeElement.boolFalse;
        if(other.equals(boolFalse)) return boolTrue;

        return other;
    }

    @Override
    public BooleanLatticeElement lessThan(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        return boolFalse;
    }

    @Override
    public BooleanLatticeElement lessThanEqual(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(this)) return boolTrue;
        if(other.equals(boolFalse)) return boolFalse;

        return other;
    }

    @Override
    public BooleanLatticeElement greaterThan(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(this)) return boolFalse;
        if(other.equals(boolFalse)) return boolTrue;

        return other;
    }

    @Override
    public BooleanLatticeElement greaterThanEqual(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        return boolTrue;
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateIntegerIndex(toInteger());
    }
}
