package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/28/15.
 */
public interface AnalysisLatticeElement extends MapLatticeElement<Context, StateLatticeElement>{

    @Override
    AnalysisLatticeElement addValue(Context key, Generator<Context, StateLatticeElement> generator);

    @Override
    AnalysisLatticeElement meet(MapLatticeElement<Context, StateLatticeElement> other);

    @Override
    AnalysisLatticeElement join(MapLatticeElement<Context, StateLatticeElement> other);


}
