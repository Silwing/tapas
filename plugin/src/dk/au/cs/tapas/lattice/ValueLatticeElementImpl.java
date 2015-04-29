package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class ValueLatticeElementImpl implements ValueLatticeElement{

    public final ArrayLatticeElement array;
    public final StringLatticeElement string;
    public final NumberLatticeElement number;
    public final BooleanLatticeElement bool;
    public final NullLatticeElement nullLatticeElement;

    public ValueLatticeElementImpl() {
        this(
                ArrayLatticeElement.bottom,
                StringLatticeElement.bottom,
                NumberLatticeElement.bottom,
                BooleanLatticeElement.bottom,
                NullLatticeElement.bottom);
    }

    public ValueLatticeElementImpl(
            ArrayLatticeElement array,
            StringLatticeElement string,
            NumberLatticeElement number,
            BooleanLatticeElement bool,
            NullLatticeElement nullLatticeElement) {
        this.array = array;
        this.string = string;
        this.number = number;
        this.bool = bool;
        this.nullLatticeElement = nullLatticeElement;
    }

    public ValueLatticeElementImpl(ArrayLatticeElement array) {
        this(
                array,
                StringLatticeElement.bottom,
                NumberLatticeElement.bottom,
                BooleanLatticeElement.bottom,
                NullLatticeElement.bottom
        );
    }

    public ValueLatticeElementImpl(StringLatticeElement string) {
        this(
                ArrayLatticeElement.bottom,
                string,
                NumberLatticeElement.bottom,
                BooleanLatticeElement.bottom,
                NullLatticeElement.bottom
        );
    }

    public ValueLatticeElementImpl(NumberLatticeElement number) {
        this(
                ArrayLatticeElement.bottom,
                StringLatticeElement.bottom,
                number,
                BooleanLatticeElement.bottom,
                NullLatticeElement.bottom
        );
    }

    public ValueLatticeElementImpl(BooleanLatticeElement bool) {
        this(
                ArrayLatticeElement.bottom,
                StringLatticeElement.bottom,
                NumberLatticeElement.bottom,
                bool,
                NullLatticeElement.bottom
        );
    }

    public ValueLatticeElementImpl(NullLatticeElement nullElm) {
        this(
                ArrayLatticeElement.bottom,
                StringLatticeElement.bottom,
                NumberLatticeElement.bottom,
                BooleanLatticeElement.bottom,
                nullElm
        );
    }

    @Override
    public ArrayLatticeElement getArray() {
        return array;
    }

    @Override
    public StringLatticeElement getString() {
        return string;
    }

    @Override
    public NumberLatticeElement getNumber() {
        return number;
    }

    @Override
    public BooleanLatticeElement getBoolean() {
        return bool;
    }

    @Override
    public NullLatticeElement getNull() {
        return nullLatticeElement;
    }

    @Override
    public ValueLatticeElement meet(ValueLatticeElement other) {
        return new ValueLatticeElementImpl(
                getArray().meet(other.getArray()),
                getString().meet(other.getString()),
                getNumber().meet(other.getNumber()),
                getBoolean().meet(other.getBoolean()),
                getNull().meet(other.getNull()));
    }

    @Override
    public ValueLatticeElement join(ValueLatticeElement other) {
        return new ValueLatticeElementImpl(
                getArray().join(other.getArray()),
                getString().join(other.getString()),
                getNumber().join(other.getNumber()),
                getBoolean().join(other.getBoolean()),
                getNull().join(other.getNull()));
    }

    @Override
    public boolean containedIn(ValueLatticeElement other) {
        return
                getArray().containedIn(other.getArray()) &&
                getString().containedIn(other.getString()) &&
                getNumber().containedIn(other.getNumber()) &&
                getBoolean().containedIn(other.getBoolean()) &&
                getNull().containedIn(other.getNull());
    }

    @Override
    public boolean equals(ValueLatticeElement other) {
        return
                getArray().equals(other.getArray()) &&
                getString().equals(other.getString()) &&
                getNumber().equals(other.getNumber()) &&
                getNull().equals(other.getNull()) &&
                getBoolean().equals(other.getBoolean());
    }
}
