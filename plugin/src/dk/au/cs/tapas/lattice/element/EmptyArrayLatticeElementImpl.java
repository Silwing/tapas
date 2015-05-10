package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by Silwing on 29-04-2015.
 */
public class EmptyArrayLatticeElementImpl implements ArrayLatticeElement {
    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        return other.equals(bottom)?other:this;
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        return other.equals(bottom)?this:other;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, ArrayLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return !other.equals(bottom);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("[]");
    }

    @Override
    public boolean equals(Object other) {
        return  other instanceof EmptyArrayLatticeElement;
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.boolFalse;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.top;
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.generateElement(0);
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.bottom;
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.generateStringLatticeElement("Array");
    }
}