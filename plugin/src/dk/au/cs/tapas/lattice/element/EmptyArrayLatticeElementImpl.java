package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by Silwing on 29-04-2015.
 */
public class EmptyArrayLatticeElementImpl implements EmptyArrayLatticeElement {
    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        return other.equals(bottom)?other:this;
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        return other.equals(bottom)?this:other;
    }

    @Override
    public boolean containedIn(ArrayLatticeElement other) {
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
        return StringLatticeElement.generateStringLatticeElement("Array");
    }

    @Override
    public boolean isRecursive(HeapMapLatticeElement latticeElement) {
        return false;
    }

    @Override
    public boolean isRecursive(HeapMapLatticeElement latticeElement, HeapLocation location) {
        return false;
    }
}
