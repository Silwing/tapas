package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class UIntStringLatticeElementImpl extends MiddleLatticeElementImpl<StringLatticeElement> implements UIntStringLatticeElement {
    private final String string;

    public UIntStringLatticeElementImpl(String string) {
        super(
                (StringLatticeElement n) -> bottom,
                (StringLatticeElement n) -> n instanceof UIntStringLatticeElementImpl?uIntStringTop:top
        );
        this.string = string;
    }

    @Override
    public String getString() {
        return string;
    }


    @Override
    public void print(LatticePrinter printer) {
        printer.print(string);
    }

    public boolean equals(Object object) {
        return object == this || (object instanceof UIntStringLatticeElementImpl && ((UIntStringLatticeElementImpl) object).getString().equals(getString()));
    }
    @Override
    public int hashCode() {
        return string.hashCode();
    }


    @Override
    public boolean containedIn(StringLatticeElement other) {
        return other.equals(this) || uIntStringTop.containedIn(other);
    }


    @Override
    public BooleanLatticeElement toBoolean() {
        return string.equals("0")?BooleanLatticeElement.boolFalse:BooleanLatticeElement.boolTrue;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.generateNumberLatticeElement(Integer.parseInt(string));
    }

    @Override
    public StringLatticeElement concat(StringLatticeElement other) {
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

        if(other instanceof UIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(string.equals(((UIntStringLatticeElement) other).getString()));

        if(other.containedIn(notUIntStringTop))
            return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement notEqual(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof UIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(!string.equals(((UIntStringLatticeElement) other).getString()));

        if(other.containedIn(notUIntStringTop))
            return BooleanLatticeElement.boolTrue;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof UIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(string.compareTo(((UIntStringLatticeElement) other).getString()) < 0);

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThanEqual(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof UIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(string.compareTo(((UIntStringLatticeElement) other).getString()) <= 0);

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThan(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof UIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(string.compareTo(((UIntStringLatticeElement) other).getString()) > 0);

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanEqual(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other instanceof UIntStringLatticeElement)
            return BooleanLatticeElement.generateBooleanLatticeElement(string.compareTo(((UIntStringLatticeElement) other).getString()) >= 0);

        return BooleanLatticeElement.top;
    }

    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.generateElement(Integer.parseInt(string));

    }

    @Override
    public StringLatticeElement toStringLattice() {
        return this;
    }


    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateIntegerIndex(toInteger());
    }
}
