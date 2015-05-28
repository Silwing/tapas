package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopStringLatticeElementImpl implements StringLatticeElement {
    @Override
    public StringLatticeElement meet(StringLatticeElement other) {
        return other;
    }

    @Override
    public StringLatticeElement join(StringLatticeElement other) {
        return this;
    }


    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    public boolean equals(Object obj){
        return  obj instanceof TopStringLatticeElementImpl;
    }

    public int hashCode(){
        return 721096509;
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return equals(other);
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.top;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.top;
    }

    @Override
    public StringLatticeElement concat(StringLatticeElement other) {
        return this;
    }

    @Override
    public BooleanLatticeElement equalOperation(StringLatticeElement other) {
        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement notEqual(StringLatticeElement other) {
        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(StringLatticeElement other) {
        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThanEqual(StringLatticeElement other) {
        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThan(StringLatticeElement other) {
        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanEqual(StringLatticeElement other) {
        return BooleanLatticeElement.top;
    }

    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.top;

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
