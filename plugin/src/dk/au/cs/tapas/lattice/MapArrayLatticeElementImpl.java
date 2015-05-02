package dk.au.cs.tapas.lattice;

import java.util.List;

/**
 * Created by budde on 4/20/15.
 *
 */
public class MapArrayLatticeElementImpl implements MapArrayLatticeElement {
    final private MapLatticeElement<IndexLatticeElement, PowerSetLatticeElement<HeapLocation>> map;

    public MapArrayLatticeElementImpl() {
        this(new MapLatticeElementImpl<>((IndexLatticeElement) -> new PowerSetLatticeElementImpl<>()));
    }

    public MapArrayLatticeElementImpl(MapLatticeElement<IndexLatticeElement, PowerSetLatticeElement<HeapLocation>> map) {
        this.map = map;
    }


    @Override
    public MapLatticeElement<IndexLatticeElement, PowerSetLatticeElement<HeapLocation>> getMap() {
        return map;
    }

    @Override
    public MapArrayLatticeElement addValue(IndexLatticeElement key, MapLatticeElement.Generator<IndexLatticeElement, PowerSetLatticeElement<HeapLocation>> generator) {
        return new MapArrayLatticeElementImpl(getMap().addValue(key, generator));
    }

    @Override
    public ArrayLatticeElement meet(ArrayLatticeElement other) {
        if(other.equals(top)){
            return this;
        }

        if(other instanceof ListArrayLatticeElement){
            return  emptyArray;
        }

        if(!(other instanceof MapArrayLatticeElement)){
            return other;
        }
        return new MapArrayLatticeElementImpl(((MapArrayLatticeElement) other).getMap().meet(getMap()));
    }

    @Override
    public ArrayLatticeElement join(ArrayLatticeElement other) {
        if(other.equals(bottom) || other.equals(emptyArray)){
            return this;
        }

        if(!(other instanceof MapArrayLatticeElement)){
            return top;
        }
        return new MapArrayLatticeElementImpl(((MapArrayLatticeElement) other).getMap().join(getMap()));
    }

    @Override
    public boolean containedIn(ArrayLatticeElement other) {
        return other instanceof TopArrayLatticeElementImpl || (other  instanceof MapArrayLatticeElement && getMap().containedIn(((MapArrayLatticeElement) other).getMap()));
    }

    @Override
    public void print(LatticePrinter printer) {
        map.print(printer);
    }

    public boolean equals(Object other){
        return other == this || (other instanceof MapArrayLatticeElement && ((MapArrayLatticeElement) other).getMap().equals(getMap()));
    }

}
