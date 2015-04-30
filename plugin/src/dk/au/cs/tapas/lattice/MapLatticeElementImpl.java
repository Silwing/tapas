package dk.au.cs.tapas.lattice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/19/15.
 *
 */
public class MapLatticeElementImpl<K, V extends LatticeElement<V>> extends LatticeElementImpl<MapLatticeElement<K,V>> implements MapLatticeElement<K, V> {

    private final Set<K> domain;
    private final Generator<K, V> generator;
    private final HashMap<K, V> cache = new HashMap<>();


    public MapLatticeElementImpl(Generator<K,V> generator){
        this(new HashSet<>(), generator);
    }

    public MapLatticeElementImpl(Set<K> domain, Generator<K, V> generator) {
        this.domain = domain;
        this.generator = generator;
    }
    @Override
    public Set<K> getDomain() {
        return new HashSet<>(domain);
    }

    @Override
    public V getValue(K key) {
        if(!cache.containsKey(key)) cache.put(key, generator.generate(key));
        return cache.get(key);
    }

    @Override
    public MapLatticeElement<K, V> addValue(final K key, final Generator<K, V> generator) {
        Set<K> domain = new HashSet<>(this.domain);
        domain.add(key);
        return new MapLatticeElementImpl<>(domain, (K k) -> k.equals(key) ? generator.generate(k) : this.generator.generate(k));
    }

    @Override
    public MapLatticeElement<K,V> meet(MapLatticeElement<K,V> other) {
        return new MapLatticeElementImpl<>(jointDomain(this, other), (K k) -> getValue(k).meet(other.getValue(k)));
    }

    @Override
    public MapLatticeElement<K,V> join(MapLatticeElement<K,V> other) {
        return new MapLatticeElementImpl<>(jointDomain(this, other), (K k) -> getValue(k).join(other.getValue(k)));
    }

    @Override
    public boolean containedIn(MapLatticeElement<K,V> other) {
        for (K key : jointDomain(this, other)) {
            if (!getValue(key).containedIn(other.getValue(key))) {
                return false;
            }
        }

        return true;
    }


    private Set<K> jointDomain(MapLatticeElement<K,V> m1, MapLatticeElement<K,V> m2){
        Set<K> newDomain = m1.getDomain();
        newDomain.addAll(m2.getDomain());
        return  newDomain;
    }



}
