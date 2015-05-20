package dk.au.cs.tapas.lattice.element;

/**
 * Created by budde on 4/20/15.
 *
 */
public abstract class MiddleLatticeElementImpl<T extends MiddleCandidateLatticeElement> implements MiddleCandidateLatticeElement<T> {
    private final ElementFinder<T> join;
    private final ElementFinder<T> meet;

    public MiddleLatticeElementImpl(ElementFinder<T> meet, ElementFinder<T> join) {
        this.join = join;
        this.meet = meet;
    }

    @Override
    public boolean containedIn(T other) {
        return containedIn(other);
    }

    @Override
    public T meet(T other) {
        if (containedIn(other)) {
            return (T) this;
        }

        if(other.containedIn(this)){
            return other;
        }

        return meet.find(other);
    }

    @Override
    public T join(T other) {
        if (other.containedIn(this)) {
            return (T) this;
        }
        if(containedIn((T) other )){
            return other;
        }
        return join.find(other);
    }

    public interface ElementFinder<T>{

        T find(T t1);

    }

}
