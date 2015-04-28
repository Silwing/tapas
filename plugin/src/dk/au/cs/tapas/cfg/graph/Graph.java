package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.cfg.node.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by budde on 4/22/15.
 *
 */
public interface Graph {

    @NotNull
    Node getExitNode();

    @NotNull
    Node getEntryNode();

}
