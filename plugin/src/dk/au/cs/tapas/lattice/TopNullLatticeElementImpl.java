package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class TopNullLatticeElementImpl implements NullLatticeElement {
    @Override
    public NullLatticeElement meet(NullLatticeElement other) {
        return other;
    }

    @Override
    public NullLatticeElement join(NullLatticeElement other) {
        return this;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, NullLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return equals(other);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("Null");
    }

    public boolean equals(Object obj){
        return  obj instanceof TopNullLatticeElementImpl;
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.boolFalse;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.generateNumberLatticeElement(0);
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.generateElement(0);
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.generateStringLatticeElement("");
    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateStringLIndex(toStringLattice());
    }
}
