package dk.au.cs.tapas.cfg.nodes;


/**
 * Created by budde on 4/22/15.
 */
public class SkipGraphImpl extends NodeGraphImpl {


    public SkipGraphImpl(Graph targetGraph) {
        super(new SkipNodeImpl(targetGraph.getEntryNode()));
    }


}
