package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopStringLatticeElementImpl implements StringLatticeElement {
    @Override
    public StringLatticeElement meet(StringLatticeElement other) {
        return other;
    }

    @Override
    public StringLatticeElement join(StringLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return equals(other);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    public boolean equals(Object obj){
        return  obj instanceof TopStringLatticeElementImpl;
    }
}
