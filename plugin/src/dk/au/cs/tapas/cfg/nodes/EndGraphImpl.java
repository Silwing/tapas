package dk.au.cs.tapas.cfg.nodes;

import dk.au.cs.tapas.cfg.nodes.Graph;

/**
 * Created by budde on 4/22/15.
 */
public class EndGraphImpl extends NodeGraphImpl{

    public EndGraphImpl() {
        super(new EndNodeImpl());
    }

    @Override
    public Node getExitNode() {
        return null;
    }


}
