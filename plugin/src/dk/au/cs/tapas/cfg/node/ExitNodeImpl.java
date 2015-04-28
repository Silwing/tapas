package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.cfg.CallArgument;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Arrays;

/**
 * Created by budde on 4/27/15.
 */
public class ExitNodeImpl extends NodeImpl implements ExitNode{
    private CallArgument[] targetName;

    public ExitNodeImpl(){
        this(new CallArgument[0]);
    }

    public ExitNodeImpl(CallArgument[] targetName) {
        super(new Node[0]);
        this.targetName = targetName;
    }


    @Override
    public CallArgument[] getCallArguments() {
        return targetName;
    }

    public void addCallArgument(CallArgument argument){
        this.targetName = new CallArgument[targetName.length+1];
        this.targetName[targetName.length-1] = argument;

    }


    @Override
    public String toString() {
        return "exit(" + Arrays.toString(targetName) + ')';
    }
}
