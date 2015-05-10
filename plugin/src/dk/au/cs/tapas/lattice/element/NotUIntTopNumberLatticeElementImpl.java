package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class NotUIntTopNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements NumberLatticeElement {

    public NotUIntTopNumberLatticeElementImpl() {
        super(
                (NumberLatticeElement) -> bottom,
                (NumberLatticeElement s) -> top);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("NotUIntNumber");
    }


    public boolean equals(Object object) {
        return object instanceof NotUIntTopNumberLatticeElementImpl;
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return other.equals(top) || other.equals(this);
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
        return StringLatticeElement.notUIntStringTop;
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
        if(other.equals(bottom)) return this;
        if(other.containedIn(this)) return top;

        return this;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {
        if(other.equals(bottom)) return this;
        if(other.containedIn(this)) return top;

        return this;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        if(other.equals(bottom)) return NumberLatticeElement.generateNumberLatticeElement(0);

        return top;
    }

    @Override
    public ValueLatticeElement divide(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

        if(other instanceof ValueNumberLatticeElement) {
            ValueNumberLatticeElement otherVal = (ValueNumberLatticeElement)other;
            if(otherVal.getNumber().doubleValue() == 0) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);
            return new ValueLatticeElementImpl(top);
        }

        return new ValueLatticeElementImpl(top, BooleanLatticeElement.boolFalse);
    }

    @Override
    public ValueLatticeElement modulo(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

        if(other instanceof ValueNumberLatticeElement) {
            ValueNumberLatticeElement otherVal = (ValueNumberLatticeElement)other;
            if(otherVal.getNumber().doubleValue() == 0) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);
            return new ValueLatticeElementImpl(top);
        }

        return new ValueLatticeElementImpl(top, BooleanLatticeElement.boolFalse);
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        if(other.equals(bottom)) return NumberLatticeElement.generateNumberLatticeElement(1);

        if(other instanceof ValueNumberLatticeElement) {
            ValueNumberLatticeElement otherVal = (ValueNumberLatticeElement)other;
            if(otherVal.getNumber().doubleValue() == 0) return NumberLatticeElement.generateNumberLatticeElement(1);
        }

        return top;
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolTrue;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolTrue;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateIntegerIndex(toInteger());
    }


    @Override
    public NumberLatticeElement minus() {
        return this;
    }
}