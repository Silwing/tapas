package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.lattice.LatticePrinter;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.VariableName;

/**
 * Created by budde on 4/20/15.
 */
public class StateLatticeElementImpl implements StateLatticeElement {

    private final MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> locals;
    private final MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> globals;
    private final HeapMapLatticeElement heap;
    private final MapLatticeElement<TemporaryVariableName, ValueLatticeElement> temps;
    private final MapLatticeElement<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> heapTemps;

    public StateLatticeElementImpl() {
        this(
                new LocalsMapLatticeElementImpl(),
                new GlobalsMapLatticeElementImpl(),
                new HeapMapLatticeElementImpl(),
                new TempsMapLatticeElementImpl(),
                new HeapTempsLatticeElementImpl());
    }

    public StateLatticeElementImpl(
            MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> locals,
            MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> globals,
            HeapMapLatticeElement heap,
            MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack,
            MapLatticeElement<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> heapTemps) {
        this.locals = locals;
        this.globals = globals;
        this.heap = heap;
        this.temps = stack;
        this.heapTemps = heapTemps;
    }

    @Override
    public MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> getLocals() {
        return locals;
    }

    @Override
    public StateLatticeElement setLocals(MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> locals) {
        return new StateLatticeElementImpl(locals, globals, heap, temps, heapTemps);
    }

    @Override
    public MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> getGlobals() {
        return globals;
    }

    @Override
    public StateLatticeElement setGlobals(MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> globals) {
        return new StateLatticeElementImpl(locals, globals, heap, temps, heapTemps);
    }

    @Override
    public HeapMapLatticeElement getHeap() {
        return heap;
    }

    @Override
    public StateLatticeElement setHeap(HeapMapLatticeElement heap) {
        return new StateLatticeElementImpl(locals, globals, heap, temps, heapTemps);
    }

    @Override
    public MapLatticeElement<TemporaryVariableName, ValueLatticeElement> getTemps() {
        return temps;
    }

    @Override
    public StateLatticeElement setTemps(MapLatticeElement<TemporaryVariableName, ValueLatticeElement> temps) {
        return new StateLatticeElementImpl(locals, globals, heap, temps, heapTemps);
    }

    @Override
    public MapLatticeElement<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> getHeapTemps() {
        return heapTemps;
    }

    @Override
    public StateLatticeElement setHeapTemps(MapLatticeElement<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> temps) {
        return new StateLatticeElementImpl(locals, globals, heap, this.temps, temps);
    }

    @Override
    public StateLatticeElement meet(StateLatticeElement other) {
        return new StateLatticeElementImpl(
                getLocals().meet(other.getLocals()),
                getGlobals().meet(other.getGlobals()),
                getHeap().meet(other.getHeap()),
                getTemps().meet(other.getTemps()),
                getHeapTemps().meet(other.getHeapTemps()));
    }

    @Override
    public StateLatticeElement join(StateLatticeElement other) {
        return new StateLatticeElementImpl(
                getLocals().join(other.getLocals()),
                getGlobals().join(other.getGlobals()),
                getHeap().join(other.getHeap()),
                getTemps().join(other.getTemps()),
                getHeapTemps().join(other.getHeapTemps()));
    }

    @Override
    public boolean containedIn(StateLatticeElement other) {
        return
                getLocals().containedIn(other.getLocals()) &&
                        getGlobals().containedIn(other.getGlobals()) &&
                        getHeap().containedIn(other.getHeap()) &&
                        getTemps().containedIn(other.getTemps()) &&
                        getHeapTemps().containedIn(other.getHeapTemps());
    }

    @Override
    public void print(LatticePrinter printer) {
        printer.print("{");
        printer.startSection();
        printer.linebreak();
        printer.print("temps -> ");
        temps.print(printer);
        printer.print(",");
        printer.linebreak();
        printer.print("heapTemps -> ");
        heapTemps.print(printer);
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
                        ((StateLatticeElement) other).getTemps().equals(getTemps()) &&
                        ((StateLatticeElement) other).getHeap().equals(getHeap()) &&
                        ((StateLatticeElement) other).getGlobals().equals(getGlobals()) &&
                        ((StateLatticeElement) other).getHeapTemps().equals(getHeapTemps()) &&
                        ((StateLatticeElement) other).getLocals().equals(getLocals()));
    }

}
