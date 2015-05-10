package dk.au.cs.tapas.lattice.element;

/**
 * Created by budde on 4/20/15.
 *
 */
public interface ValueNumberLatticeElement<T extends Number> extends NumberLatticeElement{
    //TODO more precision on calculations
    T getNumber();
}
