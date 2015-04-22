package dk.au.cs.tapas.cfg.nodes;

/**
 * Created by budde on 4/22/15.
 */
public class StartGraphImpl extends NodeGraphImpl{


    public StartGraphImpl(Graph target) {
        super(new StartNodeImpl(target.getEntryNode()));
    }

    @Override
    public Node getEntryNode() {
        return null;
    }
}
