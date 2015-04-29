package dk.au.cs.tapas.lattice;

import dk.au.cs.tapas.analysis.Analysis;

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


    MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> getLocals(Context context);

    PowerSetLatticeElement<HeapLocation> getLocalsValue(Context context, VariableName variableName);

    AnalysisLatticeElement setLocals(Context context, MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> locals);

    AnalysisLatticeElement setLocalsValue(Context context, VariableName variableName, MapLatticeElement.Generator<VariableName,PowerSetLatticeElement<HeapLocation>> generator);

    AnalysisLatticeElement addLocationToLocal(Context context, VariableName variableName, HeapLocation location);

    MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> getGlobals(Context context);

    PowerSetLatticeElement<HeapLocation> getGlobalsValue(Context context, VariableName variableName);

    AnalysisLatticeElement addLocationToGlobal(Context context, VariableName variableName, HeapLocation location);

    AnalysisLatticeElement setGlobals(Context context, MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> globals);

    AnalysisLatticeElement setGlobalsValue(Context context, VariableName variableName, MapLatticeElement.Generator<VariableName,PowerSetLatticeElement<HeapLocation>> generator);

    MapLatticeElement<HeapLocation, ValueLatticeElement> getHeap(Context context);

    ValueLatticeElement getHeapValue(Context context, HeapLocation heapLocation);

    AnalysisLatticeElement setHeap(Context context, MapLatticeElement<HeapLocation, ValueLatticeElement> heap);

    AnalysisLatticeElement setHeapValue(Context context, HeapLocation heapLocation, MapLatticeElement.Generator<HeapLocation, ValueLatticeElement> generator);

    MapLatticeElement<TemporaryVariableName, ValueLatticeElement> getStack(Context context);

    ValueLatticeElement getStackValue(Context context, TemporaryVariableName name);

    AnalysisLatticeElement setStack(Context context, MapLatticeElement<TemporaryVariableName, ValueLatticeElement> stack);

    AnalysisLatticeElement setStackValue(Context context, TemporaryVariableName variableName, MapLatticeElement.Generator<TemporaryVariableName, ValueLatticeElement> generator);


}
