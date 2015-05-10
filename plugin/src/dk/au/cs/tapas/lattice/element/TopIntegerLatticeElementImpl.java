package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopIntegerLatticeElementImpl implements IntegerLatticeElement {
    @Override
    public IntegerLatticeElement meet(IntegerLatticeElement other) {
        return other;
    }

    @Override
    public IntegerLatticeElement join(IntegerLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, IntegerLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return containedIn(other);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    public boolean equals(Object object){
        return object instanceof TopIntegerLatticeElementImpl;
    }

    @Override
    public boolean containedIn(IntegerLatticeElement other) {
        return equals(other);
    }
}
