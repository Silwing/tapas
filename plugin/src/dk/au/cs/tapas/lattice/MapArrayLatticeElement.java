package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface MapArrayLatticeElement extends ArrayLatticeElement{

    MapLatticeElement<IndexLatticeElement, PowerSetLatticeElement<HeapLocation>> getMap();

    MapArrayLatticeElement addValue(IndexLatticeElement key, MapLatticeElement.Generator<IndexLatticeElement, PowerSetLatticeElement<HeapLocation>> generator);

}
