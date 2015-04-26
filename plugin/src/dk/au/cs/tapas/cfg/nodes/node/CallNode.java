package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.cfg.nodes.CallArgument;

/**
 * Created by budde on 4/26/15.
 *
 */
public interface CallNode extends Node{

    String getFunctionName();

    CallArgument[] getCallArguments();

    ResultNode getResultNode();

}
