package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.cfg.CallArgument;

/**
 * Created by budde on 4/27/15.
 */
public class EntryNodeImpl extends NodeImpl implements EntryNode {


    private CallArgument[] arguments;

    public EntryNodeImpl(Node successor, CallArgument[] arguments) {
        super(successor);
        this.arguments = arguments;
    }

    @Override
    public CallArgument[] getCallArguments() {
        return arguments;
    }
}
