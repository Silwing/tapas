package dk.au.cs.tapas.cfg.graph;


import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.SkipNodeImpl;

import java.util.Set;

/**
 * Created by budde on 4/22/15.
 */
public class SkipGraphImpl extends NodeGraphImpl {


    public SkipGraphImpl(Graph targetGraph) {
        super(new SkipNodeImpl(targetGraph.getEntryNode()));
    }


}
