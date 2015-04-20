package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public class AnalysisLatticeElementImpl extends MapLatticeElementImpl<Context, StateLatticeElement>{

    public AnalysisLatticeElementImpl() {
        super((Context context) -> new StateLatticeElementImpl());
    }
}
