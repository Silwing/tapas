package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class UIntNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements ValueNumberLatticeElement {
    private final Number number;

    public UIntNumberLatticeElementImpl(Number number) {
        super(
                (NumberLatticeElement n) -> bottom,
                (NumberLatticeElement n) -> n instanceof UIntNumberLatticeElementImpl?uIntTop:top
        );
        this.number = number;
    }


    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return other.equals(this) || uIntTop.containedIn(other);
    }


    public boolean equals(NumberLatticeElement object){
        return object instanceof UIntNumberLatticeElementImpl && ((UIntNumberLatticeElementImpl) object).getNumber().equals(getNumber());
    }

    @Override
    public Number getNumber() {
        return number;
    }
}
