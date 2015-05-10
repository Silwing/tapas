package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.Coercible;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface BooleanLatticeElement extends MiddleCandidateLatticeElement<BooleanLatticeElement>, Coercible {
    BooleanLatticeElement bottom = new BottomBooleanLatticeElementImpl();
    BooleanLatticeElement top = new TopBooleanLatticeElementImpl();
    BooleanLatticeElement boolTrue = new TrueBooleanLatticeElement();
    BooleanLatticeElement boolFalse = new FalseBooleanLatticeElementImpl();
    static BooleanLatticeElement generateBooleanLatticeElement(Boolean bool) {
        if(bool.booleanValue())
            return boolTrue;
        else
            return boolFalse;
    }

    BooleanLatticeElement negate();

}
