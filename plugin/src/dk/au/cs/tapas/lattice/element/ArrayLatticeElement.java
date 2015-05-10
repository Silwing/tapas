package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.Coercible;
import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface ArrayLatticeElement extends LatticeElement<ArrayLatticeElement>, Coercible {

    ArrayLatticeElement bottom = new BottomArrayLatticeElementImpl();
    ArrayLatticeElement emptyArray = new EmptyArrayLatticeElementImpl();
    ArrayLatticeElement top = new TopArrayLatticeElementImpl();

    static ListArrayLatticeElement generateList(HeapLocationPowerSetLatticeElement locations){
        return new ListArrayLatticeElementImpl(locations);
    }

    static ListArrayLatticeElement generateList(){
        return  new ListArrayLatticeElementImpl();
    }

    static MapArrayLatticeElement generateMap(MapLatticeElement<IndexLatticeElement, HeapLocationPowerSetLatticeElement> map){
        return new MapArrayLatticeElementImpl(map);
    }

    static MapArrayLatticeElement generateMap(){
        return new MapArrayLatticeElementImpl();
    }

    static ListArrayLatticeElement generateList(Set<HeapLocation> locationSet) {
        return generateList(new HeapLocationPowerSetLatticeElementImpl(locationSet));
    }

    static MapArrayLatticeElement generateMap(IndexLatticeElement index, Set<HeapLocation> locationSet) {
        MapLatticeElement<IndexLatticeElement, HeapLocationPowerSetLatticeElement> map = new MapLatticeElementImpl<>(i -> new HeapLocationPowerSetLatticeElementImpl());
        return generateMap(map.addValue(index, i -> new HeapLocationPowerSetLatticeElementImpl(locationSet)));
    }
}
