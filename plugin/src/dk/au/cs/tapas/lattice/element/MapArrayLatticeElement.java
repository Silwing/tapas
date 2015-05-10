package dk.au.cs.tapas.lattice.element;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface MapArrayLatticeElement extends ArrayLatticeElement{

    MapLatticeElement<IndexLatticeElement, HeapLocationPowerSetLatticeElement> getMap();

    MapArrayLatticeElement addValue(IndexLatticeElement key, MapLatticeElement.Generator<IndexLatticeElement, HeapLocationPowerSetLatticeElement> generator);

    HeapLocationPowerSetLatticeElement getValue(IndexLatticeElement key);
}
