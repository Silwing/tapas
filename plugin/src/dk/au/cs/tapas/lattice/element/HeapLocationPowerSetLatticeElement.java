package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface HeapLocationPowerSetLatticeElement extends LatticeElement<HeapLocationPowerSetLatticeElement>{


    Set<HeapLocation> getLocations();


    @Override
    boolean containedIn(HeapLocationPowerSetLatticeElement other);

    HeapLocationPowerSetLatticeElement addLocation(HeapLocation value);

    HeapLocationPowerSetLatticeElement addLocations(Set<HeapLocation> locations);

    boolean isRecursive(HeapMapLatticeElement heapMap);
    boolean isRecursive(HeapMapLatticeElement heapMap, HeapLocation location);

}
