package dk.au.cs.tapas.lattice.element;

/**
 * Created by budde on 5/4/15.
 */
public interface MiddleCandidateLatticeElement<T extends MiddleCandidateLatticeElement> extends LatticeElement<T>{

    boolean containedIn(T other);
}
