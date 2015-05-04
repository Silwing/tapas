package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 */
public class StateLatticeElementImpl implements StateLatticeElement {

    private final MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> locals;
    private final MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> globals;
    private final HeapMapLatticeElement heap;
    private final MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack;

    public StateLatticeElementImpl() {
        this(
                new LocalsMapLatticeElementImpl(),
                new GlobalsMapLatticeElementImpl(),
                new HeapMapLatticeElementImpl(),
                new StateMapLatticeElementImpl());
    }

    public StateLatticeElementImpl(
            MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> locals,
            MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> globals,
            HeapMapLatticeElement heap,
            MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack) {
        this.locals = locals;
        this.globals = globals;
        this.heap = heap;
        this.stack = stack;
    }

    @Override
    public MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> getLocals() {
        return locals;
    }

    @Override
    public StateLatticeElement setLocals(MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> locals) {
        return new StateLatticeElementImpl(locals, globals, heap, stack);
    }

    @Override
    public MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> getGlobals() {
        return globals;
    }

    @Override
    public StateLatticeElement setGlobals(MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> globals) {
        return new StateLatticeElementImpl(locals, globals, heap, stack);
    }

    @Override
    public HeapMapLatticeElement getHeap() {
        return heap;
    }

    @Override
    public StateLatticeElement setHeap(HeapMapLatticeElement heap) {
        return new StateLatticeElementImpl(locals, globals, heap, stack);
    }

    @Override
    public MapLatticeElement<TemporaryVariableName, ValueLatticeElement> getStack() {
        return stack;
    }

    @Override
    public StateLatticeElement setStack(MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack) {
        return new StateLatticeElementImpl(locals, globals, heap, stack);
    }

    @Override
    public StateLatticeElement meet(StateLatticeElement other) {
        return new StateLatticeElementImpl(
                getLocals().meet(other.getLocals()),
                getGlobals().meet(other.getGlobals()),
                getHeap().meet(other.getHeap()),
                getStack().meet(other.getStack()));
    }

    @Override
    public StateLatticeElement join(StateLatticeElement other) {
        return new StateLatticeElementImpl(
                getLocals().join(other.getLocals()),
                getGlobals().join(other.getGlobals()),
                getHeap().join(other.getHeap()),
                getStack().join(other.getStack()));
    }

    @Override
    public boolean containedIn(HeapMapLatticeElement thisAnalysis, StateLatticeElement other, HeapMapLatticeElement otherAnalysis) {
        return
                getLocals().containedIn(thisAnalysis, other.getLocals(), otherAnalysis) &&
                        getGlobals().containedIn(thisAnalysis, other.getGlobals(), otherAnalysis) &&
                        getHeap().containedIn(thisAnalysis, other.getHeap(), otherAnalysis) &&
                        getStack().containedIn(thisAnalysis, other.getStack(), otherAnalysis);
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("{");
        printer.startSection();
        printer.linebreak();
        printer.print("stack -> ");
        stack.print(printer);
        printer.print(",");
        printer.linebreak();
        printer.print("locals -> ");
        locals.print(printer);
        printer.print(",");
        printer.linebreak();
        printer.print("globals -> ");
        globals.print(printer);
        printer.print(",");
        printer.linebreak();
        printer.print("heap -> ");
        heap.print(printer);
        printer.endSection();
        printer.linebreak();
        printer.print("}");
    }


    public boolean equals(Object other) {
        return other == this || (
                other instanceof StateLatticeElement &&
                        ((StateLatticeElement) other).getStack().equals(getStack()) &&
                        ((StateLatticeElement) other).getHeap().equals(getHeap()) &&
                        ((StateLatticeElement) other).getGlobals().equals(getGlobals()) &&
                        ((StateLatticeElement) other).getLocals().equals(getLocals()));
    }

}
