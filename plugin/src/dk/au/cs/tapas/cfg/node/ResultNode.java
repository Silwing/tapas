package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.cfg.CallArgument;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 */
public interface ResultNode extends Node{

    CallArgument getTargetName();

    CallNode getCallNode();

}
