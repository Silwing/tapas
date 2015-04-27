package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.cfg.CallArgument;

/**
 * Created by budde on 4/26/15.
 *
 */
public interface CallNode extends Node{

    String getFunctionName();

    CallArgument[] getCallArguments();

    ResultNode getResultNode();

}
