package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.Coercible;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface StringLatticeElement extends MiddleCandidateLatticeElement<StringLatticeElement>, Coercible {
    StringLatticeElement bottom = new BottomStringLatticeElementImpl();
    StringLatticeElement top = new TopStringLatticeElementImpl();
    StringLatticeElement uIntStringTop = new UIntTopStringLatticeElementImpl();
    StringLatticeElement notUIntStringTop = new NotUIntTopStringLatticeElementImpl();
    static StringLatticeElement generateStringLatticeElement(String string){
        if(string.matches("[1-9][0-9]*")){
            return new UIntStringLatticeElementImpl(string);
        }
        return new NotUIntStringLatticeElementImpl(string);
    }

    StringLatticeElement concat(StringLatticeElement other);
    BooleanLatticeElement equalOperation(StringLatticeElement other);
    BooleanLatticeElement notEqual(StringLatticeElement other);
    BooleanLatticeElement lessThan(StringLatticeElement other);
    BooleanLatticeElement lessThanEqual(StringLatticeElement other);
    BooleanLatticeElement greaterThan(StringLatticeElement other);
    BooleanLatticeElement greaterThanEqual(StringLatticeElement other);
}
