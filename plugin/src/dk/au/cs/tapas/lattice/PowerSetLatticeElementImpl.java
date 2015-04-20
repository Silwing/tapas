package dk.au.cs.tapas.lattice;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/19/15.
 *
 */
public class PowerSetLatticeElementImpl<T> extends LatticeElementImpl<PowerSetLatticeElement<T>> implements PowerSetLatticeElement<T>{

    private final Set<T> values;

    public PowerSetLatticeElementImpl(){
        this(new HashSet<>());
    }

    public PowerSetLatticeElementImpl(Set<T> values) {
        this.values = values;
    }

    @Override
    public Set<T> getValues() {
        return new HashSet<>(values);
    }

    @Override
    public PowerSetLatticeElement<T> addValue(T value) {
        if(values.contains(value)){
            return this;
        }
        Set<T> newValues  = getValues();
        newValues.add(value);
        return new PowerSetLatticeElementImpl<>(newValues);

    }

    @Override
    public PowerSetLatticeElement<T> meet(PowerSetLatticeElement<T> other) {
        Set<T> newValues = other.getValues();
        newValues.stream().filter(key -> !values.contains(key)).forEach(newValues::remove);
        return new PowerSetLatticeElementImpl<>(newValues);
    }

    @Override
    public PowerSetLatticeElement<T> join(PowerSetLatticeElement<T> other) {
        Set<T> newValues = other.getValues();
        newValues.addAll(values);
        return new PowerSetLatticeElementImpl<>(newValues);
    }

    @Override
    public boolean containedIn(PowerSetLatticeElement<T> other) {
        return other.getValues().containsAll(values);
    }


}