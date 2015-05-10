package dk.au.cs.tapas.lattice.element;

import java.util.Set;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface MapLatticeElement<K,V extends LatticeElement<V>> extends LatticeElement<MapLatticeElement<K,V>>{

    Set<K> getDomain();

    V getValue(K key);

    V[] getValues();

    MapLatticeElement<K,V> addValue(K key, Generator<K,V> generator);

    interface Generator<K,V>{

        V generate(K key);

    }

}
