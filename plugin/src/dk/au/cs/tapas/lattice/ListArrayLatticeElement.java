package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface ListArrayLatticeElement extends ArrayLatticeElement{

    HeapLocationPowerSetLatticeElement getLocations();

    ListArrayLatticeElement addLocation(HeapLocation location);

}
