package dk.au.cs.tapas.lattice;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by budde on 4/19/15.
 *
 */
public class HeapLocationPowerSetLatticeElementImpl implements HeapLocationPowerSetLatticeElement {

    private final Set<HeapLocation> values;

    public HeapLocationPowerSetLatticeElementImpl(){
        this(new HashSet<>());
    }

    public HeapLocationPowerSetLatticeElementImpl(Set<HeapLocation> values) {
        this.values = values;
    }

    @Override
    public Set<HeapLocation> getLocations() {
        return new HashSet<>(values);
    }

    @Override
    public HeapLocationPowerSetLatticeElement addLocation(HeapLocation value) {
        if(values.contains(value)){
            return this;
        }
        Set<HeapLocation> newValues  = getLocations();
        newValues.add(value);
        return new HeapLocationPowerSetLatticeElementImpl(newValues);

    }

    @Override
    public HeapLocationPowerSetLatticeElement meet(HeapLocationPowerSetLatticeElement other) {
        Set<HeapLocation> newValues = other.getLocations();
        newValues.stream().filter(key -> !values.contains(key)).forEach(newValues::remove);
        return new HeapLocationPowerSetLatticeElementImpl(newValues);
    }

    @Override
    public HeapLocationPowerSetLatticeElement join(HeapLocationPowerSetLatticeElement other) {
        Set<HeapLocation> newValues = other.getLocations();
        newValues.addAll(values);
        return new HeapLocationPowerSetLatticeElementImpl(newValues);
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, HeapLocationPowerSetLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return thisAnalysis.getValue(getLocations(), LatticeElement::join).containedIn(thisAnalysis,
                otherAnalysis.getValue(other.getLocations(), LatticeElement::join), otherAnalysis);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print(getLocations().stream().map(HeapLocation::toString).collect(Collectors.joining(", ")));
    }

    public boolean equals(Object other) {
        return other == this || (other instanceof HeapLocationPowerSetLatticeElement && ((HeapLocationPowerSetLatticeElement) other).getLocations().equals(getLocations()));
    }


}
