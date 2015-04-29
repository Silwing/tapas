package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.analysis.ContextNodePair;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by budde on 4/22/15.
 *
 */
public interface Graph {

    @NotNull
    Node getExitNode();

    @NotNull
    Node getEntryNode();


    Set<ContextNodePair> getFlow(ContextNodePair contextNodePair);

    Set<Node> getNodes();


}
