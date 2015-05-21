package dk.au.cs.tapas.lattice;

import dk.au.cs.tapas.cfg.node.Node;

/**
 * Created by budde on 4/22/15.
 *
 */
public class HeapLocationImpl implements HeapLocation {

    private final Context context;
    private final Node node;
    private final Integer number;


    public HeapLocationImpl(Context context, Node node) {
        this(context, node, 0);
    }

    public HeapLocationImpl(Context context, Node node, Integer number) {
        this.context = context;
        this.node = node;
        this.number = number;
    }

    @Override
    public String toString() {
        return "l_" + hashCode() % 1000;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        HeapLocationImpl that = (HeapLocationImpl) object;

        return context.equals(that.context) && node.equals(that.node) && number.equals(that.number);

    }

    @Override
    public int hashCode() {
        int result = context.hashCode();
        result = 31 * result + node.hashCode();
        result = 31 * result + number.hashCode();
        return result;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Integer getNumber() {
        return number;
    }
}
