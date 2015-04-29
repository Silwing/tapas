package dk.au.cs.tapas.lattice;


/**
 * Created by budde on 4/20/15.
 *
 */
public class ListArrayLatticeElementImpl implements ListArrayLatticeElement {

    final PowerSetLatticeElement<HeapLocation> locations;

    public ListArrayLatticeElementImpl() {
        this(new PowerSetLatticeElementImpl<>());
    }

    public ListArrayLatticeElementImpl(PowerSetLatticeElement<HeapLocation> locations) {
        this.locations = locations;
    }

    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        if(other.equals(top)){
            return this;
        }

        if(other instanceof MapArrayLatticeElement){
            return emptyArray;
        }

        if(!(other instanceof  ListArrayLatticeElement)){
            return other;
        }
        return new ListArrayLatticeElementImpl(((ListArrayLatticeElement) other).getLocations().meet(getLocations()));
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        if(other.equals(bottom) || other.equals(emptyArray)){
            return this;
        }
        if(!(other instanceof  ListArrayLatticeElement)){
            return top;
        }
        return new ListArrayLatticeElementImpl(((ListArrayLatticeElement) other).getLocations().join(getLocations()));
    }

    @Override
    public boolean containedIn(ArrayLatticeElement other) {
        return other.equals(top) || (other instanceof ListArrayLatticeElement && getLocations().containedIn(((ListArrayLatticeElement) other).getLocations()));
    }

    @Override
    public PowerSetLatticeElement<HeapLocation> getLocations() {
        return locations;
    }

    @Override
    public ListArrayLatticeElement addLocation(HeapLocation location) {
        return new ListArrayLatticeElementImpl(getLocations().addValue(location));
    }

    public boolean equals(ArrayLatticeElement object) {
        return  object instanceof  ListArrayLatticeElement && ((ListArrayLatticeElement) object).getLocations().equals(getLocations());
    }

}

