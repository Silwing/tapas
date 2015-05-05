package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomNullLatticeElementImpl implements NullLatticeElement{

    @Override
    public NullLatticeElement meet(NullLatticeElement other) {
        return this;
    }

    @Override
    public NullLatticeElement join(NullLatticeElement other) {
        return other;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, NullLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return true;
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊥");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BottomNullLatticeElementImpl;
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.bottom;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return null;
    }
}
