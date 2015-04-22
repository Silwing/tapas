package dk.au.cs.tapas.cfg.nodes;

/**
 * Created by budde on 4/22/15.
 */
public class NodeGraphImpl implements Graph{

    final private Node node;

    public NodeGraphImpl(Node node){
        this.node = node;
    }


    @Override
    public Node getExitNode() {
        return node;
    }

    @Override
    public Node getEntryNode() {
        return node;
    }




}
