package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomIntegerLatticeElementImpl implements IntegerLatticeElement {
    @Override
    public IntegerLatticeElement meet(IntegerLatticeElement other) {
        return this;
    }

    @Override
    public IntegerLatticeElement join(IntegerLatticeElement other) {
        return other;
    }


    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊥");
    }

    public boolean equals(Object object){
        return  object instanceof BottomIntegerLatticeElementImpl;
    }

    @Override
    public boolean containedIn(IntegerLatticeElement other) {
        return true;
    }
}
