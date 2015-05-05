package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface NumberLatticeElement extends MiddleCandidateLatticeElement<NumberLatticeElement>, Coercible{
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

    NumberLatticeElement increment();
    NumberLatticeElement decrement();

    NumberLatticeElement add(NumberLatticeElement other);

    NumberLatticeElement subtract(NumberLatticeElement other);

    NumberLatticeElement multiply(NumberLatticeElement other);

    NumberLatticeElement divide(NumberLatticeElement other);

    NumberLatticeElement modulo(NumberLatticeElement other);

    NumberLatticeElement exponent(NumberLatticeElement other);
}
