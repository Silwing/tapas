package dk.au.cs.tapas.lattice.element;

import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.*;

import java.util.Set;

/**
 * Created by budde on 4/28/15.
 */
public interface AnalysisLatticeElement extends MapLatticeElement<Context, StateLatticeElement>{

    @Override
    AnalysisLatticeElement addValue(Context key, Generator<Context, StateLatticeElement> generator);

    @Override
    AnalysisLatticeElement meet(MapLatticeElement<Context, StateLatticeElement> other);

    @Override
    AnalysisLatticeElement join(MapLatticeElement<Context, StateLatticeElement> other);

    boolean containedIn(AnalysisLatticeElement otherAnalysis);

    MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> getLocals(Context context);

    HeapLocationPowerSetLatticeElement getLocalsValue(Context context, VariableName variableName);

    AnalysisLatticeElement setLocals(Context context, MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> locals);

    AnalysisLatticeElement setLocalsValue(Context context, VariableName variableName, HeapLocationPowerSetLatticeElement set);

    AnalysisLatticeElement setLocalsValue(Context context, VariableName variableName, MapLatticeElement.Generator<VariableName,HeapLocationPowerSetLatticeElement> generator);

    AnalysisLatticeElement addLocationToLocal(Context context, VariableName variableName, HeapLocation location);

    MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> getGlobals(Context context);

    HeapLocationPowerSetLatticeElement getGlobalsValue(Context context, VariableName variableName);

    AnalysisLatticeElement addLocationToGlobal(Context context, VariableName variableName, HeapLocation location);

    AnalysisLatticeElement setGlobals(Context context, MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> globals);


    AnalysisLatticeElement setGlobalsValue(Context context, VariableName variableName, HeapLocationPowerSetLatticeElement set);

    AnalysisLatticeElement setGlobalsValue(Context context, VariableName variableName, MapLatticeElement.Generator<VariableName,HeapLocationPowerSetLatticeElement> generator);

    HeapMapLatticeElement getHeap(Context context);

    ValueLatticeElement getHeapValue(Context context, HeapLocation heapLocation);

    AnalysisLatticeElement setHeap(Context context, HeapMapLatticeElement heap);

    AnalysisLatticeElement setHeapValue(Context context, HeapLocation heapLocation, ValueLatticeElement value);

    AnalysisLatticeElement joinHeapValue(Context context, HeapLocation heapLocation, ValueLatticeElement value);

    AnalysisLatticeElement setHeapValue(Context context, HeapLocation heapLocation, MapLatticeElement.Generator<HeapLocation, ValueLatticeElement> generator);

    MapLatticeElement<TemporaryVariableName, ValueLatticeElement> getTemps(Context context);

    ValueLatticeElement getTempsValue(Context context, TemporaryVariableName name);

    AnalysisLatticeElement setTemps(Context context, MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack);

    AnalysisLatticeElement setTempsValue(Context context, TemporaryVariableName variableName, ValueLatticeElement value);

    AnalysisLatticeElement setTempsValue(Context context, TemporaryVariableName variableName, MapLatticeElement.Generator<TemporaryVariableName, ValueLatticeElement> generator);

    AnalysisLatticeElement joinTempsValue(Context context, TemporaryVariableName name, ValueLatticeElement value);

    MapLatticeElement<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> getHeapTemps(Context context);

    HeapLocationPowerSetLatticeElement getHeapTempsValue(Context context, TemporaryHeapVariableName name);

    AnalysisLatticeElement setHeapTemps(Context context, MapLatticeElement<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> stack);

    AnalysisLatticeElement setHeapTempsValue(Context context, TemporaryHeapVariableName variableName, HeapLocationPowerSetLatticeElement value);

    AnalysisLatticeElement setHeapTempsValue(Context context, TemporaryHeapVariableName variableName, HeapLocation location);

    AnalysisLatticeElement setHeapTempsValue(Context context, TemporaryHeapVariableName variableName, MapLatticeElement.Generator<TemporaryHeapVariableName, HeapLocationPowerSetLatticeElement> generator);

    AnalysisLatticeElement joinHeapTempsValue(Context context, TemporaryHeapVariableName name, HeapLocationPowerSetLatticeElement value);

    AnalysisLatticeElement joinHeapTempsValue(Context context, TemporaryHeapVariableName name, Set<HeapLocation> locationSet);

    AnalysisLatticeElement joinHeapTempsValue(Context context, TemporaryHeapVariableName name, HeapLocation location);

    AnalysisLatticeElement setLocalsValue(Context context, VariableName name, Set<HeapLocation> argument);

    AnalysisLatticeElement setLocalsValue(Context context, Node node, VariableName name, ValueLatticeElement argument);

    AnalysisLatticeElement setGlobalsValue(Context context, Node node, VariableName name, ValueLatticeElement argument);

    AnalysisLatticeElement setGlobalsValue(Context context, VariableName name, Set<HeapLocation> locationSet);

    AnalysisLatticeElement setHeapTempsValue(Context context, TemporaryHeapVariableName targetTempHeapName, Set<HeapLocation> locationSet);

    AnalysisLatticeElement setLocalsValue(Context newContext, Node node, Integer number, VariableName variableName, ValueLatticeElement value);

    AnalysisLatticeElement setGlobalsValue(ContextImpl context, Node node, Integer number, VariableName name, ValueLatticeElementImpl value);
}
