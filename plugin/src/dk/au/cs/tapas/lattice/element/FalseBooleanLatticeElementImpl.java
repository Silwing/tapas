package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

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

    public int hashCode(){
        return 587985939;
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
    public BooleanLatticeElement equalOperation(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(boolFalse)) return boolTrue;
        if(other.equals(boolTrue)) return boolFalse;

        return top;
    }

    @Override
    public BooleanLatticeElement notEqual(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(boolTrue)) return boolTrue;
        if(other.equals(boolFalse)) return boolFalse;

        return top;
    }

    @Override
    public BooleanLatticeElement lessThan(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(boolTrue)) return boolTrue;
        if(other.equals(boolFalse)) return boolFalse;

        return top;
    }

    @Override
    public BooleanLatticeElement lessThanEqual(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        return boolTrue;
    }

    @Override
    public BooleanLatticeElement greaterThan(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        return boolFalse;
    }

    @Override
    public BooleanLatticeElement greaterThanEqual(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(boolTrue)) return boolFalse;
        if(other.equals(boolFalse)) return boolTrue;

        return top;
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateIntegerIndex(toInteger());
    }
}
