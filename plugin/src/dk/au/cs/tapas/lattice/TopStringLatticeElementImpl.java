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
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, StringLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return containedIn(other);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    public boolean equals(Object obj){
        return  obj instanceof TopStringLatticeElementImpl;
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return equals(other);
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.top;
    }

    @Override
<<<<<<< f4c0a8684b36ca38ed917ef1aab3e5e6939d0998
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.top;
    }

    @Override
    public NumberLatticeElement concat(StringLatticeElement other) {
        return null;
=======
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.top;
>>>>>>> 48787faedda6ae77521f1ee71f373e6bde9e04fe
    }
}
