package dk.au.cs.tapas.lattice.element;


import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.LatticePrinter;

import java.util.Set;

/**
 * Created by budde on 4/20/15.
 */
public class ListArrayLatticeElementImpl implements ListArrayLatticeElement {

    final HeapLocationPowerSetLatticeElement locations;

    public ListArrayLatticeElementImpl() {
        this(new HeapLocationPowerSetLatticeElementImpl());
    }

    public ListArrayLatticeElementImpl(HeapLocationPowerSetLatticeElement locations) {
        this.locations = locations;
    }

    public ListArrayLatticeElementImpl(Set<HeapLocation> valueLocationSet) {
        this(new HeapLocationPowerSetLatticeElementImpl(valueLocationSet));
    }

    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        if (other.equals(top)) {
            return this;
        }

        if (other instanceof MapArrayLatticeElement) {
            return emptyArray;
        }

        if (!(other instanceof ListArrayLatticeElement)) {
            return other;
        }
        return new ListArrayLatticeElementImpl(((ListArrayLatticeElement) other).getLocations().meet(getLocations()));
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        if (other.equals(bottom) || other.equals(emptyArray)) {
            return this;
        }
        if (!(other instanceof ListArrayLatticeElement)) {
            return top;
        }
        return new ListArrayLatticeElementImpl(((ListArrayLatticeElement) other).getLocations().join(getLocations()));
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, ArrayLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        if (other.equals(top)) {
            return true;
        }

        if(other.isRecursive(otherAnalysis)) {
            return true;
        }

        if (!(other instanceof ListArrayLatticeElement)) {
            return false;
        }

        return !isRecursive(thisAnalysis) && getLocations().containedIn(thisAnalysis, ((ListArrayLatticeElement) other).getLocations(), otherAnalysis);
    }

    @Override
    public void print(LatticePrinter printer) {
        if (locations.getLocations().isEmpty())
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

    @Override
    public ListArrayLatticeElement addLocations(Set<HeapLocation> locations) {
        return new ListArrayLatticeElementImpl(getLocations().addLocations(locations));
    }

    public boolean equals(Object object) {
        return object == this || (object instanceof ListArrayLatticeElement && ((ListArrayLatticeElement) object).getLocations().equals(getLocations()));
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.top;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.bottom;
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.bottom;
    }


    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.bottom;
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.generateStringLatticeElement("Array");
    }

    @Override
    public boolean isRecursive(HeapMapLatticeElement latticeElement) {
        return locations.isRecursive(latticeElement);
    }


    @Override
    public boolean isRecursive(HeapMapLatticeElement latticeElement, HeapLocation location) {
        return locations.isRecursive(latticeElement, location);
    }


}

