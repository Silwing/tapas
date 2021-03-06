package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomArrayLatticeElementImpl implements ArrayLatticeElement{

    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        return this;
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        return other;
    }

    @Override
    public boolean containedIn(ArrayLatticeElement other) {
        return true;
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊥");
    }

    public boolean equals(Object obj) {
        return obj instanceof BottomArrayLatticeElementImpl;
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
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.bottom;
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.bottom;
    }

    @Override
    public boolean isRecursive(HeapMapLatticeElement latticeElement) {
        return false;
    }

    @Override
    public boolean isRecursive(HeapMapLatticeElement latticeElement, HeapLocation location) {
        return false;
    }

    public int hashCode(){
        return 115297698;
    }
}
