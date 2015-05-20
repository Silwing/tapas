package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.Coercible;
import dk.au.cs.tapas.lattice.LatticePrinter;

import java.util.function.BiFunction;

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

    private static boolean haveMoreThanOnePossibleValue(ValueLatticeElement value) {
        int possibles = 0;
        if(!value.getArray().equals(ArrayLatticeElement.bottom))
            possibles++;
        if(!value.getNumber().equals(NumberLatticeElement.bottom))
            possibles++;
        if(!value.getBoolean().equals(BooleanLatticeElement.bottom))
            possibles++;
        if(!value.getNull().equals(NullLatticeElement.bottom))
            possibles++;
        if(!value.getString().equals(StringLatticeElement.bottom))
            possibles++;

        return possibles > 1;
    }

    private static Coercible getCoercibleSingleValue(ValueLatticeElement value) {
        if(!value.getArray().equals(ArrayLatticeElement.bottom))
            return value.getArray();
        if(!value.getNumber().equals(NumberLatticeElement.bottom))
            return value.getNumber();
        if(!value.getBoolean().equals(BooleanLatticeElement.bottom))
            return value.getBoolean();
        if(!value.getNull().equals(NullLatticeElement.bottom))
            return value.getNull();
        if(!value.getString().equals(StringLatticeElement.bottom))
            return value.getString();

        throw new UnsupportedOperationException("Does not support getCoercibleSingleValue on bottom value"); // Should never happen
    }

    @Override
    public BooleanLatticeElement equalOperation(ValueLatticeElement rightValue) {
        if(haveMoreThanOnePossibleValue(this) || haveMoreThanOnePossibleValue(rightValue))
            return BooleanLatticeElement.top;
        if(this.equals(bottom) || rightValue.equals(bottom))
            return BooleanLatticeElement.bottom;

        if((!getString().equals(StringLatticeElement.bottom) || !getNull().equals(NullLatticeElement.bottom)) && !rightValue.getString().equals(StringLatticeElement.bottom)) {
            if(!getString().equals(StringLatticeElement.bottom))
                return getString().equalOperation(rightValue.getString());
        }

        if(!getNull().equals(NullLatticeElement.bottom)) {
            return getNull().toBoolean().equalOperation(getCoercibleSingleValue(rightValue).toBoolean());
        }
        if(!getBoolean().equals(BooleanLatticeElement.bottom)) {
            return getBoolean().equalOperation(getCoercibleSingleValue(rightValue).toBoolean());
        }

        if(!getString().equals(StringLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getString().toNumber().equalOperation(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getString().toNumber().equalOperation(rightValue.getString().toNumber());
        }

        if(!getNumber().equals(NumberLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getNumber().equalOperation(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getNumber().equalOperation(rightValue.getString().toNumber());
        }

        if(!getArray().equals(ArrayLatticeElement.bottom)) {
            if(!rightValue.getArray().equals(ArrayLatticeElement.bottom))
                return BooleanLatticeElement.top; // We know nothing about array comparison
            else
                return BooleanLatticeElement.boolFalse; // Arrays are always greater than anything else, so false for equal
        }

        throw new UnsupportedOperationException("Unsupported equal operation");
    }

    @Override
    public BooleanLatticeElement identical(ValueLatticeElement rightValue) {
        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement notEqualOperation(ValueLatticeElement rightValue) {
        if(haveMoreThanOnePossibleValue(this) || haveMoreThanOnePossibleValue(rightValue))
            return BooleanLatticeElement.top;
        if(this.equals(bottom) || rightValue.equals(bottom))
            return BooleanLatticeElement.bottom;

        if((!getString().equals(StringLatticeElement.bottom) || !getNull().equals(NullLatticeElement.bottom)) && !rightValue.getString().equals(StringLatticeElement.bottom)) {
            if(!getString().equals(StringLatticeElement.bottom))
                return getString().notEqual(rightValue.getString());
        }

        if(!getNull().equals(NullLatticeElement.bottom)) {
            return getNull().toBoolean().notEqual(getCoercibleSingleValue(rightValue).toBoolean());
        }
        if(!getBoolean().equals(BooleanLatticeElement.bottom)) {
            return getBoolean().notEqual(getCoercibleSingleValue(rightValue).toBoolean());
        }

        if(!getString().equals(StringLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getString().toNumber().notEqual(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getString().toNumber().notEqual(rightValue.getString().toNumber());
        }

        if(!getNumber().equals(NumberLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getNumber().notEqual(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getNumber().notEqual(rightValue.getString().toNumber());
        }

        if(!getArray().equals(ArrayLatticeElement.bottom)) {
            if(!rightValue.getArray().equals(ArrayLatticeElement.bottom))
                return BooleanLatticeElement.top; // We know nothing about array comparison
            else
                return BooleanLatticeElement.boolFalse; // Arrays are always greater than anything else, so false for equal
        }

        throw new UnsupportedOperationException("Unsupported equal operation");
    }

    @Override
    public BooleanLatticeElement notIdentical(ValueLatticeElement rightValue) {
        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(ValueLatticeElement rightValue) {
        if(haveMoreThanOnePossibleValue(this) || haveMoreThanOnePossibleValue(rightValue))
            return BooleanLatticeElement.top;
        if(this.equals(bottom) || rightValue.equals(bottom))
            return BooleanLatticeElement.bottom;

        if((!getString().equals(StringLatticeElement.bottom) || !getNull().equals(NullLatticeElement.bottom)) && !rightValue.getString().equals(StringLatticeElement.bottom)) {
            if(!getString().equals(StringLatticeElement.bottom))
                return getString().lessThan(rightValue.getString());
        }

        if(!getNull().equals(NullLatticeElement.bottom)) {
            return getNull().toBoolean().lessThan(getCoercibleSingleValue(rightValue).toBoolean());
        }
        if(!getBoolean().equals(BooleanLatticeElement.bottom)) {
            return getBoolean().lessThan(getCoercibleSingleValue(rightValue).toBoolean());
        }

        if(!getString().equals(StringLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getString().toNumber().lessThan(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getString().toNumber().lessThan(rightValue.getString().toNumber());
        }

        if(!getNumber().equals(NumberLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getNumber().lessThan(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getNumber().lessThan(rightValue.getString().toNumber());
        }

        if(!getArray().equals(ArrayLatticeElement.bottom)) {
            if(!rightValue.getArray().equals(ArrayLatticeElement.bottom))
                return BooleanLatticeElement.top; // We know nothing about array comparison
            else
                return BooleanLatticeElement.boolFalse; // Arrays are always greater than anything else, so false for equal
        }

        throw new UnsupportedOperationException("Unsupported equal operation");
    }

    private BooleanLatticeElement relativeOp(ValueLatticeElement other, BiFunction<NumberLatticeElement, NumberLatticeElement, BooleanLatticeElement> function){
        if(getBoolean().equals(other.getBoolean()) && getBoolean().equals(BooleanLatticeElement.boolFalse) &&
                getString().equals(other.getString()) && getString().equals(StringLatticeElement.bottom) &&
                getArray().equals(other.getArray()) && getArray().equals(ArrayLatticeElement.bottom) &&
                getNull().equals(other.getNull()) && getNull().equals(NullLatticeElement.bottom)){
            return BooleanLatticeElement.top;

        }

        return function.apply(getNumber(), other.getNumber());
    }

    @Override
    public BooleanLatticeElement greaterThan(ValueLatticeElement rightValue) {
        if(haveMoreThanOnePossibleValue(this) || haveMoreThanOnePossibleValue(rightValue))
            return BooleanLatticeElement.top;
        if(this.equals(bottom) || rightValue.equals(bottom))
            return BooleanLatticeElement.bottom;

        if((!getString().equals(StringLatticeElement.bottom) || !getNull().equals(NullLatticeElement.bottom)) && !rightValue.getString().equals(StringLatticeElement.bottom)) {
            if(!getString().equals(StringLatticeElement.bottom))
                return getString().greaterThan(rightValue.getString());
        }

        if(!getNull().equals(NullLatticeElement.bottom)) {
            return getNull().toBoolean().greaterThan(getCoercibleSingleValue(rightValue).toBoolean());
        }
        if(!getBoolean().equals(BooleanLatticeElement.bottom)) {
            return getBoolean().greaterThan(getCoercibleSingleValue(rightValue).toBoolean());
        }

        if(!getString().equals(StringLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getString().toNumber().greaterThan(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getString().toNumber().greaterThan(rightValue.getString().toNumber());
        }

        if(!getNumber().equals(NumberLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getNumber().greaterThan(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getNumber().greaterThan(rightValue.getString().toNumber());
        }

        if(!getArray().equals(ArrayLatticeElement.bottom)) {
            if(!rightValue.getArray().equals(ArrayLatticeElement.bottom))
                return BooleanLatticeElement.top; // We know nothing about array comparison
            else
                return BooleanLatticeElement.boolFalse; // Arrays are always greater than anything else, so false for equal
        }

        throw new UnsupportedOperationException("Unsupported equal operation");
    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(ValueLatticeElement rightValue) {
        if(haveMoreThanOnePossibleValue(this) || haveMoreThanOnePossibleValue(rightValue))
            return BooleanLatticeElement.top;
        if(this.equals(bottom) || rightValue.equals(bottom))
            return BooleanLatticeElement.bottom;

        if((!getString().equals(StringLatticeElement.bottom) || !getNull().equals(NullLatticeElement.bottom)) && !rightValue.getString().equals(StringLatticeElement.bottom)) {
            if(!getString().equals(StringLatticeElement.bottom))
                return getString().greaterThanEqual(rightValue.getString());
        }

        if(!getNull().equals(NullLatticeElement.bottom)) {
            return getNull().toBoolean().greaterThanEqual(getCoercibleSingleValue(rightValue).toBoolean());
        }
        if(!getBoolean().equals(BooleanLatticeElement.bottom)) {
            return getBoolean().greaterThanEqual(getCoercibleSingleValue(rightValue).toBoolean());
        }

        if(!getString().equals(StringLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getString().toNumber().greaterThanOrEqual(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getString().toNumber().greaterThanOrEqual(rightValue.getString().toNumber());
        }

        if(!getNumber().equals(NumberLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getNumber().greaterThan(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getNumber().greaterThan(rightValue.getString().toNumber());
        }

        if(!getArray().equals(ArrayLatticeElement.bottom)) {
            if(!rightValue.getArray().equals(ArrayLatticeElement.bottom))
                return BooleanLatticeElement.top; // We know nothing about array comparison
            else
                return BooleanLatticeElement.boolFalse; // Arrays are always greater than anything else, so false for equal
        }

        throw new UnsupportedOperationException("Unsupported equal operation");
    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(ValueLatticeElement rightValue) {
        if(haveMoreThanOnePossibleValue(this) || haveMoreThanOnePossibleValue(rightValue))
            return BooleanLatticeElement.top;
        if(this.equals(bottom) || rightValue.equals(bottom))
            return BooleanLatticeElement.bottom;

        if((!getString().equals(StringLatticeElement.bottom) || !getNull().equals(NullLatticeElement.bottom)) && !rightValue.getString().equals(StringLatticeElement.bottom)) {
            if(!getString().equals(StringLatticeElement.bottom))
                return getString().lessThanEqual(rightValue.getString());
        }

        if(!getNull().equals(NullLatticeElement.bottom)) {
            return getNull().toBoolean().lessThanEqual(getCoercibleSingleValue(rightValue).toBoolean());
        }
        if(!getBoolean().equals(BooleanLatticeElement.bottom)) {
            return getBoolean().lessThanEqual(getCoercibleSingleValue(rightValue).toBoolean());
        }

        if(!getString().equals(StringLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getString().toNumber().lessThanOrEqual(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getString().toNumber().lessThanOrEqual(rightValue.getString().toNumber());
        }

        if(!getNumber().equals(NumberLatticeElement.bottom)) {
            if(!rightValue.getNumber().equals(NumberLatticeElement.bottom))
                return getNumber().lessThanOrEqual(rightValue.getNumber());
            if(!rightValue.getString().equals(StringLatticeElement.bottom))
                return getNumber().lessThanOrEqual(rightValue.getString().toNumber());
        }

        if(!getArray().equals(ArrayLatticeElement.bottom)) {
            if(!rightValue.getArray().equals(ArrayLatticeElement.bottom))
                return BooleanLatticeElement.top; // We know nothing about array comparison
            else
                return BooleanLatticeElement.boolFalse; // Arrays are always greater than anything else, so false for equal
        }

        throw new UnsupportedOperationException("Unsupported equal operation");
    }

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
    public boolean containedIn(ValueLatticeElement other) {
        return
                getArray().containedIn(other.getArray()) &&
                        getString().containedIn(other.getString()) &&
                        getNumber().containedIn(other.getNumber()) &&
                        getBoolean().containedIn(other.getBoolean()) &&
                        getNull().containedIn(other.getNull());
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
