package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

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
    public void print(LatticePrinter printer) {
        printer.print("⊥");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BottomNumberLatticeElementImpl;
    }

    public int hashCode(){
        return 813021924;
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
    public ValueLatticeElement divide(NumberLatticeElement other) {
        return new ValueLatticeElementImpl(this);
    }

    @Override
    public ValueLatticeElement modulo(NumberLatticeElement other) {
        return new ValueLatticeElementImpl(this);
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        return this;
    }

    @Override
    public BooleanLatticeElement equalOperation(NumberLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public BooleanLatticeElement notEqual(NumberLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {
        return BooleanLatticeElement.bottom;    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public NumberLatticeElement minus() {
        return NumberLatticeElement.bottom;
    }
    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.bottom;
    }
}
