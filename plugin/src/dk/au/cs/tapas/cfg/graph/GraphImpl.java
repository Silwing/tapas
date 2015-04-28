package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.cfg.node.Node;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by budde on 4/28/15.
 */
public abstract class GraphImpl implements Graph {


    @Override
    public Set<Node> getFlow(Node n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Node> getNodes() {
        throw new UnsupportedOperationException();
    }
}
