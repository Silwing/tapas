package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class ValueIntegerLatticeElementImpl extends MiddleLatticeElementImpl<IntegerLatticeElement> implements ValueIntegerLatticeElement {


    private final Integer integer;

    public ValueIntegerLatticeElementImpl(Integer integer) {
        super((IntegerLatticeElement) -> bottom, (IntegerLatticeElement) -> top);
        this.integer = integer;
    }

    @Override
    public Integer getInteger() {
        return integer;
    }

    @Override
    public boolean containedIn( IntegerLatticeElement other) {
        return other.equals(this) || other.equals(top);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print(integer.toString());
    }

    public boolean equals(Object object){
        return object == this || ( object instanceof  ValueIntegerLatticeElement && ((ValueIntegerLatticeElement) object).getInteger().equals(getInteger()));
    }
}
