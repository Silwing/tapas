package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 */
public class NotUIntNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements ValueNumberLatticeElement {

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
        return number.equals(0) ? BooleanLatticeElement.boolTrue : BooleanLatticeElement.boolTrue;
    }

    @Override
    public NumberLatticeElement increment() {
        return NumberLatticeElement.generateNumberLatticeElement(number.doubleValue() + 1);
    }

    @Override
    public NumberLatticeElement decrement() {
        return NumberLatticeElement.generateNumberLatticeElement(number.doubleValue() - 1);
    }
}
