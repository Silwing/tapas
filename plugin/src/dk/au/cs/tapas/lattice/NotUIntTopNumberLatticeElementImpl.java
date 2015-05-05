package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class NotUIntTopNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements NumberLatticeElement {

    public NotUIntTopNumberLatticeElementImpl() {
        super(
                (NumberLatticeElement) -> bottom,
                (NumberLatticeElement s) -> top);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("NotUIntNumber");
    }


    public boolean equals(Object object) {
        return object instanceof NotUIntTopNumberLatticeElementImpl;
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return other.equals(top) || other.equals(this);
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
    public NumberLatticeElement increment() {
        return this;
    }

    @Override
    public NumberLatticeElement decrement() {
        return this;
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
