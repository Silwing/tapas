package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.*;

import java.util.List;
import java.util.Set;

/**
 * Created by budde on 4/19/15.
 */
public class AnalysisLatticeElementImpl implements AnalysisLatticeElement {

    private final MapLatticeElement<Context, StateLatticeElement> mapLatticeElement;

    public AnalysisLatticeElementImpl(Generator<Context, StateLatticeElement> generator) {
        this(new MapLatticeElementImpl<>(generator));
    }


    public AnalysisLatticeElementImpl() {
        this(context -> new StateLatticeElementImpl());
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
    public List<StateLatticeElement> getValues() {
        return mapLatticeElement.getValues();
    }

    @Override
    public AnalysisLatticeElement addValue(Context key, Generator<Context, StateLatticeElement> generator) {
        return new AnalysisLatticeElementImpl(mapLatticeElement.addValue(key, generator));
    }


    @Override
    public AnalysisLatticeElement meet(MapLatticeElement<Context, StateLatticeElement> other) {
        assert this != other;
        return new AnalysisLatticeElementImpl(mapLatticeElement.meet(other));
    }

    @Override
    public AnalysisLatticeElement join(MapLatticeElement<Context, StateLatticeElement> other) {
        assert this != other;
        return new AnalysisLatticeElementImpl(mapLatticeElement.join(other));
    }

    @Override
    public boolean containedIn(MapLatticeElement<Context, StateLatticeElement> other) {
        throw new UnsupportedOperationException();
    }


    @Override
    public MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> getLocals(Context context) {
        return getValue(context).getLocals();
    }

    @Override
    public HeapLocationPowerSetLatticeElement getLocalsValue(Context context, VariableName variableName) {
        return getLocals(context).getValue(variableName);
    }

    @Override
    public AnalysisLatticeElement setLocals(Context context, MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> locals) {
        return addValue(context, c -> getValue(c).setLocals(locals));
    }

    @Override
    public AnalysisLatticeElement setLocalsValue(Context context, VariableName variableName, HeapLocationPowerSetLatticeElement set) {
        return setLocalsValue(context, variableName, n -> set);
    }

    @Override
    public AnalysisLatticeElement setLocalsValue(Context context, VariableName variableName, Generator<VariableName, HeapLocationPowerSetLatticeElement> generator) {
        return setLocals(context, getLocals(context).addValue(variableName, generator));
    }

    @Override
    public AnalysisLatticeElement addLocationToLocal(Context context, VariableName variableName, HeapLocation location) {
        return setLocalsValue(context, variableName, v -> getLocalsValue(context, v).addLocation(location));
    }

    @Override
    public MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> getGlobals(Context context) {
        return getValue(context).getGlobals();
    }

    @Override
    public HeapLocationPowerSetLatticeElement getGlobalsValue(Context context, VariableName variableName) {
        return getGlobals(context).getValue(variableName);
    }

    @Override
    public AnalysisLatticeElement addLocationToGlobal(Context context, VariableName variableName, HeapLocation location) {
        return setGlobalsValue(context, variableName, v -> getGlobalsValue(context, v).addLocation(location));
    }

    @Override
    public AnalysisLatticeElement setGlobals(Context context, MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> globals) {
        return addValue(context, c -> getValue(c).setGlobals(globals));
    }

    @Override
    public AnalysisLatticeElement setGlobalsValue(Context context, VariableName variableName, HeapLocationPowerSetLatticeElement set) {
        return setGlobalsValue(context, variableName, n -> set);
    }

    @Override
    public AnalysisLatticeElement setGlobalsValue(Context context, VariableName variableName, Generator<VariableName, HeapLocationPowerSetLatticeElement> generator) {
        return setGlobals(context, getGlobals(context).addValue(variableName, generator));
    }

    @Override
    public HeapMapLatticeElement getHeap(Context context) {
        return getValue(context).getHeap();
    }

    @Override
    public ValueLatticeElement getHeapValue(Context context, HeapLocation location) {
        return getHeap(context).getValue(location);
    }

    @Override
    public AnalysisLatticeElement setHeap(Context context, HeapMapLatticeElement heap) {
        return addValue(context, c -> getValue(c).setHeap(heap));
    }

    @Override
    public AnalysisLatticeElement setHeapValue(Context context, HeapLocation heapLocation, ValueLatticeElement value) {
        return setHeapValue(context, heapLocation, h -> value);
    }

    @Override
    public AnalysisLatticeElement joinHeapValue(Context context, HeapLocation heapLocation, ValueLatticeElement value) {
        return setHeapValue(context, heapLocation, value.join(getHeapValue(context, heapLocation)));
    }

    @Override
    public AnalysisLatticeElement setHeapValue(Context context, HeapLocation heapLocation, Generator<HeapLocation, ValueLatticeElement> generator) {
        return setHeap(context, getHeap(context).addValue(heapLocation, generator));
    }

    @Override
    public MapLatticeElement<TemporaryVariableName, ValueLatticeElement> getTemps(Context context) {
        return getValue(context).getTemps();
    }

    @Override
    public ValueLatticeElement getTempsValue(Context context, TemporaryVariableName name) {
        return getTemps(context).getValue(name);
    }

    @Override
    public AnalysisLatticeElement setTemps(Context context, MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack) {
        return addValue(context, c -> getValue(c).setTemps(stack));
    }

    @Override
    public AnalysisLatticeElement setTempsValue(Context context, TemporaryVariableName variableName, ValueLatticeElement value) {
        return setTempsValue(context, variableName, n -> value);
    }

    @Override
    public AnalysisLatticeElement setTempsValue(Context context, TemporaryVariableName variableName, Generator<TemporaryVariableName, ValueLatticeElement> generator) {
        return setTemps(context, getTemps(context).addValue(variableName, generator));
    }

    @Override
    public AnalysisLatticeElement joinTempsValue(Context context, TemporaryVariableName name, ValueLatticeElement value) {
        return setTempsValue(context, name, value.join(getTempsValue(context, name)));
    }

    @Override
    public MapLatticeElement<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> getHeapTemps(Context context) {
        return getValue(context).getHeapTemps();
    }

    @Override
    public HeapLocationPowerSetLatticeElement getHeapTempsValue(Context context, TemporaryHeapVariableName name) {
        return getValue(context).getHeapTemps().getValue(name);
    }

    @Override
    public AnalysisLatticeElement setHeapTemps(Context context, MapLatticeElement<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> stack) {
        return addValue(context, c -> getValue(c).setHeapTemps(stack));
    }

    @Override
    public AnalysisLatticeElement setHeapTempsValue(Context context, TemporaryHeapVariableName variableName, HeapLocationPowerSetLatticeElement value) {
        return setHeapTempsValue(context, variableName, v -> value);
    }

    @Override
    public AnalysisLatticeElement setHeapTempsValue(Context context, TemporaryHeapVariableName variableName, HeapLocation location) {
        return setHeapTempsValue(context, variableName, new HeapLocationPowerSetLatticeElementImpl(location));
    }

    @Override
    public AnalysisLatticeElement setHeapTempsValue(Context context, TemporaryHeapVariableName variableName, Generator<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> generator) {
        return setHeapTemps(context, getHeapTemps(context).addValue(variableName, generator));
    }

    @Override
    public AnalysisLatticeElement joinHeapTempsValue(Context context, TemporaryHeapVariableName name, HeapLocationPowerSetLatticeElement value) {
        return setHeapTempsValue(context, name, value.join(getHeapTempsValue(context, name)));
    }

    @Override
    public AnalysisLatticeElement joinHeapTempsValue(Context context, TemporaryHeapVariableName name, Set<HeapLocation> locationSet) {
        return joinHeapTempsValue(context, name, new HeapLocationPowerSetLatticeElementImpl(locationSet));
    }

    @Override
    public AnalysisLatticeElement joinHeapTempsValue(Context context, TemporaryHeapVariableName name, HeapLocation location) {
        return joinHeapTempsValue(context, name, new HeapLocationPowerSetLatticeElementImpl(location));
    }

    @Override
    public AnalysisLatticeElement setLocalsValue(Context context, VariableName name, Set<HeapLocation> argument) {
        return setLocalsValue(context, name, new HeapLocationPowerSetLatticeElementImpl(argument));
    }

    @Override
    public AnalysisLatticeElement setLocalsValue(Context context, Node node, VariableName name, ValueLatticeElement argument) {
        HeapLocation newLocation = new HeapLocationImpl(context, node);
        AnalysisLatticeElement resultLattice = setHeapValue(context, newLocation, argument);
        return resultLattice.setLocalsValue(context, name, new HeapLocationPowerSetLatticeElementImpl(newLocation));

    }

    @Override
    public AnalysisLatticeElement setGlobalsValue(Context context, Node node, VariableName name, ValueLatticeElement argument) {
        HeapLocation newLocation = new HeapLocationImpl(context, node);
        AnalysisLatticeElement resultLattice = setHeapValue(context, newLocation, argument);
        return resultLattice.setGlobalsValue(context, name, new HeapLocationPowerSetLatticeElementImpl(newLocation));

    }

    @Override
    public AnalysisLatticeElement setGlobalsValue(Context context, VariableName name, Set<HeapLocation> locationSet) {
        return setGlobalsValue(context, name, new HeapLocationPowerSetLatticeElementImpl(locationSet));
    }

    @Override
    public AnalysisLatticeElement setHeapTempsValue(Context context, TemporaryHeapVariableName targetTempHeapName, Set<HeapLocation> locationSet) {
        return setHeapTempsValue(context, targetTempHeapName, new HeapLocationPowerSetLatticeElementImpl(locationSet));
    }

    @Override
    public AnalysisLatticeElement setLocalsValue(Context context, Node node, Integer number, VariableName variableName, ValueLatticeElement value) {
        HeapLocation newLocation = new HeapLocationImpl(context, node, number);
        return setHeapValue(context, newLocation, value).setLocalsValue(context, variableName, new HeapLocationPowerSetLatticeElementImpl(newLocation));
    }

    @Override
    public AnalysisLatticeElement setGlobalsValue(ContextImpl context, Node node, Integer number, VariableName name, ValueLatticeElementImpl value) {
        HeapLocation newLocation = new HeapLocationImpl(context, node, number);
        return setHeapValue(context, newLocation, value).setGlobalsValue(context, name, new HeapLocationPowerSetLatticeElementImpl(newLocation));
    }



    private Set<Context> jointDomain(MapLatticeElement<Context, StateLatticeElement> m1, MapLatticeElement<Context, StateLatticeElement> m2) {
        Set<Context> newDomain = m1.getDomain();
        newDomain.addAll(m2.getDomain());
        return newDomain;
    }


    @Override
    public boolean containedIn(AnalysisLatticeElement other) {
        for (Context key : jointDomain(this, other)) {
            if (!getValue(key).containedIn(
                    other.getValue(key)
            )) {
                return false;
            }
        }

        return true;
    }


    @Override
    public void print(LatticePrinter printer) {
        mapLatticeElement.print(printer);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof AnalysisLatticeElement && mapLatticeElement.equals(other);
    }
}
