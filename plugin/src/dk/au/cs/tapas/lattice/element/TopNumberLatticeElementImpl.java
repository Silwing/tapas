package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopNumberLatticeElementImpl implements NumberLatticeElement {
    @Override
    public NumberLatticeElement meet(NumberLatticeElement other) {
        return other;
    }

    @Override
    public NumberLatticeElement join(NumberLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, NumberLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return containedIn(other);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    public boolean equals(Object obj){
        return  obj instanceof TopNumberLatticeElementImpl;
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return equals(other);
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.top;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return this;
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.top;
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.top;
    }

    @Override
    public NumberLatticeElement increment() {
        return this;
    }

    @Override
    public NumberLatticeElement decrement() {
        return this;
    }

    @Override
    public NumberLatticeElement add(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        return this;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        return this;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) other).getNumber() == 0) {
            return NumberLatticeElement.generateNumberLatticeElement(0);
        }

        return this;
    }

    @Override
    public ValueLatticeElement divide(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl();

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) other).getNumber() == 0) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

        BooleanLatticeElement bool = (other.containedIn(notUIntTop)) ? BooleanLatticeElement.bottom : BooleanLatticeElement.boolFalse;

        return new ValueLatticeElementImpl(this, bool);
    }

    @Override
    public ValueLatticeElement modulo(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl();

        if(other instanceof ValueNumberLatticeElement && ((ValueNumberLatticeElement) other).getNumber().doubleValue() <= -1)
            return new ValueLatticeElementImpl(top);

        if(other instanceof UIntNumberLatticeElement) {
            if(((UIntNumberLatticeElement) other).getNumber() == 0)
                return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);
            else
                return new ValueLatticeElementImpl(top);
        }

        return new ValueLatticeElementImpl(top, BooleanLatticeElement.boolFalse);
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) other).getNumber() == 0) return NumberLatticeElement.generateNumberLatticeElement(1);

        return this;
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {
        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {
        return BooleanLatticeElement.top;    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement numberLatticeElement) {
        return BooleanLatticeElement.top;    }

    @Override
    public NumberLatticeElement minus() {
        return this;
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateIntegerIndex(toInteger());
    }
}
