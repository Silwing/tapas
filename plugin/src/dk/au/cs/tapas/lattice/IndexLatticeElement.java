package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface IndexLatticeElement extends LatticeElement<IndexLatticeElement> {

    IndexLatticeElement top = new TopIndexLatticeElementImpl();
    IndexLatticeElement bottom = new BottomIndexLatticeElementImpl();

    static IndexLatticeElement generateIntegerIndex(IntegerLatticeElement element) {
        if (element.equals(IntegerLatticeElement.top)) {
            return top;
        }
        if (element instanceof ValueIntegerLatticeElement) {
            return new IntegerIndexLatticeElementImpl((ValueIntegerLatticeElement) element);
        }

        return bottom;
    }

    static IndexLatticeElement generateStringLIndex(StringLatticeElement element){
        if(element.equals(StringLatticeElement.top)){
            return top;
        }
        if(element instanceof ValueStringLatticeElement){
            return  new StringIndexLatticeElementImpl(element);
        }
        return  bottom;
    }

}
