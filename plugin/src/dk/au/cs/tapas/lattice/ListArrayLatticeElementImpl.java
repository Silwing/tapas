package dk.au.cs.tapas.lattice;


/**
 * Created by budde on 4/20/15.
 *
 */
public class ListArrayLatticeElementImpl implements ListArrayLatticeElement {

    final HeapLocationPowerSetLatticeElement locations;

    public ListArrayLatticeElementImpl() {
        this(new HeapLocationPowerSetLatticeElementImpl());
    }

    public ListArrayLatticeElementImpl(HeapLocationPowerSetLatticeElement locations) {
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
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, ArrayLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return other.equals(top) || (other instanceof ListArrayLatticeElement && getLocations().containedIn(
                thisAnalysis,
                ((ListArrayLatticeElement) other).getLocations(),
                otherAnalysis));
    }

    @Override
    public void print(LatticePrinter printer) {
        if(locations.getLocations().isEmpty())
            printer.print("[]");
        else {
            printer.print("[");
            locations.print(printer);
            printer.print("]");
        }
    }

    @Override
    public HeapLocationPowerSetLatticeElement getLocations() {
        return locations;
    }

    @Override
    public ListArrayLatticeElement addLocation(HeapLocation location) {
        return new ListArrayLatticeElementImpl(getLocations().addLocation(location));
    }

    public boolean equals(Object object) {
        return  object == this || (object instanceof  ListArrayLatticeElement && ((ListArrayLatticeElement) object).getLocations().equals(getLocations()));
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.boolTrue;
    }
}

