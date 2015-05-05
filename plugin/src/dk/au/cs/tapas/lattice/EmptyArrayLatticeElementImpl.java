package dk.au.cs.tapas.lattice;

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
        return null;
    }
}
