package dk.au.cs.tapas.lattice;

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
            return IndexLatticeElement.generateStringLIndex(((StringIndexLatticeElement) other).getString().meet(getString()));
        }
        return  top;
    }

    @Override
    public boolean containedIn(IndexLatticeElement other) {
        return other instanceof TopIndexLatticeElementImpl || other.equals(this);
    }

    @Override
    public boolean equals(IndexLatticeElement other) {
        return other instanceof StringIndexLatticeElement && ((StringIndexLatticeElement) other).getString().equals(getString());
    }

    @Override
    public StringLatticeElement getString() {
        return string;
    }


}