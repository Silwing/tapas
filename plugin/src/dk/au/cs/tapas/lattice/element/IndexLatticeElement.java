package dk.au.cs.tapas.lattice.element;

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
        else if (element.equals(IntegerLatticeElement.bottom)) {
            return bottom;
        }
        else {
            return new IntegerIndexLatticeElementImpl((ValueIntegerLatticeElement) element);
        }
    }

    static IndexLatticeElement generateStringLIndex(StringLatticeElement element){
        if(element.equals(StringLatticeElement.top)){
            return top;
        }
        else if (element.equals(StringLatticeElement.bottom)) {
            return bottom;
        }
        else {
            return new StringIndexLatticeElementImpl(element);
        }
    }
    static IndexLatticeElement generateStringLIndex(String element){
        return generateStringLIndex(StringLatticeElement.generateStringLatticeElement(element));
    }

    boolean containedIn(IndexLatticeElement other);

    static IndexLatticeElement generateIntegerIndex(int i) {
        return generateIntegerIndex(IntegerLatticeElement.generateElement(i));
    }
}
