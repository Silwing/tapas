package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.EndNodeImpl;

/**
 * Created by budde on 4/22/15.
 *
 */
public class EndGraphImpl extends NodeGraphImpl {

    public EndGraphImpl() {
        super(new EndNodeImpl());
    }

    @Override
    public Node getExitNode() {
        return null;
    }


}
