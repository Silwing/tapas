package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface IntegerLatticeElement extends MiddleCandidateLatticeElement<IntegerLatticeElement>{

    IntegerLatticeElement bottom = new BottomIntegerLatticeElementImpl();
    IntegerLatticeElement top = new TopIntegerLatticeElementImpl();
    static IntegerLatticeElement generateElement(Integer integer){
        return new ValueIntegerLatticeElementImpl(integer);
    }


}
