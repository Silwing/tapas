package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class UIntTopNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements NumberLatticeElement{

    public UIntTopNumberLatticeElementImpl() {
        super(
                (NumberLatticeElement n) -> bottom,
                (NumberLatticeElement n) -> top
        );
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return other.equals(this) || other.equals(top);
    }


    public boolean equals(Object object){
        return object instanceof UIntTopNumberLatticeElementImpl;
    }

}
