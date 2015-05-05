package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopBooleanLatticeElementImpl implements BooleanLatticeElement {
    @Override
    public BooleanLatticeElement meet(BooleanLatticeElement other) {
        return other;
    }

    @Override
    public BooleanLatticeElement join(BooleanLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(BooleanLatticeElement other) {
        return equals(other);
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, BooleanLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return containedIn(other);
    }


    @Override
    public void print(LatticePrinter printer) {
        printer.print("⊤");
    }

    public boolean equals(Object obj){
        return  obj instanceof TopBooleanLatticeElementImpl;
    }


    @Override
    public BooleanLatticeElement toBoolean() {
        return this;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.top;
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.top;
    }


    @Override
    public BooleanLatticeElement negate() {
        return this;
    }
}
