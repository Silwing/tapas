package dk.au.cs.tapas.lattice;

import dk.au.cs.tapas.cfg.node.Node;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface HeapLocation {

    Node getNode();

    Context getContext();

    Integer getNumber();

}
