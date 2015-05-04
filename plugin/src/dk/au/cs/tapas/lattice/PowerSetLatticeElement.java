package dk.au.cs.tapas.lattice;

import java.util.Set;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface PowerSetLatticeElement<T> extends LatticeElement<PowerSetLatticeElement<T>>{


    Set<T> getValues();


    PowerSetLatticeElement<T> addValue(T value);

}
