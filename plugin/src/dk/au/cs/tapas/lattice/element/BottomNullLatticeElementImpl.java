package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

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
    public boolean containedIn(NullLatticeElement other) {
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

    public int hashCode(){
        return 522633506;
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


}
