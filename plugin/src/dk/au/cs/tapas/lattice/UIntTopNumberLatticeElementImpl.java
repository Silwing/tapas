package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class UIntTopNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements NumberLatticeElement{

    public UIntTopNumberLatticeElementImpl() {
        super(
                (NumberLatticeElement n) -> bottom,
                (NumberLatticeElement n) -> top
        );
    }

    @Override
    public boolean containedIn( NumberLatticeElement other) {
        return other.equals(this) || other.equals(top);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("UIntNumber");
    }


    public boolean equals(Object object){
        return object instanceof UIntTopNumberLatticeElementImpl;
    }

    @Override
    public BooleanLatticeElement toBoolean() {
        return BooleanLatticeElement.top;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return this;
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return IntegerLatticeElement.top;
    }

    @Override
    public NumberLatticeElement increment() {
        return this;
    }

    @Override
    public NumberLatticeElement decrement() {
        return this;
    }
    @Override
    public NumberLatticeElement add(NumberLatticeElement other) {
        return top;
    }

    @Override
    public NumberLatticeElement subtract(NumberLatticeElement other) {
        return top;
    }

    @Override
    public NumberLatticeElement multiply(NumberLatticeElement other) {
        return top;
    }

    @Override
    public NumberLatticeElement divide(NumberLatticeElement other) {
        return top;
    }

    @Override
    public NumberLatticeElement modulo(NumberLatticeElement other) {
        return top;
    }

    @Override
    public NumberLatticeElement exponent(NumberLatticeElement other) {
        return top;
    }

    @Override
    public BooleanLatticeElement greaterThan(NumberLatticeElement other) {
        return BooleanLatticeElement.top;    }

    @Override
    public BooleanLatticeElement lessThan(NumberLatticeElement other) {
        return BooleanLatticeElement.top;    }

    @Override
    public BooleanLatticeElement greaterThanOrEqual(NumberLatticeElement other) {
        return BooleanLatticeElement.top;    }

    @Override
    public BooleanLatticeElement lessThanOrEqual(NumberLatticeElement numberLatticeElement) {
        return BooleanLatticeElement.top;    }

    @Override
    public IndexLatticeElement toArrayIndex() {
        return null; //TODO implement
    }
}
