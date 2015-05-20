package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomBooleanLatticeElementImpl implements BooleanLatticeElement {
    @Override
    public BooleanLatticeElement meet(BooleanLatticeElement other) {
        return this;
    }

    @Override
    public BooleanLatticeElement join(BooleanLatticeElement other) {
        return other;
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊥");
    }

    public boolean equals(Object other) {
        return other instanceof BottomBooleanLatticeElementImpl;
    }


    @Override
    public boolean containedIn(BooleanLatticeElement other) {
        return true;
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return bottom;
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
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.bottom;
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.bottom;
    }

    @Override
    public BooleanLatticeElement negate() {
        return top;
    }

    @Override
    public BooleanLatticeElement equalOperation(BooleanLatticeElement other) {
        return bottom;
    }

    @Override
    public BooleanLatticeElement notEqual(BooleanLatticeElement other) {
        return bottom;
    }

    @Override
    public BooleanLatticeElement lessThan(BooleanLatticeElement other) {
        return bottom;
    }

    @Override
    public BooleanLatticeElement lessThanEqual(BooleanLatticeElement other) {
        return bottom;
    }

    @Override
    public BooleanLatticeElement greaterThan(BooleanLatticeElement other) {
        return bottom;
    }

    @Override
    public BooleanLatticeElement greaterThanEqual(BooleanLatticeElement other) {
        return bottom;
    }
}
