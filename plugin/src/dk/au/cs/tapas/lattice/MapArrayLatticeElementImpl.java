package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class MapArrayLatticeElementImpl implements MapArrayLatticeElement {
    final private MapLatticeElement<IndexLatticeElement, HeapLocationPowerSetLatticeElement> map;

    public MapArrayLatticeElementImpl() {
        this(new MapLatticeElementImpl<>((IndexLatticeElement) -> new HeapLocationPowerSetLatticeElementImpl()));
    }

    public MapArrayLatticeElementImpl(MapLatticeElement<IndexLatticeElement, HeapLocationPowerSetLatticeElement> map) {
        this.map = map;
    }


    @Override
    public MapLatticeElement<IndexLatticeElement, HeapLocationPowerSetLatticeElement> getMap() {
        return map;
    }

    @Override
    public MapArrayLatticeElement addValue(IndexLatticeElement key, MapLatticeElement.Generator<IndexLatticeElement, HeapLocationPowerSetLatticeElement> generator) {
        return new MapArrayLatticeElementImpl(getMap().addValue(key, generator));
    }

    @Override
    public HeapLocationPowerSetLatticeElement getValue(IndexLatticeElement key) {
        return getMap().getValue(key);
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
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, ArrayLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return other instanceof TopArrayLatticeElementImpl || (other  instanceof MapArrayLatticeElement && getMap().containedIn(
                thisAnalysis,
                ((MapArrayLatticeElement) other).getMap(),
                otherAnalysis));
    }

    @Override
    public void print(LatticePrinter printer) {
        map.print(printer);
    }

    public boolean equals(Object other){
        return other == this || (other instanceof MapArrayLatticeElement && ((MapArrayLatticeElement) other).getMap().equals(getMap()));
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.boolTrue;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return null;
    }
}
