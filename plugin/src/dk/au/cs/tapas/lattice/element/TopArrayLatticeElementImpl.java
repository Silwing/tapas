package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopArrayLatticeElementImpl implements ArrayLatticeElement {
    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        return other;
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, ArrayLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return equals(other);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    public boolean equals(Object obj){
        return  obj instanceof TopArrayLatticeElementImpl;
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.top;
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
}
