package dk.au.cs.tapas.cfg.nodes;

import org.jetbrains.annotations.Nullable;

/**
 * Created by budde on 4/22/15.
 */
public interface Graph {

    @Nullable
    Node getExitNode();

    @Nullable
    Node getEntryNode();

}
