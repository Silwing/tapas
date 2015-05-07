package dk.au.cs.tapas.lattice;

import java.util.HashSet;
import java.util.Set;

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
        if(key.equals(IndexLatticeElement.top)) return new HeapLocationPowerSetLatticeElementImpl(getMap().getValues());
        if(key.equals(IndexLatticeElement.bottom)) return new HeapLocationPowerSetLatticeElementImpl();

        Set<HeapLocationPowerSetLatticeElement> locations = new HashSet<>();

        for(IndexLatticeElement index : getMap().getDomain()) {
            if(key.containedIn(index) || index.containedIn(key)) {
                locations.add(getMap().getValue(index));
            }
        }

        return new HeapLocationPowerSetLatticeElementImpl(locations.toArray(new HeapLocationPowerSetLatticeElement[locations.size()]));
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
        return NumberLatticeElement.top;
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.top;
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.generateStringLatticeElement("Array");
    }
    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.bottom;
    }
}
