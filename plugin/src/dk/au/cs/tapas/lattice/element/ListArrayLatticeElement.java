package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface ListArrayLatticeElement extends ArrayLatticeElement{

    HeapLocationPowerSetLatticeElement getLocations();

    ListArrayLatticeElement addLocation(HeapLocation location);

    ListArrayLatticeElement addLocations(Set<HeapLocation> locations);


}
