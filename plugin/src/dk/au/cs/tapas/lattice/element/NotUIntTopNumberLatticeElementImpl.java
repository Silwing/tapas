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
        return top;
    }

    @Override
    public NumberLatticeElement decrement() {
        return this;
    }

    @Override
    public NumberLatticeElement add(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.containedIn(uIntTop)) return uIntTop;

        return top;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.containedIn(uIntTop)) return notUIntTop;

        return top;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement)other).getNumber() == 0) {
            return NumberLatticeElement.generateNumberLatticeElement(0);
        }

        return top;
    }

    @Override
    public ValueLatticeElement divide(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl();

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) other).getNumber() == 0)
            return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

        if(other.equals(notUIntTop) || other instanceof UIntNumberLatticeElement) return new ValueLatticeElementImpl(top);

        return new ValueLatticeElementImpl(top, BooleanLatticeElement.boolFalse);
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

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) other).getNumber() == 0)
            return NumberLatticeElement.generateNumberLatticeElement(0);

        return top;
    }

    @Override
    public BooleanLatticeElement equalOperation(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other.containedIn(uIntTop)) return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement notEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other.containedIn(uIntTop)) return BooleanLatticeElement.boolTrue;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

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
