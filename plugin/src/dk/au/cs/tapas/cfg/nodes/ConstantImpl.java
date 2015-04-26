package dk.au.cs.tapas.cfg.nodes;

/**
 * Created by budde on 4/26/15.
 *
 */
public class ConstantImpl implements Constant{
    protected Object value;

    public ConstantImpl(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "const[" + value + ']';
    }
}
