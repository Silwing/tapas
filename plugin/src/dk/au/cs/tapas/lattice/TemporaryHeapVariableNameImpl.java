package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/22/15.
 *
 */
public class TemporaryHeapVariableNameImpl implements TemporaryHeapVariableName{

    static private int COUNTER = 0;

    private int number;

    public TemporaryHeapVariableNameImpl() {
        number = ++COUNTER;
    }

    @Override
    public String toString() {
        return "t"+number;
    }

}

