package dk.au.cs.tapas.cfg.nodes;

import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

/**
 * Created by budde on 4/27/15.
 */
public class TemporaryVariableCallArgumentImpl extends CallArgumentImpl<TemporaryVariableName> implements TemporaryVariableCallArgument{

    public TemporaryVariableCallArgumentImpl(){
        this(new TemporaryVariableNameImpl());
    }

    public TemporaryVariableCallArgumentImpl(TemporaryVariableName argument) {
        super(argument);
    }
}
