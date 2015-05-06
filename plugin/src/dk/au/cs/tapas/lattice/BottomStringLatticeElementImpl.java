package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class BottomStringLatticeElementImpl implements StringLatticeElement{

    @Override
    public StringLatticeElement meet(StringLatticeElement other) {
        return this;
    }

    @Override
    public StringLatticeElement join(StringLatticeElement other) {
        return other;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, StringLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return containedIn(other);
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return true;
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊥");
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BottomStringLatticeElementImpl;
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
    public NumberLatticeElement concat(StringLatticeElement other) {
        return null;
    }

    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.bottom;
    }
    @Override
    public IndexLatticeElement toArrayIndex() {
        return null; //TODO implement
    }



}
