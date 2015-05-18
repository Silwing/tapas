package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopBooleanLatticeElementImpl implements BooleanLatticeElement {
    @Override
    public BooleanLatticeElement meet(BooleanLatticeElement other) {
        return other;
    }

    @Override
    public BooleanLatticeElement join(BooleanLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(BooleanLatticeElement other) {
        return equals(other);
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, BooleanLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return containedIn(other);
    }


    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    public boolean equals(Object obj){
        return  obj instanceof TopBooleanLatticeElementImpl;
    }


    @Override
    public BooleanLatticeElement toBoolean() {
        return this;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.top;
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
    public BooleanLatticeElement negate() {
        return this;
    }

    @Override
    public BooleanLatticeElement equalOperation(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        return top;
    }

    @Override
    public BooleanLatticeElement notEqual(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        return top;
    }

    @Override
    public BooleanLatticeElement lessThan(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(boolFalse)) return boolFalse;

        return top;
    }

    @Override
    public BooleanLatticeElement lessThanEqual(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(boolTrue)) return boolTrue;

        return top;
    }

    @Override
    public BooleanLatticeElement greaterThan(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(boolTrue)) return boolFalse;

        return top;
    }

    @Override
    public BooleanLatticeElement greaterThanEqual(BooleanLatticeElement other) {
        if(other.equals(bottom)) return bottom;

        if(other.equals(boolFalse)) return boolTrue;

        return top;
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateIntegerIndex(toInteger());
    }
}
