package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface IntegerLatticeElement extends LatticeElement<IntegerLatticeElement>{

    IntegerLatticeElement bottom = new BottomIntegerLatticeElementImpl();
    IntegerLatticeElement top = new TopIntegerLatticeElementImpl();
    static IntegerLatticeElement generateElement(Integer integer){
        if(integer < 0){
            throw new UnsupportedOperationException();
        }
        return new ValueIntegerLatticeElementImpl(integer);
    }


}
