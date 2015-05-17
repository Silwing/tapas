package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;

/**
 * Created by budde on 4/20/15.
 *
 */
public class NotUIntTopStringLatticeElementImpl extends MiddleLatticeElementImpl<StringLatticeElement> implements StringLatticeElement {


    public NotUIntTopStringLatticeElementImpl() {
        super(
                (StringLatticeElement s) -> bottom,
                (StringLatticeElement s) -> top
        );
    }


    @Override
    public void print(LatticePrinter printer) {
        printer.print("NotUIntString");
    }

    public boolean equals(Object other) {
        return other instanceof NotUIntTopStringLatticeElementImpl;
    }

    @Override
    public boolean containedIn(StringLatticeElement other) {
        return other.equals(this) || other.equals(top);
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.top;
    }

    @Override

    public NumberLatticeElement toNumber() {
        return NumberLatticeElement.top;
    }

    @Override
    public StringLatticeElement concat(StringLatticeElement other) {
        return StringLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement equalOperation(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other.containedIn(uIntStringTop)) return BooleanLatticeElement.boolFalse;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement notEqual(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        if(other.containedIn(uIntStringTop)) return BooleanLatticeElement.boolTrue;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThan(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement lessThanEqual(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThan(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        return BooleanLatticeElement.top;
    }

    @Override
    public BooleanLatticeElement greaterThanEqual(StringLatticeElement other) {
        if(other.equals(bottom)) return BooleanLatticeElement.bottom;

        return BooleanLatticeElement.top;
    }

    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.top;

    }

    @Override
    public StringLatticeElement toStringLattice() {
        return this;
    }
    @Override
    public IndexLatticeElement toArrayIndex() {
        return IndexLatticeElement.generateStringLIndex(this);
    }
}
