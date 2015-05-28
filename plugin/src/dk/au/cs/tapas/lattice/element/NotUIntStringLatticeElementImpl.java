package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class NotUIntStringLatticeElementImpl extends MiddleLatticeElementImpl<StringLatticeElement> implements NotUIntStringLatticeElement{
    private final String string;

    public NotUIntStringLatticeElementImpl(String string) {
        super(
                (StringLatticeElement n) -> bottom,
                (StringLatticeElement n) -> n instanceof NotUIntStringLatticeElementImpl?notUIntStringTop:top
        );
        this.string = string;
    }


    @Override
    public void print(LatticePrinter printer) {
        printer.print(string);
    }

    @Override
    public String getString() {
        return string;
    }

    public boolean equals(Object other){
        return other == this || (other instanceof NotUIntStringLatticeElementImpl && ((NotUIntStringLatticeElementImpl) other).getString().equals(getString()));
    }

    @Override
    public int hashCode() {
        return string.hashCode();
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return other.equals(this) || notUIntStringTop.containedIn(other);
    }


    @Override
    public BooleanLatticeElement toBoolean() {
        return string.length() == 0?BooleanLatticeElement.boolFalse:BooleanLatticeElement.boolTrue;
    }

    @Override
    public NumberLatticeElement toNumber() {
        Number number = NumberLatticeElement.parseNumberString(string);
        return NumberLatticeElement.generateNumberLatticeElement(number == null?0:number);
    }

    @Override
    public StringLatticeElement concat(StringLatticeElement other) {
        if(other.equals(bottom)) return bottom;
        if(other instanceof ValueStringLatticeElement) {
            ValueStringLatticeElement otherVal = (ValueStringLatticeElement)other;
            return StringLatticeElement.generateStringLatticeElement(getString() + otherVal.getString());
        } else {
            return top;
        }
    }

    @Override
    public BooleanLatticeElement equalOperation(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof NotUIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(string.equals(((NotUIntStringLatticeElement) other).getString()));

        if(other.containedIn(uIntStringTop))
            return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement notEqual(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof NotUIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(!string.equals(((NotUIntStringLatticeElement) other).getString()));

        if(other.containedIn(uIntStringTop))
            return BooleanLatticeElement.boolTrue;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof NotUIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(string.compareTo(((NotUIntStringLatticeElement) other).getString()) < 0);

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThanEqual(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof NotUIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(string.compareTo(((NotUIntStringLatticeElement) other).getString()) <= 0);

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThan(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof NotUIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(string.compareTo(((NotUIntStringLatticeElement) other).getString()) > 0);

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanEqual(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof NotUIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(string.compareTo(((NotUIntStringLatticeElement) other).getString()) >= 0);

        return BooleanLatticeElement.top;
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.bottom;
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return this;
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateStringLIndex(this);
    }
}
