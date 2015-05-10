package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class StringIndexLatticeElementImpl implements StringIndexLatticeElement {
    private final StringLatticeElement string;

    public StringIndexLatticeElementImpl(StringLatticeElement element) {
        this.string = element;
    }

    @Override
    public IndexLatticeElement meet(IndexLatticeElement other) {
        if(other.equals(top)){
            return this;
        }
        if(other instanceof StringIndexLatticeElement){
            return IndexLatticeElement.generateStringLIndex(((StringIndexLatticeElement) other).getString().meet(getString()));
        }
        return  bottom;
    }

    @Override
    public IndexLatticeElement join(IndexLatticeElement other) {
        if(other.equals(bottom)){
            return this;
        }
        if(other instanceof StringIndexLatticeElement){
            return IndexLatticeElement.generateStringLIndex(((StringIndexLatticeElement) other).getString().join(getString()));
        }
        return  top;
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, IndexLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return containedIn(other);
    }

    @Override
    public void print(LatticePrinter printer) {
        string.print(printer);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof StringIndexLatticeElement && ((StringIndexLatticeElement) other).getString().equals(getString()));
    }

    @Override
    public StringLatticeElement getString() {
        return string;
    }


    @Override
    public boolean containedIn(IndexLatticeElement other) {
        if (other instanceof StringIndexLatticeElement){
            return getString().containedIn(((StringIndexLatticeElement) other).getString());
        }

        return other instanceof TopIndexLatticeElementImpl;
    }
}
