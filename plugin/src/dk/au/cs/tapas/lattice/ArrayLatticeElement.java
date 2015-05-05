package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface ArrayLatticeElement extends LatticeElement<ArrayLatticeElement> {

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
}
