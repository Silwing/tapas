package dk.au.cs.tapas.lattice;

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
    public boolean containedIn(IntegerLatticeElement other) {
        return other.equals(this) || other.equals(top);
    }

    public boolean equals(IntegerLatticeElement object){
        return  object instanceof  ValueIntegerLatticeElement && ((ValueIntegerLatticeElement) object).getInteger().equals(getInteger());
    }
}
