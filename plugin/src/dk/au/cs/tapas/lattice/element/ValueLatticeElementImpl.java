package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

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

    public ValueLatticeElementImpl(NumberLatticeElement number, BooleanLatticeElement bool) {
        this.array = ArrayLatticeElement.bottom;
        this.string = StringLatticeElement.bottom;
        this.number = number;
        this.bool = bool;
        this.nullLatticeElement = NullLatticeElement.bottom;
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
        return
                getArray().toStringLattice()
                    .join(getBoolean().toStringLattice())
                    .join(getString())
                    .join(getNull().toStringLattice())
                    .join(getNumber().toStringLattice());
    }

    @Override
    public BooleanLatticeElement equalOperation(ValueLatticeElement rightValue) {
        return BooleanLatticeElement.top;    }

    @Override
    public BooleanLatticeElement identical(ValueLatticeElement rightValue) {
        return BooleanLatticeElement.top;    }

    @Override
    public BooleanLatticeElement notEqualOperation(ValueLatticeElement rightValue) {
        return BooleanLatticeElement.top;    }

    @Override
    public BooleanLatticeElement notIdentical(ValueLatticeElement rightValue) {
        return BooleanLatticeElement.top;    }

    @Override
    public ValueLatticeElement setArray(ArrayLatticeElement array) {
        return new ValueLatticeElementImpl(array, getString(), getNumber(), getBoolean(), getNull());
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
    public IndexLatticeElement toArrayIndex() {
        return getArray().toArrayIndex()
                .join(getBoolean().toArrayIndex())
                .join(getString().toArrayIndex())
                .join(getNumber().toArrayIndex())
                .join(getNull().toArrayIndex());
    }


    @Override
    public NumberLatticeElement toNumber() {
        return getArray().toNumber()
                .join(getBoolean().toNumber())
                .join(getString().toNumber())
                .join(getNull().toNumber())
                .join(getNumber());
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return getArray().toInteger().join(getString().toInteger()).join(getNull().toInteger()).join(getNumber().toInteger()).join(getBoolean().toInteger());
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
