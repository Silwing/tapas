package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.Coercible;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface NumberLatticeElement extends MiddleCandidateLatticeElement<NumberLatticeElement>, Coercible {
    NumberLatticeElement bottom = new BottomNumberLatticeElementImpl();
    NumberLatticeElement top = new TopNumberLatticeElementImpl();
    NumberLatticeElement uIntTop = new UIntTopNumberLatticeElementImpl();
    NumberLatticeElement notUIntTop = new NotUIntTopNumberLatticeElementImpl();
    static NumberLatticeElement generateNumberLatticeElement(Number i){
        if(i.doubleValue() == i.intValue() && i.intValue() >= 0){
            return new UIntNumberLatticeElementImpl(i.intValue());
        }
        return  new NotUIntNumberLatticeElementImpl(i);
    }

    static Number parseNumberString(String text){
        if(text.matches("^(([-+]?[1-9][0-9]*)|0)$")){
            return Integer.parseInt(text);
        }

        if(text.matches("^([+-]?(0[xX][0-9a-fA-F]+))$")){
            return Long.decode(text);
        }

        if(text.matches("^([+-]?(0[0-7]+)$)")){
            return Long.decode(text);
        }

        if(text.matches("^([+-](0b[01]+))$")) {
            return Integer.parseInt(text.substring(3), 2);
        }
        if(text.matches("^(0b[01]+)$")){
            return Integer.parseInt(text.substring(2), 2);
        }

        if(text.matches("^([+-]?(([0-9]*[\\.][0-9]+)|([0-9]+[\\.][0-9]*)))$")){
            return Double.parseDouble(text);
        }

        if(text.matches("^([+-]?(([0-9]+|([0-9]*[\\.][0-9]+)|([0-9]+[\\.][0-9]*))[eE][+-]?[0-9]+))$")){
            return Double.valueOf(text).longValue();
        }
        return null;
    }

    NumberLatticeElement increment();
    NumberLatticeElement decrement();

    NumberLatticeElement add(NumberLatticeElement other);

    NumberLatticeElement subtract(NumberLatticeElement other);

    NumberLatticeElement multiply(NumberLatticeElement other);

    ValueLatticeElement divide(NumberLatticeElement other);

    ValueLatticeElement modulo(NumberLatticeElement other);

    NumberLatticeElement exponent(NumberLatticeElement other);

    BooleanLatticeElement equalOperation(NumberLatticeElement other);

    BooleanLatticeElement notEqual(NumberLatticeElement other);

    BooleanLatticeElement greaterThan(NumberLatticeElement other);

    BooleanLatticeElement lessThan(NumberLatticeElement other);

    BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other);

    BooleanLatticeElement lessThanOrEqual(NumberLatticeElement numberLatticeElement);

    NumberLatticeElement minus();
}
