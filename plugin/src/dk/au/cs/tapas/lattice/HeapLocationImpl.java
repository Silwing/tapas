package dk.au.cs.tapas.lattice;

import dk.au.cs.tapas.cfg.node.Node;

/**
 * Created by budde on 4/22/15.
 *
 */
public class HeapLocationImpl implements HeapLocation {

    private final Context context;
    private final Node node;


    public HeapLocationImpl(Context context, Node node) {
        this.context = context;
        this.node = node;

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

        return context.equals(that.context) && node.equals(that.node);

    }

    @Override
    public int hashCode() {
        int result = context.hashCode();
        result = 31 * result + node.hashCode();
        return result;
    }
}
