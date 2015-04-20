package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface NumberLatticeElement extends LatticeElement<NumberLatticeElement>{
    NumberLatticeElement bottom = new BottomNumberLatticeElementImpl();
    NumberLatticeElement top = new TopNumberLatticeElementImpl();
    NumberLatticeElement uIntTop = new UIntTopNumberLatticeElementImpl();
    NumberLatticeElement notUIntTop = new NotUIntTopNumberLatticeElementImpl();
    static NumberLatticeElement generateNumberLatticeElement(Number i){
        if(i instanceof Integer && i.intValue() >= 0){
            return new UIntNumberLatticeElementImpl(i);
        }
        return  new NotUIntNumberLatticeElementImpl(i);
    }

}
