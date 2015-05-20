package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

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
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof TopIndexLatticeElementImpl;
    }

    @Override
    public boolean containedIn(IndexLatticeElement other) {
        return equals(other);
    }
}
