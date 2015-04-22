package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/22/15.
 */
public class HeapLocationImpl implements HeapLocation {

    static private int COUNTER = 0;

    private int number;

    public HeapLocationImpl() {
        number = ++COUNTER;
    }

    @Override
    public String toString() {
        return "l"+number;
    }
}
