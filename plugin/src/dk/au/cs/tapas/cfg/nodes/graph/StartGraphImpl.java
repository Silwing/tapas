package dk.au.cs.tapas.cfg.nodes.graph;

import dk.au.cs.tapas.cfg.nodes.graph.Graph;
import dk.au.cs.tapas.cfg.nodes.graph.NodeGraphImpl;
import dk.au.cs.tapas.cfg.nodes.node.Node;
import dk.au.cs.tapas.cfg.nodes.node.StartNodeImpl;

/**
 * Created by budde on 4/22/15.
 *
 */
public class StartGraphImpl extends NodeGraphImpl {


    public StartGraphImpl(Graph target) {
        super(new StartNodeImpl(target.getEntryNode()));
    }

    @Override
    public Node getEntryNode() {
        return null;
    }
}
