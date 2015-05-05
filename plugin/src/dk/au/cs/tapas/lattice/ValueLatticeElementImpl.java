package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 */
public class ValueLatticeElementImpl implements ValueLatticeElement {

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
    public StringLatticeElement toStringLattice() {
        return null;
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return
                getArray().toBoolean()
                        .join(getBoolean())
                        .join(getString().toBoolean())
                        .join(getNumber().toBoolean())
                        .join(getNull().toBoolean());

    }

    @Override
    public NumberLatticeElement toNumber() {
        return null;
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
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, ValueLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return
                getArray().containedIn(thisAnalysis, other.getArray(), otherAnalysis) &&
                        getString().containedIn(thisAnalysis, other.getString(), otherAnalysis) &&
                        getNumber().containedIn(thisAnalysis, other.getNumber(), otherAnalysis) &&
                        getBoolean().containedIn(thisAnalysis, other.getBoolean(), otherAnalysis) &&
                        getNull().containedIn(thisAnalysis, other.getNull(), otherAnalysis);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("{");
        printer.startSection();
        printer.linebreak();
        printer.print("array -> ");
        array.print(printer);
        printer.print(",");
        printer.linebreak();
        printer.print("string -> ");
        string.print(printer);
        printer.print(",");
        printer.linebreak();
        printer.print("number -> ");
        number.print(printer);
        printer.print(",");
        printer.linebreak();
        printer.print("boolean -> ");
        bool.print(printer);
        printer.print(",");
        printer.linebreak();
        printer.print("null -> ");
        nullLatticeElement.print(printer);
        printer.endSection();
        printer.linebreak();
        printer.print("}");
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof ValueLatticeElement &&
                getArray().equals(((ValueLatticeElement) other).getArray()) &&
                getString().equals(((ValueLatticeElement) other).getString()) &&
                getNumber().equals(((ValueLatticeElement) other).getNumber()) &&
                getNull().equals(((ValueLatticeElement) other).getNull()) &&
                getBoolean().equals(((ValueLatticeElement) other).getBoolean()));
    }
}
