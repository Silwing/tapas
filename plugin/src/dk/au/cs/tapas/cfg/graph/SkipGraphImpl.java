package dk.au.cs.tapas.cfg.graph;


import dk.au.cs.tapas.cfg.node.SkipNodeImpl;

/**
 * Created by budde on 4/22/15.
 */
public class SkipGraphImpl extends NodeGraphImpl {


    public SkipGraphImpl(Graph targetGraph) {
        super(new SkipNodeImpl(targetGraph.getEntryNode()));
    }


}
