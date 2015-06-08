package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/22/15.
 *
 */
public class TemporaryVariableNameImpl implements TemporaryVariableName{

    public static int COUNTER = 0;

    private int number;

    public TemporaryVariableNameImpl() {
        number = ++COUNTER;
    }

    @Override
    public String toString() {
        return "t"+number;
    }

}

