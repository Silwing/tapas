package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public abstract class LatticeElementImpl<T extends LatticeElement<T>> implements LatticeElement<T> {

    public boolean equals(T other) {
        return other.containedIn((T) this) && containedIn(other);

    }

}
