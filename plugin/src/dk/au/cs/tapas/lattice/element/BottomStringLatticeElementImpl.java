package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomStringLatticeElementImpl implements StringLatticeElement{

    @Override
    public StringLatticeElement meet(StringLatticeElement other) {
        return this;
    }

    @Override
    public StringLatticeElement join(StringLatticeElement other) {
        return other;
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return true;
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊥");
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BottomStringLatticeElementImpl;
    }

    public int hashCode(){
        return 650023044;
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
    public StringLatticeElement concat(StringLatticeElement other) {
        return StringLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement equalOperation(StringLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public BooleanLatticeElement notEqual(StringLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public BooleanLatticeElement lessThan(StringLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public BooleanLatticeElement lessThanEqual(StringLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public BooleanLatticeElement greaterThan(StringLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public BooleanLatticeElement greaterThanEqual(StringLatticeElement other) {
        return BooleanLatticeElement.bottom;
    }

    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.bottom;
    }
    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.bottom;
    }


    @Override
    public StringLatticeElement toStringLattice() {
        return this;
    }


}
