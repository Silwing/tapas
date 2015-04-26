package dk.au.cs.tapas.cfg.nodes.graph;


import dk.au.cs.tapas.cfg.nodes.graph.Graph;
import dk.au.cs.tapas.cfg.nodes.graph.NodeGraphImpl;
import dk.au.cs.tapas.cfg.nodes.node.SkipNodeImpl;

/**
 * Created by budde on 4/22/15.
 */
public class SkipGraphImpl extends NodeGraphImpl {


    public SkipGraphImpl(Graph targetGraph) {
        super(new SkipNodeImpl(targetGraph.getEntryNode()));
    }


}
