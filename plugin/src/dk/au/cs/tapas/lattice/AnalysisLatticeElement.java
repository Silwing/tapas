package dk.au.cs.tapas.lattice;

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

    MapLatticeElement<TemporaryVariableName, ValueLatticeElement> getStack(Context context);

    ValueLatticeElement getStackValue(Context context, TemporaryVariableName name);

    AnalysisLatticeElement setStack(Context context, MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack);

    AnalysisLatticeElement setStackValue(Context context, TemporaryVariableName variableName, ValueLatticeElement value);

    AnalysisLatticeElement setStackValue(Context context, TemporaryVariableName variableName, MapLatticeElement.Generator<TemporaryVariableName, ValueLatticeElement> generator);


    AnalysisLatticeElement joinStackValue(Context context, TemporaryVariableName name, ValueLatticeElement value);

    AnalysisLatticeElement setLocalsValue(Context context, VariableName name, Set<HeapLocation> argument);

    AnalysisLatticeElement setLocalsValue(Context context, VariableName name, ValueLatticeElement argument);

    AnalysisLatticeElement setGlobalsValue(Context context, VariableName name, ValueLatticeElement argument);

    AnalysisLatticeElement setGlobalsValue(Context context, VariableName name, Set<HeapLocation> locationSet);
}
