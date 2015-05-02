package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TrueBooleanLatticeElement extends MiddleLatticeElementImpl<BooleanLatticeElement> implements BooleanLatticeElement{
    public TrueBooleanLatticeElement() {
        super((BooleanLatticeElement b1) -> bottom, (BooleanLatticeElement b1) -> top);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TrueBooleanLatticeElement;
    }

    @Override
    public boolean containedIn(BooleanLatticeElement other) {
        return other.equals(top) || other.equals(this);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("true");
    }


}
