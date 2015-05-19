package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.LatticePrinter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        if(key.equals(IndexLatticeElement.top)) {
            Set<HeapLocation> locations = getMap().getValues().stream().map(HeapLocationPowerSetLatticeElement::getLocations).reduce(new HashSet<>(), (set, powerSet) -> {
                set.addAll(powerSet);
                return set;
            });
            return new HeapLocationPowerSetLatticeElementImpl(locations);
        }
        if(key.equals(IndexLatticeElement.bottom)) return new HeapLocationPowerSetLatticeElementImpl();

        Set<HeapLocationPowerSetLatticeElement> locations = getMap().getDomain().stream().filter(index -> key.containedIn(index) || index.containedIn(key)).map(index -> getMap().getValue(index)).collect(Collectors.toSet());

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
        if (other.equals(top)) {
            return true;
        }

        if(other.isRecursive(otherAnalysis)) {
            return true;
        }

        if (!(other instanceof MapArrayLatticeElement)) {
            return false;
        }
        return !isRecursive(thisAnalysis) && getMap().containedIn(thisAnalysis, ((MapArrayLatticeElement) other).getMap(), thisAnalysis);


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
        return BooleanLatticeElement.top;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.bottom;
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.bottom;
    }

    @Override
    public StringLatticeElement toStringLattice() {
        return StringLatticeElement.generateStringLatticeElement("Array");
    }
    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.bottom;
    }

    @Override
    public boolean isRecursive(HeapMapLatticeElement latticeElement) {
        return  map.getValues().stream().anyMatch(h -> h.isRecursive(latticeElement));
    }


    @Override
    public boolean isRecursive(HeapMapLatticeElement latticeElement, HeapLocation location) {
        return map.getValues().stream().anyMatch(h -> h.isRecursive(latticeElement, location));
    }


}
