package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopNumberLatticeElementImpl implements NumberLatticeElement {
    @Override
    public NumberLatticeElement meet(NumberLatticeElement other) {
        return other;
    }

    @Override
    public NumberLatticeElement join(NumberLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, NumberLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return containedIn(other);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    public boolean equals(Object obj){
        return  obj instanceof TopNumberLatticeElementImpl;
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return equals(other);
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
        return StringLatticeElement.top;
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
        return this;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {
        return this;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        return this;
    }

    @Override
    public NumberLatticeElement divide(NumberLatticeElement other) {
        return this;
    }

    @Override
    public NumberLatticeElement modulo(NumberLatticeElement other) {
        return this;
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        return this;
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {
        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {
        return BooleanLatticeElement.top;    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {
        return BooleanLatticeElement.top;    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement numberLatticeElement) {
        return BooleanLatticeElement.top;    }
}
