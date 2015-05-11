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
        return number instanceof Integer ? IntegerLatticeElement.generateElement(number.intValue()) : IntegerLatticeElement.generateElement((int)Math.floor(number.doubleValue()));
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.generateStringLatticeElement(getNumber().toString());
    }

    @Override
    public NumberLatticeElement increment() {
        return NumberLatticeElement.generateNumberLatticeElement(number.intValue() + 1);
    }

    @Override
    public NumberLatticeElement decrement() {
        return NumberLatticeElement.generateNumberLatticeElement(number.intValue() - 1);
    }
    @Override
    public NumberLatticeElement add(NumberLatticeElement other) {

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(getNumber() + ((ValueNumberLatticeElement) other).getNumber().doubleValue());
        }

        if(other.equals(notUIntTop)) return top;

        return other;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(getNumber() - ((ValueNumberLatticeElement) other).getNumber().doubleValue());
        }

        if(other.equals(uIntTop)) return top;

        return other;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {


        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(getNumber() * ((ValueNumberLatticeElement) other).getNumber().doubleValue());
        }

        if(other.equals(notUIntTop)) return top;

        return other;
    }

    @Override
    public ValueLatticeElement divide(NumberLatticeElement other) {

        if(other instanceof ValueNumberLatticeElement) {
            ValueNumberLatticeElement otherVal = (ValueNumberLatticeElement)other;
            if(otherVal.getNumber().doubleValue() == 0) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

            if(getNumber() == 0) return new ValueLatticeElementImpl(this);

            return new ValueLatticeElementImpl(NumberLatticeElement.generateNumberLatticeElement(getNumber() / otherVal.getNumber().doubleValue()));
        }

        return new ValueLatticeElementImpl(top, BooleanLatticeElement.boolFalse);
    }

    @Override
    public ValueLatticeElement modulo(NumberLatticeElement other) {

        if(other instanceof ValueNumberLatticeElement) {
            ValueNumberLatticeElement otherVal = (ValueNumberLatticeElement)other;
            if(otherVal.getNumber().doubleValue() == 0) return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

            if(getNumber() == 0) return new ValueLatticeElementImpl(this);

            return new ValueLatticeElementImpl(NumberLatticeElement.generateNumberLatticeElement(getNumber() % otherVal.getNumber().doubleValue()));
        }

        return new ValueLatticeElementImpl(top, BooleanLatticeElement.boolFalse);
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {

        if(other instanceof ValueNumberLatticeElement) {
            return NumberLatticeElement.generateNumberLatticeElement(Math.pow(getNumber(), ((ValueNumberLatticeElement) other).getNumber().doubleValue()));
        }

        if(other.equals(notUIntTop)) return top;

        return other;
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {

        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber() > ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {

        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber() < ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {

        if(other instanceof ValueNumberLatticeElement) {
            return BooleanLatticeElement.generateBooleanLatticeElement(getNumber() >= ((ValueNumberLatticeElement)other).getNumber().doubleValue());
        }

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement other) {

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
