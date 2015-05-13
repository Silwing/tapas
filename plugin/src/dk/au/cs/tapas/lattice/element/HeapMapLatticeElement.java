package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;
import java.util.function.BinaryOperator;

/**
 * Created by budde on 5/4/15.
 */
public interface HeapMapLatticeElement extends MapLatticeElement<HeapLocation, ValueLatticeElement>{

    @Override
    HeapMapLatticeElement join( MapLatticeElement<HeapLocation, ValueLatticeElement> other);

    @Override
    HeapMapLatticeElement meet( MapLatticeElement<HeapLocation, ValueLatticeElement> other);

    @Override
    HeapMapLatticeElement addValue(HeapLocation key, Generator<HeapLocation, ValueLatticeElement> generator);

    ValueLatticeElement getValue(HeapLocationPowerSetLatticeElement locations, BinaryOperator<ValueLatticeElement> combiner);

    ValueLatticeElement getValue(Set<HeapLocation> locations, BinaryOperator<ValueLatticeElement> combiner);

    ValueLatticeElement getValue(Set<HeapLocation> locations, ValueLatticeElement initial, BinaryOperator<ValueLatticeElement> combiner);



}
