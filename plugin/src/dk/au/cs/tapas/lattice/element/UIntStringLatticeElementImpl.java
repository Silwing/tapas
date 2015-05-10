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
