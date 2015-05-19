package dk.au.cs.tapas.lattice.element;

import com.sun.org.apache.xpath.internal.operations.Bool;
import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class UIntTopNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements NumberLatticeElement{

    public UIntTopNumberLatticeElementImpl() {
        super(
                (NumberLatticeElement n) -> bottom,
                (NumberLatticeElement n) -> top
        );
    }

    @Override
    public boolean containedIn( NumberLatticeElement other) {
        return other.equals(this) || other.equals(top);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("UIntNumber");
    }


    public boolean equals(Object object){
        return object instanceof UIntTopNumberLatticeElementImpl;
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
        return StringLatticeElement.uIntStringTop;
    }

    @Override
    public NumberLatticeElement increment() {
        return this;
    }

    @Override
    public NumberLatticeElement minus() {
        return top;
    }
    @Override
    public NumberLatticeElement decrement() {
        return top;
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

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) other).getNumber() == 0)
            return uIntTop;

        if(other instanceof NotUIntNumberLatticeElement) {
            NotUIntNumberLatticeElement otherVal = (NotUIntNumberLatticeElement)other;
            if(otherVal.getNumber().doubleValue() < 0 && otherVal.getNumber().intValue() == Math.ceil(otherVal.getNumber().doubleValue()))
                return uIntTop;
            else return notUIntTop;
        }

        return top;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) other).getNumber() == 0)
            return NumberLatticeElement.generateNumberLatticeElement(0);

        if(other.containedIn(uIntTop)) return uIntTop;

        if(other instanceof NotUIntNumberLatticeElement && ((NotUIntNumberLatticeElement) other).getNumber().doubleValue() < 0)
            return notUIntTop;

        return top;
    }

    @Override
    public ValueLatticeElement divide(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl();

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) other).getNumber() == 0)
            return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

        if(other instanceof  NotUIntNumberLatticeElement && ((NotUIntNumberLatticeElement) other).getNumber().doubleValue() < 0)
            return new ValueLatticeElementImpl(notUIntTop);

        if(other.containedIn(notUIntTop) || other instanceof UIntNumberLatticeElement) return new ValueLatticeElementImpl(top);

        return new ValueLatticeElementImpl(top, BooleanLatticeElement.boolFalse);
    }

    @Override
    public ValueLatticeElement modulo(NumberLatticeElement other) {
        if(other.equals(bottom)) return new ValueLatticeElementImpl();

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) other).getNumber() == 0)
            return new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);

        if(other instanceof UIntNumberLatticeElement) return new ValueLatticeElementImpl(uIntTop);

        return new ValueLatticeElementImpl(uIntTop, BooleanLatticeElement.boolFalse);
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other instanceof UIntNumberLatticeElement && ((UIntNumberLatticeElement) other).getNumber() == 0)
            return NumberLatticeElement.generateNumberLatticeElement(1);

        if(other.containedIn(uIntTop)) return uIntTop;

        return top;
    }

    @Override
    public BooleanLatticeElement equalOperation(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other.containedIn(notUIntTop)) return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement notEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other.containedIn(notUIntTop)) return BooleanLatticeElement.boolTrue;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof NotUIntNumberLatticeElement && ((NotUIntNumberLatticeElement) other).getNumber().doubleValue() < 0)
            return BooleanLatticeElement.boolTrue;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof NotUIntNumberLatticeElement && ((NotUIntNumberLatticeElement) other).getNumber().doubleValue() < 0)
            return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof NotUIntNumberLatticeElement && ((NotUIntNumberLatticeElement) other).getNumber().doubleValue() < 0)
            return BooleanLatticeElement.boolTrue;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof NotUIntNumberLatticeElement && ((NotUIntNumberLatticeElement) other).getNumber().doubleValue() < 0)
            return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateIntegerIndex(toInteger());
    }
}
