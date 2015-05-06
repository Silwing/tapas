package dk.au.cs.tapas.lattice;

import java.util.Set;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface HeapLocationPowerSetLatticeElement extends LatticeElement<HeapLocationPowerSetLatticeElement>{


    Set<HeapLocation> getLocations();


    @Override
    boolean containedIn(HeapMapLatticeElement thisAnalysis, HeapLocationPowerSetLatticeElement other, HeapMapLatticeElement otherAnalysis);

    HeapLocationPowerSetLatticeElement addLocation(HeapLocation value);

    HeapLocationPowerSetLatticeElement addLocations(Set<HeapLocation> locations);
}
