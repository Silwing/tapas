package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/20/15.
 */
public class StateLatticeElementImpl implements StateLatticeElement {

    private final MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> locals;
    private final MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> globals;
    private final MapLatticeElement<HeapLocation, ValueLatticeElement> heap;
    private final MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack;

    public StateLatticeElementImpl() {
        this(
                new LocalsMapLatticeElementImpl(),
                new GlobalsMapLatticeElementImpl(),
                new HeapMapLatticeElementImpl(),
                new StateMapLatticeElementImpl());
    }

    public StateLatticeElementImpl(
            MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> locals,
            MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> globals,
            MapLatticeElement<HeapLocation, ValueLatticeElement> heap,
            MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack) {
        this.locals = locals;
        this.globals = globals;
        this.heap = heap;
        this.stack = stack;
    }

    @Override
    public MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> getLocals() {
        return locals;
    }

    @Override
    public StateLatticeElement setLocals(MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> locals) {
        return new StateLatticeElementImpl(locals, globals, heap, stack);
    }

    @Override
    public MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> getGlobals() {
        return globals;
    }

    @Override
    public StateLatticeElement setGlobals(MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> globals) {
        return new StateLatticeElementImpl(locals, globals, heap, stack);
    }

    @Override
    public MapLatticeElement<HeapLocation, ValueLatticeElement> getHeap() {
        return heap;
    }

    @Override
    public StateLatticeElement setHeap(MapLatticeElement<HeapLocation, ValueLatticeElement> heap) {
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
    public boolean containedIn(StateLatticeElement other) {
        return
                getLocals().containedIn(other.getLocals()) &&
                        getGlobals().containedIn(other.getGlobals()) &&
                        getHeap().containedIn(other.getHeap()) &&
                        getStack().containedIn(other.getStack());
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
