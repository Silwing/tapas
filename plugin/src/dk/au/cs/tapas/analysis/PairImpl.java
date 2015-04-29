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
        if (!(o instanceof PairImpl)) return false;
        PairImpl pairo = (PairImpl) o;
        return this.left.equals(pairo.getLeft()) &&
                this.right.equals(pairo.getRight());
    }

}
