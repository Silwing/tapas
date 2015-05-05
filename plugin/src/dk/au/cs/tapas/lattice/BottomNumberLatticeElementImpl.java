package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomNumberLatticeElementImpl implements NumberLatticeElement {
    @Override
    public NumberLatticeElement meet(NumberLatticeElement other) {
        return this;
    }

    @Override
    public NumberLatticeElement join(NumberLatticeElement other) {
        return other;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, NumberLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return containedIn(other);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊥");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BottomNumberLatticeElementImpl;
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return true;
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.bottom;
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
        return top;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {
        return top;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        return top;
    }

    @Override
    public NumberLatticeElement divide(NumberLatticeElement other) {
        return top;
    }

    @Override
    public NumberLatticeElement modulo(NumberLatticeElement other) {
        return top;
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        return top;
    }
}
