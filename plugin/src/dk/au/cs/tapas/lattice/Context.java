package dk.au.cs.tapas.lattice;

import dk.au.cs.tapas.cfg.node.CallNode;

import java.util.List;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface Context {

    List<CallNode> getNodes();

    Context addNode(CallNode node);

    Context popNode();

    boolean equals(Context other);

}
