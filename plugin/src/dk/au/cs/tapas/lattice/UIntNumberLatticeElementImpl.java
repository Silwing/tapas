package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 *
 */
public class UIntNumberLatticeElementImpl extends MiddleLatticeElementImpl<NumberLatticeElement> implements UIntNumberLatticeElement {
    private final Integer number;

    public UIntNumberLatticeElementImpl(Integer number) {
        super(
                (NumberLatticeElement n) -> bottom,
                (NumberLatticeElement n) -> n instanceof UIntNumberLatticeElementImpl?uIntTop:top
        );
        this.number = number;
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print(number.toString());
    }


    public boolean equals(Object object){
        return object == this || (object instanceof UIntNumberLatticeElementImpl && ((UIntNumberLatticeElementImpl) object).getNumber().equals(getNumber()));
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public boolean containedIn(NumberLatticeElement other) {
        return other.equals(this) || uIntTop.containedIn(other);
    }


    @Override
    public BooleanLatticeElement toBoolean() {
        return number.equals(0)?BooleanLatticeElement.boolFalse:BooleanLatticeElement.boolTrue;
    }

    @Override
    public NumberLatticeElement toNumber() {
        return this;
    }

    @Override
    public IntegerLatticeElement toInteger() {
        return number instanceof Integer ? IntegerLatticeElement.generateElement(number.intValue()) : IntegerLatticeElement.generateElement((int)Math.floor(number.doubleValue()));
    }

    @Override
    public NumberLatticeElement increment() {
        return NumberLatticeElement.generateNumberLatticeElement(number.intValue()+1);
    }

    @Override
    public NumberLatticeElement decrement() {
        return NumberLatticeElement.generateNumberLatticeElement(number.intValue()-1);
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
