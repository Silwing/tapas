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
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.bottom;
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.bottom;
    }

    @Override
    public NumberLatticeElement increment() {
        return NumberLatticeElement.generateNumberLatticeElement(1);
    }

    @Override
    public NumberLatticeElement decrement() {
        return this;
    }

    @Override
    public NumberLatticeElement add(NumberLatticeElement other) {
        return other;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {
        return other.minus();
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        return NumberLatticeElement.generateNumberLatticeElement(0);
    }

    @Override
    public ValueLatticeElement divide(NumberLatticeElement other) {
        return other.equals(this) ? new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse) : new ValueLatticeElementImpl(NumberLatticeElement.generateNumberLatticeElement(0));
    }

    @Override
    public ValueLatticeElement modulo(NumberLatticeElement other) {
        return other.equals(this) ? new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse) : new ValueLatticeElementImpl(NumberLatticeElement.generateNumberLatticeElement(0));
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        return other.equals(this) ? NumberLatticeElement.generateNumberLatticeElement(1) : NumberLatticeElement.generateNumberLatticeElement(0);
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {
        return BooleanLatticeElement.boolFalse;    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {
        return other.equals(this) ? BooleanLatticeElement.boolFalse : BooleanLatticeElement.boolTrue;    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {
        return other.equals(this) ? BooleanLatticeElement.boolTrue : greaterThan(other);
    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement other) {
        return other.equals(this) ? BooleanLatticeElement.boolTrue : lessThan(other);    }

    @Override
    public NumberLatticeElement minus() {
        return NumberLatticeElement.generateNumberLatticeElement(0);
    }
    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.bottom;
    }
}
