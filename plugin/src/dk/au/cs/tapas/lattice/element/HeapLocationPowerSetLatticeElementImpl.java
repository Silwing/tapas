package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.LatticePrinter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by budde on 4/19/15.
 */
public class HeapLocationPowerSetLatticeElementImpl implements HeapLocationPowerSetLatticeElement {

    private final Set<HeapLocation> values;

    public HeapLocationPowerSetLatticeElementImpl() {
        this(new HashSet<>());
    }

    public HeapLocationPowerSetLatticeElementImpl(Set<HeapLocation> values) {
        this.values = values;
    }

    public HeapLocationPowerSetLatticeElementImpl(HeapLocationPowerSetLatticeElement[] values) {
        this(new HashSet<>());

        for (HeapLocationPowerSetLatticeElement elm : values) {
            this.values.addAll(elm.getLocations());
        }
    }

    public HeapLocationPowerSetLatticeElementImpl(HeapLocation newLocation) {
        this(new HashSet<>(Arrays.asList(new HeapLocation[]{newLocation})));
    }

    @Override
    public Set<HeapLocation> getLocations() {
        return new HashSet<>(values);
    }

    @Override
    public HeapLocationPowerSetLatticeElement addLocation(HeapLocation value) {
        if (values.contains(value)) {
            return this;
        }
        Set<HeapLocation> newValues = getLocations();
        newValues.add(value);
        return new HeapLocationPowerSetLatticeElementImpl(newValues);

    }

    @Override
    public HeapLocationPowerSetLatticeElement addLocations(Set<HeapLocation> locations) {
        Set<HeapLocation> newValues = getLocations();
        newValues.addAll(locations);
        if (newValues.equals(getLocations())) {
            return this;
        }
        return new HeapLocationPowerSetLatticeElementImpl(newValues);
    }

    @Override
    public boolean isRecursive(HeapMapLatticeElement heapMap) {
        return values.stream().anyMatch(l -> heapMap.getValue(l).getArray().isRecursive(heapMap, l));
    }

    @Override
    public boolean isRecursive(HeapMapLatticeElement heapMap, HeapLocation location) {
        return values.contains(location) ||
                values.stream().anyMatch(l -> heapMap.getValue(l).getArray().isRecursive(heapMap, location));
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
    public boolean containedIn(HeapLocationPowerSetLatticeElement other) {
        return other.getLocations().containsAll(getLocations());
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print(getLocations().stream().map(HeapLocation::toString).collect(Collectors.joining(", ")));
    }

    public boolean equals(Object other) {
        return other == this || (other instanceof HeapLocationPowerSetLatticeElement && ((HeapLocationPowerSetLatticeElement) other).getLocations().equals(getLocations()));
    }


}
