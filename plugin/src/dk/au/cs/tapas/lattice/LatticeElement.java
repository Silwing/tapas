package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface LatticeElement<T extends LatticeElement, S extends LatticeElement> {

    T meet(T other);

    T join(T other);

    boolean containedIn(S thisAnalysis, T other, S otherAnalysis);

    void print(LatticePrinter printer);
}
