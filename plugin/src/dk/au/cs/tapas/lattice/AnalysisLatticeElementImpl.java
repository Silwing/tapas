package dk.au.cs.tapas.lattice;

import java.util.Set;

/**
 * Created by budde on 4/19/15.
 *
 */
public class AnalysisLatticeElementImpl implements AnalysisLatticeElement {

    private final MapLatticeElement<Context, StateLatticeElement> mapLatticeElement;

    public AnalysisLatticeElementImpl() {
        this(new MapLatticeElementImpl<>(context -> new StateLatticeElementImpl()));
    }

    private AnalysisLatticeElementImpl(MapLatticeElement<Context, StateLatticeElement> mapLatticeElement) {
        this.mapLatticeElement = mapLatticeElement;
    }

    @Override
    public Set<Context> getDomain() {
        return mapLatticeElement.getDomain();
    }

    @Override
    public StateLatticeElement getValue(Context key) {
        return mapLatticeElement.getValue(key);
    }

    @Override
    public AnalysisLatticeElement addValue(Context key, Generator<Context, StateLatticeElement> generator) {
        return new AnalysisLatticeElementImpl(mapLatticeElement.addValue(key, generator));
    }

    @Override
    public AnalysisLatticeElement meet(MapLatticeElement<Context, StateLatticeElement> other) {
        return new AnalysisLatticeElementImpl(mapLatticeElement.meet(other));
    }

    @Override
    public AnalysisLatticeElement join(MapLatticeElement<Context, StateLatticeElement> other) {
        return new AnalysisLatticeElementImpl(mapLatticeElement.join(other));
    }


    @Override
    public MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> getLocals(Context context) {
        return getValue(context).getLocals();
    }

    @Override
    public PowerSetLatticeElement<HeapLocation> getLocalsValue(Context context, VariableName variableName) {
        return getLocals(context).getValue(variableName);
    }

    @Override
    public AnalysisLatticeElement setLocals(Context context, MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> locals) {
        return addValue(context, c -> getValue(c).setLocals(locals));
    }

    @Override
    public AnalysisLatticeElement setLocalsValue(Context context, VariableName variableName, Generator<VariableName, PowerSetLatticeElement<HeapLocation>> generator) {
        return setLocals(context, getLocals(context).addValue(variableName, generator));
    }

    @Override
    public AnalysisLatticeElement addLocationToLocal(Context context, VariableName variableName, HeapLocation location) {
        return setLocalsValue(context, variableName, v -> getLocalsValue(context, v).addValue(location));
    }

    @Override
    public MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> getGlobals(Context context) {
        return getValue(context).getGlobals();
    }

    @Override
    public PowerSetLatticeElement<HeapLocation> getGlobalsValue(Context context, VariableName variableName) {
        return getGlobals(context).getValue(variableName);
    }

    @Override
    public AnalysisLatticeElement addLocationToGlobal(Context context, VariableName variableName, HeapLocation location) {
        return setGlobalsValue(context, variableName, v -> getGlobalsValue(context, v).addValue(location));
    }

    @Override
    public AnalysisLatticeElement setGlobals(Context context, MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> globals) {
        return addValue(context, c -> getValue(c).setGlobals(globals));
    }

    @Override
    public AnalysisLatticeElement setGlobalsValue(Context context, VariableName variableName, Generator<VariableName, PowerSetLatticeElement<HeapLocation>> generator) {
        return setGlobals(context, getGlobals(context).addValue(variableName, generator));
    }

    @Override
    public MapLatticeElement<HeapLocation, ValueLatticeElement> getHeap(Context context) {
        return getValue(context).getHeap();
    }

    @Override
    public ValueLatticeElement getHeapValue(Context context, HeapLocation location) {
        return getHeap(context).getValue(location);
    }

    @Override
    public AnalysisLatticeElement setHeap(Context context, MapLatticeElement<HeapLocation, ValueLatticeElement> heap) {
        return addValue(context, c -> getValue(c).setHeap(heap));
    }

    @Override
    public AnalysisLatticeElement setHeapValue(Context context, HeapLocation heapLocation, Generator<HeapLocation, ValueLatticeElement> generator) {
        return setHeap(context, getHeap(context).addValue(heapLocation, generator));
    }

    @Override
    public MapLatticeElement<TemporaryVariableName, ValueLatticeElement> getStack(Context context) {
        return getValue(context).getStack();
    }

    @Override
    public ValueLatticeElement getStackValue(Context context, TemporaryVariableName name) {
        return getStack(context).getValue(name);
    }

    @Override
    public AnalysisLatticeElement setStack(Context context, MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack) {
        return addValue(context, c -> getValue(c).setStack(stack));
    }

    @Override
    public AnalysisLatticeElement setStackValue(Context context, TemporaryVariableName variableName, Generator<TemporaryVariableName, ValueLatticeElement> generator) {
        return setStack(context, getStack(context).addValue(variableName, generator));
    }

    @Override
    public boolean containedIn(MapLatticeElement<Context, StateLatticeElement> other) {
        return mapLatticeElement.containedIn(other);
    }

    @Override
    public boolean equals(MapLatticeElement<Context, StateLatticeElement> other) {
        return mapLatticeElement.equals(other);
    }
}
