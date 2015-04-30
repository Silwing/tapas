package dk.au.cs.tapas.analysis;

/**
 * Created by Silwing on 28-04-2015.
 */
public class PairImpl<L,R> implements Pair<L,R>{

    private final L left;
    private final R right;

    public PairImpl(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() { return left; }
    public R getRight() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        boolean b1 = this.left.equals(pairo.getLeft()), b2 =
                this.right.equals(pairo.getRight());
        return b1 && b2;
    }

    @Override
    public String toString() {
        return "(" + left + ", " + right +')';
    }
}
