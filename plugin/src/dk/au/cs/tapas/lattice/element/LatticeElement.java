package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface LatticeElement<T extends LatticeElement> {

    T meet(T other);

    T join(T other);

    boolean containedIn(T other);

    void print(LatticePrinter printer);
}
