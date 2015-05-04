package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopIndexLatticeElementImpl implements IndexLatticeElement {
    @Override
    public IndexLatticeElement meet(IndexLatticeElement other) {
        return other;
    }

    @Override
    public IndexLatticeElement join(IndexLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, IndexLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return equals(other);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof TopIndexLatticeElementImpl;
    }
}
