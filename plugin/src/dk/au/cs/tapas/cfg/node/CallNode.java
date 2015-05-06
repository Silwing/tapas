package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.cfg.CallArgument;
import dk.au.cs.tapas.cfg.graph.FunctionGraph;

/**
 * Created by budde on 4/26/15.
 *
 */
public interface CallNode extends Node{

    String getFunctionName();

    FunctionGraph getFunctionGraph();

    CallArgument[] getCallArguments();

    ResultNode getResultNode();

}
