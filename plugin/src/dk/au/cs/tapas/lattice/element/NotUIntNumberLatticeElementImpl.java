package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 */
public class NotUIntNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements NotUIntNumberLatticeElement{

    private final Number number;

    public NotUIntNumberLatticeElementImpl(Number number) {
        super(
                (NumberLatticeElement n) -> bottom,
                (NumberLatticeElement n) -> top
        );
        this.number = number;
    }

    public Number getNumber() {
        return number;
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print(getNumber().toString());
    }

    public boolean equals(Object object) {
        return object == this || (object instanceof NotUIntNumberLatticeElementImpl && ((NotUIntNumberLatticeElementImpl) object).getNumber().equals(getNumber()));
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return other.equals(this) || notUIntTop.containedIn(other);
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return number.equals(0) ? BooleanLatticeElement.boolFalse : BooleanLatticeElement.boolTrue;
    }

    @Override
    public NumberLatticeElement minus() {
        return NumberLatticeElement.generateNumberLatticeElement(-getNumber().doubleValue());
    }
    @Override
    public NumberLatticeElement toNumber() {
        return this;
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.generateElement(number.intValue());
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateIntegerIndex(toInteger());
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.generateStringLatticeElement(getNumber().toString());
    }

    @Override
    public NumberLatticeElement increment() {
        return NumberLatticeElement.generateNumberLatticeElement(number.doubleValue() + 1);
    }

    @Override
    public NumberLatticeElement decrement() {
        return NumberLatticeElement.generateNumberLatticeElement(number.doubleValue() - 1);
    }

    @Override
    public NumberLatticeElement add(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(getNumber().doubleValue() + ((ValueNumberLatticeElement) other).getNumber().doubleValue());
        }

        return top;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(getNumber().doubleValue() - ((ValueNumberLatticeElement) other).getNumber().doubleValue());
        }
        if(other.equals(uIntTop)) return notUIntTop;

        return top;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(getNumber().doubleValue() * ((ValueNumberLatticeElement) other).getNumber().doubleValue());
        }

        if(number.doubleValue() < 0 && other.equals(uIntTop)) return notUIntTop;

        return top;
    }

    @Override
    public ValueLatticeElement divide(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl(bottom);

        if(other instanceof ValueNumberLatticeElement) {
            ValueNumberLatticeElement otherVal = (ValueNumberLatticeElement)other;
            if(otherVal instanceof UIntNumberLatticeElement && otherVal.getNumber().intValue() == 0) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

            return new ValueLatticeElementImpl(NumberLatticeElement.generateNumberLatticeElement(getNumber().doubleValue() / otherVal.getNumber().doubleValue()));
        }

        if(number.doubleValue() < 0 && other.equals(uIntTop)) return new ValueLatticeElementImpl(notUIntTop, BooleanLatticeElement.boolFalse);

        return new ValueLatticeElementImpl(top, BooleanLatticeElement.boolFalse);
    }

    @Override
    public ValueLatticeElement modulo(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl(bottom);

        if(other instanceof ValueNumberLatticeElement) {
            ValueNumberLatticeElement otherVal = (ValueNumberLatticeElement)other;
            if(otherVal instanceof UIntNumberLatticeElement && otherVal.getNumber().intValue() == 0) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

            return new ValueLatticeElementImpl(NumberLatticeElement.generateNumberLatticeElement(getNumber().doubleValue() % otherVal.getNumber().doubleValue()));
        }

        NumberLatticeElement num = (number.doubleValue() < 0) ? notUIntTop : uIntTop;
        BooleanLatticeElement bool = (other.equals(notUIntTop)) ? BooleanLatticeElement.bottom : BooleanLatticeElement.boolFalse;

        return new ValueLatticeElementImpl(num, bool);
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(Math.pow(getNumber().doubleValue(), ((ValueNumberLatticeElement)other).getNumber().doubleValue()));
        }

        return top;
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {
        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber().doubleValue() > ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolFalse;

        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber().doubleValue() < ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolTrue;

        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber().doubleValue() >= ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolFalse;

        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber().doubleValue() <= ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }
}
