package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 5/4/15.
 */
public interface MiddleCandidateLatticeElement<T extends MiddleCandidateLatticeElement> extends LatticeElement<T, HeapMapLatticeElement>{

    boolean containedIn(T other);
}
