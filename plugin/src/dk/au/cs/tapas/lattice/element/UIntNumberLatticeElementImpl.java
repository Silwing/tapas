package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class UIntNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements UIntNumberLatticeElement {
    private final Integer number;

    public UIntNumberLatticeElementImpl(Integer number) {
        super(
                (NumberLatticeElement n) -> bottom,
                (NumberLatticeElement n) -> n instanceof UIntNumberLatticeElementImpl?uIntTop:top
        );
        this.number = number;
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print(number.toString());
    }


    public boolean equals(Object object){
        return object == this || (object instanceof UIntNumberLatticeElementImpl && ((UIntNumberLatticeElementImpl) object).getNumber().equals(getNumber()));
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return other.equals(this) || uIntTop.containedIn(other);
    }


    @Override
    public NumberLatticeElement minus() {
        return NumberLatticeElement.generateNumberLatticeElement(-getNumber());
    }
    @Override
    public BooleanLatticeElement toBoolean() {
        return number.equals(0)?BooleanLatticeElement.boolFalse:BooleanLatticeElement.boolTrue;
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
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.generateStringLatticeElement(getNumber().toString());
    }

    @Override
    public NumberLatticeElement increment() {

        return NumberLatticeElement.generateNumberLatticeElement(number + 1);
    }

    @Override
    public NumberLatticeElement decrement() {
        return NumberLatticeElement.generateNumberLatticeElement(number - 1);
    }
    @Override
    public NumberLatticeElement add(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(getNumber() + ((ValueNumberLatticeElement) other).getNumber().doubleValue());
        }

        if(other.equals(uIntTop)) return uIntTop;

        return top;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(getNumber() - ((ValueNumberLatticeElement) other).getNumber().doubleValue());
        }

        return top;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(getNumber() * ((ValueNumberLatticeElement) other).getNumber().doubleValue());
        }

        if(getNumber() == 0) return NumberLatticeElement.generateNumberLatticeElement(0);
        if(other.equals(uIntTop)) return uIntTop;

        return top;
    }

    @Override
    public ValueLatticeElement divide(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl();

        if(other instanceof ValueNumberLatticeElement) {
            ValueNumberLatticeElement otherVal = (ValueNumberLatticeElement)other;
            if(otherVal.getNumber().doubleValue() == 0) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

            if(getNumber() == 0) return new ValueLatticeElementImpl(this);

            return new ValueLatticeElementImpl(NumberLatticeElement.generateNumberLatticeElement(getNumber() / otherVal.getNumber().doubleValue()));
        }

        if(getNumber() == 0) {
            if(other.equals(notUIntTop)) return new ValueLatticeElementImpl(this);
            else return new ValueLatticeElementImpl(this, BooleanLatticeElement.boolFalse);
        }

        if(other.equals(notUIntTop)) return new ValueLatticeElementImpl(top);

        return new ValueLatticeElementImpl(top, BooleanLatticeElement.boolFalse);
    }

    @Override
    public ValueLatticeElement modulo(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl();

        if(other instanceof ValueNumberLatticeElement) {
            ValueNumberLatticeElement otherVal = (ValueNumberLatticeElement)other;
            if(otherVal.getNumber().intValue() == 0) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

            if(getNumber() == 0) return new ValueLatticeElementImpl(this);

            return new ValueLatticeElementImpl(NumberLatticeElement.generateNumberLatticeElement(getNumber() % otherVal.getNumber().intValue()));
        }

        if(getNumber() == 0) return new ValueLatticeElementImpl(this, BooleanLatticeElement.boolFalse);

        return new ValueLatticeElementImpl(uIntTop, BooleanLatticeElement.boolFalse);
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(Math.pow(getNumber(), ((ValueNumberLatticeElement) other).getNumber().doubleValue()));
        }

        if(other.equals(uIntTop)) return uIntTop;

        if(getNumber() == 0 && other.equals(notUIntTop)) return NumberLatticeElement.generateNumberLatticeElement(0);
        if(getNumber() == 0 && other.equals(top)) return uIntTop;

        return top;
    }

    @Override
    public BooleanLatticeElement equalOperation(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof UIntNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(number == ((UIntNumberLatticeElement) other).getNumber());
        }

        if(other.containedIn(notUIntTop)) return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement notEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof UIntNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(number != ((UIntNumberLatticeElement) other).getNumber());
        }

        if(other.containedIn(notUIntTop)) return BooleanLatticeElement.boolTrue;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolTrue;

        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber() > ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolFalse;

        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber() < ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolTrue;

        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber() >= ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.boolFalse;

        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber() <= ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }


    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateIntegerIndex(toInteger());
    }
}
