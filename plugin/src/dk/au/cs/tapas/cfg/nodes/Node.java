package dk.au.cs.tapas.cfg.nodes;

import java.util.Set;

/**
 * Created by budde on 4/22/15.
 */
public interface Node {

    public Node[] getSuccessors();


    Set<String> toDot();

    Set<String> toDot(Set<Node> visited);

}
