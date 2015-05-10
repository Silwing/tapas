package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.LatticePrinter;

import java.util.Set;
import java.util.function.BinaryOperator;

/**
 * Created by budde on 4/20/15.
 */
public class HeapMapLatticeElementImpl implements HeapMapLatticeElement {
    private final MapLatticeElement<HeapLocation, ValueLatticeElement> mapLatticeElement;


    public HeapMapLatticeElementImpl() {
        this(new MapLatticeElementImpl<>(location -> new ValueLatticeElementImpl()));
    }

    public HeapMapLatticeElementImpl(MapLatticeElement<HeapLocation, ValueLatticeElement> mapLatticeElement) {
        this.mapLatticeElement = mapLatticeElement;

    }

    @Override
    public Set<HeapLocation> getDomain() {
        return mapLatticeElement.getDomain();
    }

    @Override
    public ValueLatticeElement getValue(HeapLocation key) {
        return mapLatticeElement.getValue(key);
    }

    @Override
    public ValueLatticeElement[] getValues() {
        return mapLatticeElement.getValues();
    }

    @Override
    public HeapMapLatticeElement addValue(HeapLocation key, Generator<HeapLocation, ValueLatticeElement> generator) {
        return new HeapMapLatticeElementImpl(mapLatticeElement.addValue(key, generator));
    }

    @Override
    public ValueLatticeElement getValue(Set<HeapLocation> locations, BinaryOperator<ValueLatticeElement> combiner) {
        return getValue(locations, new ValueLatticeElementImpl(), combiner);
    }

    @Override
    public ValueLatticeElement getValue(Set<HeapLocation> locations, ValueLatticeElement initial, BinaryOperator<ValueLatticeElement> combiner) {
        return locations.stream().map(this::getValue).reduce(initial, combiner);
    }

    @Override
    public HeapMapLatticeElement meet(MapLatticeElement<HeapLocation, ValueLatticeElement> other) {
        return new HeapMapLatticeElementImpl(mapLatticeElement.meet(other));
    }



    @Override
    public HeapMapLatticeElement join(MapLatticeElement<HeapLocation, ValueLatticeElement> other) {
        return new HeapMapLatticeElementImpl(mapLatticeElement.join(other));
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, MapLatticeElement<HeapLocation, ValueLatticeElement> other, HeapMapLatticeElement otherAnalysis) {
        return mapLatticeElement.containedIn(thisAnalysis, other, otherAnalysis);
    }

    @Override
    public void print(LatticePrinter printer) {
        mapLatticeElement.print(printer);
    }
}
