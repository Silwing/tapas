package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.StartNodeImpl;

/**
 * Created by budde on 4/22/15.
 *
 */
public class StartGraphImpl extends NodeGraphImpl {


    public StartGraphImpl(Graph target) {
        super(new StartNodeImpl(target.getEntryNode()));
    }

}
