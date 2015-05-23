package dk.au.cs.tapas.lattice.element;

import java.util.function.Predicate;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface MapArrayLatticeElement extends ArrayLatticeElement{

    MapLatticeElement<IndexLatticeElement, HeapLocationPowerSetLatticeElement> getMap();

    MapArrayLatticeElement addValue(IndexLatticeElement key, MapLatticeElement.Generator<IndexLatticeElement, HeapLocationPowerSetLatticeElement> generator);

    HeapLocationPowerSetLatticeElement getValue(IndexLatticeElement key);

    MapArrayLatticeElement addValue(Predicate<IndexLatticeElement> test, MapLatticeElement.Generator<IndexLatticeElement, HeapLocationPowerSetLatticeElement> generator);

}
