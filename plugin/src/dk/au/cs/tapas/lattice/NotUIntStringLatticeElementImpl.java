package dk.au.cs.tapas.lattice;

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
        if(other instanceof ValueStringLatticeElement) {
            ValueStringLatticeElement otherVal = (ValueStringLatticeElement)other;
            return StringLatticeElement.generateStringLatticeElement(getString() + otherVal.getString());
        } else {
            return StringLatticeElement.top;
        }
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
