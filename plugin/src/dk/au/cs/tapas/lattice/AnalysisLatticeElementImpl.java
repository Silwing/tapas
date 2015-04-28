package dk.au.cs.tapas.lattice;

import java.util.Set;

/**
 * Created by budde on 4/19/15.
 *
 */
public class AnalysisLatticeElementImpl implements AnalysisLatticeElement {

    private final MapLatticeElement<Context, StateLatticeElement> mapLatticeElement;

    public AnalysisLatticeElementImpl() {
        this(new MapLatticeElementImpl<>(context -> new StateLatticeElementImpl()));
    }

    private AnalysisLatticeElementImpl(MapLatticeElement<Context, StateLatticeElement> mapLatticeElement) {
        this.mapLatticeElement = mapLatticeElement;
    }

    @Override
    public Set<Context> getDomain() {
        return mapLatticeElement.getDomain();
    }

    @Override
    public StateLatticeElement getValue(Context key) {
        return mapLatticeElement.getValue(key);
    }

    @Override
    public AnalysisLatticeElement addValue(Context key, Generator<Context, StateLatticeElement> generator) {
        return new AnalysisLatticeElementImpl(mapLatticeElement.addValue(key, generator));
    }

    @Override
    public AnalysisLatticeElement meet(MapLatticeElement<Context, StateLatticeElement> other) {
        return new AnalysisLatticeElementImpl(mapLatticeElement.meet(other));
    }

    @Override
    public AnalysisLatticeElement join(MapLatticeElement<Context, StateLatticeElement> other) {
        return new AnalysisLatticeElementImpl(mapLatticeElement.join(other));
    }

    @Override
    public boolean containedIn(MapLatticeElement<Context, StateLatticeElement> other) {
        return mapLatticeElement.containedIn(other);
    }

    @Override
    public boolean equals(MapLatticeElement<Context, StateLatticeElement> other) {
        return mapLatticeElement.equals(other);
    }
}
