package dk.au.cs.tapas.cfg;

import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableNameImpl;

/**
 * Created by budde on 4/27/15.
 */
public class TemporaryHeapVariableCallArgumentImpl extends CallArgumentImpl<TemporaryHeapVariableName> implements TemporaryHeapVariableCallArgument {

    public TemporaryHeapVariableCallArgumentImpl(TemporaryHeapVariableName argument) {
        super(argument);
    }

    public TemporaryHeapVariableCallArgumentImpl() {
        super(new TemporaryHeapVariableNameImpl());
    }
}
