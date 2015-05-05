package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class UIntNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements ValueNumberLatticeElement {
    private final Number number;

    public UIntNumberLatticeElementImpl(Number number) {
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
    public Number getNumber() {
        return number;
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return other.equals(this) || uIntTop.containedIn(other);
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
    public NumberLatticeElement increment() {
        return NumberLatticeElement.generateNumberLatticeElement(number.intValue()+1);
    }

    @Override
    public NumberLatticeElement decrement() {
        return NumberLatticeElement.generateNumberLatticeElement(number.intValue()-1);
    }

    @Override
    public NumberLatticeElement add(NumberLatticeElement other) {
        return null;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {
        return null;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        return null;
    }

    @Override
    public NumberLatticeElement divide(NumberLatticeElement other) {
        return null;
    }

    @Override
    public NumberLatticeElement modulo(NumberLatticeElement other) {
        return null;
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        return null;
    }
}
