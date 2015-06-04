package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.*;
import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.cfg.graph.LibraryFunctionGraph;
import dk.au.cs.tapas.cfg.graph.NumberConstantImpl;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.*;
import dk.au.cs.tapas.lattice.element.*;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Silwing on 28-04-2015.
 */
public class TypeAnalysisImpl implements Analysis {

    private final AnalysisAnnotator annotator;

    public TypeAnalysisImpl(AnalysisAnnotator annotator) {
        this.annotator = annotator;
    }

    @Override
    public AnalysisLatticeElement getEmptyLattice() {
        return new AnalysisLatticeElementImpl();
    }

    @Override
    public AnalysisLatticeElement getStartLattice(Graph graph) {
        AnalysisLatticeElement lattice = new AnalysisLatticeElementImpl(c -> new StateLatticeElementImpl(new HeapMapLatticeElementImpl(v -> new ValueLatticeElementImpl(NullLatticeElement.top))));
        Context context = new ContextImpl();
        Function<Integer, HeapLocation> generator = i -> new HeapLocationImpl(context, graph.getEntryNode(), i);
        lattice = lattice.setGlobalsValue(context, VariableName.GLOBALS, new HeapLocationPowerSetLatticeElementImpl(generator.apply(0)));
        lattice = lattice.setGlobalsValue(context, VariableName.SERVER, new HeapLocationPowerSetLatticeElementImpl(generator.apply(1)));
        lattice = lattice.setGlobalsValue(context, VariableName.SESSION, new HeapLocationPowerSetLatticeElementImpl(generator.apply(2)));
        lattice = lattice.setGlobalsValue(context, VariableName.ENV, new HeapLocationPowerSetLatticeElementImpl(generator.apply(3)));
        lattice = lattice.setGlobalsValue(context, VariableName.COOKIE, new HeapLocationPowerSetLatticeElementImpl(generator.apply(4)));
        lattice = lattice.setGlobalsValue(context, VariableName.POST, new HeapLocationPowerSetLatticeElementImpl(generator.apply(5)));
        lattice = lattice.setGlobalsValue(context, VariableName.GET, new HeapLocationPowerSetLatticeElementImpl(generator.apply(6)));
        lattice = lattice.setGlobalsValue(context, VariableName.REQUEST, new HeapLocationPowerSetLatticeElementImpl(generator.apply(7)));
        lattice = lattice.setGlobalsValue(context, VariableName.FILES, new HeapLocationPowerSetLatticeElementImpl(generator.apply(8)));
        lattice = lattice.setHeapValue(context, generator.apply(0), new ValueLatticeElementImpl(ArrayLatticeElement.top));
        lattice = lattice.setHeapValue(context, generator.apply(1), new ValueLatticeElementImpl(ArrayLatticeElement.top));
        lattice = lattice.setHeapValue(context, generator.apply(2), new ValueLatticeElementImpl(ArrayLatticeElement.top));
        lattice = lattice.setHeapValue(context, generator.apply(3), new ValueLatticeElementImpl(ArrayLatticeElement.generateMap(IndexLatticeElement.top, generator.apply(9))));
        lattice = lattice.setHeapValue(context, generator.apply(4), new ValueLatticeElementImpl(ArrayLatticeElement.generateMap(IndexLatticeElement.top, generator.apply(10))));
        lattice = lattice.setHeapValue(context, generator.apply(5), new ValueLatticeElementImpl(ArrayLatticeElement.generateMap(IndexLatticeElement.top, generator.apply(11))));
        lattice = lattice.setHeapValue(context, generator.apply(6), new ValueLatticeElementImpl(ArrayLatticeElement.generateMap(IndexLatticeElement.top, generator.apply(12))));
        lattice = lattice.setHeapValue(context, generator.apply(7), new ValueLatticeElementImpl(ArrayLatticeElement.generateMap(IndexLatticeElement.top, generator.apply(13))));
        lattice = lattice.setHeapValue(context, generator.apply(8), new ValueLatticeElementImpl(ArrayLatticeElement.generateMap(IndexLatticeElement.top, generator.apply(14))));
        lattice = lattice.setHeapValue(context, generator.apply(9), new ValueLatticeElementImpl(StringLatticeElement.top));
        lattice = lattice.setHeapValue(context, generator.apply(10), new ValueLatticeElementImpl(StringLatticeElement.top));
        lattice = lattice.setHeapValue(context, generator.apply(11), new ValueLatticeElementImpl(ArrayLatticeElement.top, StringLatticeElement.top));
        lattice = lattice.setHeapValue(context, generator.apply(12), new ValueLatticeElementImpl(ArrayLatticeElement.top, StringLatticeElement.top));
        lattice = lattice.setHeapValue(context, generator.apply(13), new ValueLatticeElementImpl(ArrayLatticeElement.top, StringLatticeElement.top));
        lattice = lattice.setHeapValue(context, generator.apply(14), new ValueLatticeElementImpl(ArrayLatticeElement.generateMap(IndexLatticeElement.top, generator.apply(15))));
        lattice = lattice.setHeapValue(context, generator.apply(15), new ValueLatticeElementImpl(StringLatticeElement.top, NumberLatticeElement.top));

        return lattice;
    }

    @Override
    public AnalysisLatticeElement analyse(AnalysisTarget target, AnalysisLatticeElement latticeElement) {
        Node node = target.getNode();
        annotator.setNode(node);
        //latticeElement.print(new PrintStreamLatticePrinter(System.out));
        Context context = target.getContext().toContext();
        if (node instanceof VariableReadLocationSetNode) {
            return analyse((VariableReadLocationSetNode) node, latticeElement, context);
        }
        if (node instanceof ArrayInitStackOperationNode) {
            return analyse((ArrayInitStackOperationNode) node, latticeElement, context);
        }
        if (node instanceof ArrayAppendStackOperationNode) {
            return analyse((ArrayAppendStackOperationNode) node, latticeElement, context);
        }
        if (node instanceof ArrayAppendLocationSetNode) {
            return analyse((ArrayAppendLocationSetNode) node, latticeElement, context);
        }
        if (node instanceof ArrayReadLocationSetNode) {
            return analyse((ArrayReadLocationSetNode) node, latticeElement, context);
        }
        if (node instanceof ArrayReadStackOperationNode) {
            return analyse((ArrayReadStackOperationNode) node, latticeElement, context);
        }
        if (node instanceof ArrayWriteStackOperationNode) {
            return analyse((ArrayWriteStackOperationNode) node, latticeElement, context);
        }
        if (node instanceof VariableAssignmentNode) {
            return analyse((VariableAssignmentNode) node, latticeElement, context);
        }
        if (node instanceof ArrayWriteAssignmentNodeImpl) {
            return analyse((ArrayWriteAssignmentNode) node, latticeElement, context);
        }
        if (node instanceof ArrayAppendAssignmentNode) {
            return analyse((ArrayAppendAssignmentNode) node, latticeElement, context);
        }
        if (node instanceof ShortCircuitBinaryOperationNode) {
            return analyse((ShortCircuitBinaryOperationNode) node, latticeElement, context);
        }
        if (node instanceof BinaryOperationNode) {
            return analyse((BinaryOperationNode) node, latticeElement, context);
        }
        if (node instanceof CallNode) {
            return analyse((CallNode) node, latticeElement, context);
        }

        if (node instanceof IncrementDecrementOperationStackOperationNode) {
            return analyse((IncrementDecrementOperationStackOperationNode) node, latticeElement, context);
        }
        if (node instanceof ReadConstNode) {
            return analyse((ReadConstNode) node, latticeElement, context);
        }
        if (node instanceof VariableReadNode) {
            return analyse((VariableReadNode) node, latticeElement, context);
        }
        if (node instanceof ArrayWriteReferenceAssignmentNode) {
            return analyse((ArrayWriteReferenceAssignmentNode) node, latticeElement, context);
        }
        if (node instanceof ArrayAppendReferenceAssignmentNode) {
            return analyse((ArrayAppendReferenceAssignmentNode) node, latticeElement, context);
        }
        if (node instanceof VariableReferenceAssignmentNode) {
            return analyse((VariableReferenceAssignmentNode) node, latticeElement, context);
        }
        if (node instanceof ResultNode) {
            ResultNode resultNode = (ResultNode) node;
            if (resultNode.getFunctionGraph() instanceof LibraryFunctionGraph) {
                return ((LibraryFunctionGraph) resultNode.getFunctionGraph()).analyse(resultNode, context, latticeElement, annotator);
            }
            return analyse(resultNode, latticeElement, target.getCallLattice(), context);
        }

        if (node instanceof UnaryOperationNode) {
            return analyse((UnaryOperationNode) node, latticeElement, context);
        }

        if (node instanceof GlobalNode) {
            return analyse((GlobalNode) node, latticeElement, context);
        }


        // Fallback to identity function for unhandled nodes
        return latticeElement;

    }

    private AnalysisLatticeElement analyse(ArrayAppendAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement listValue = latticeElement.getTempsValue(context, node.getValueName());
        HeapLocation listLocation = new HeapLocationImpl(context, node);

        latticeElement = latticeElement
                .setTempsValue(context, node.getTargetName(), listValue)
                .setHeapValue(context, listLocation, listValue);

        allCheckArray(latticeElement, context, node.getVariableTempHeapName(), a -> a instanceof MapArrayLatticeElement, () -> annotator.error("Appending on map"));
        anyCheckArray(latticeElement, context, node.getVariableTempHeapName(), a -> a instanceof MapArrayLatticeElement, () -> annotator.warning("Possible appending on map"));
        checkArrayAddValue(latticeElement.getValue(context), latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations(), listLocation);

        for (HeapLocation location : latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations()) {
            ValueLatticeElement newValue = new ValueLatticeElementImpl(ArrayLatticeElement.generateList(listLocation));
            latticeElement = latticeElement.joinHeapValue(context, location, newValue);
        }
        return latticeElement;
    }


    private AnalysisLatticeElement analyse(ArrayAppendReferenceAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement listValue = new ValueLatticeElementImpl(ArrayLatticeElement.generateList(latticeElement.getHeapTempsValue(context, node.getValueTempHeapName())));

        latticeElement = latticeElement
                .setTempsValue(
                        context,
                        node.getTargetName(),
                        latticeElement.getHeap(context).getValue(
                                latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()),
                                LatticeElement::join));

        allCheckArray(latticeElement, context, node.getVariableTempHeapName(), a -> a instanceof MapArrayLatticeElement, () -> annotator.error("Appending on map"));
        anyCheckArray(latticeElement, context, node.getVariableTempHeapName(), a -> a instanceof MapArrayLatticeElement, () -> annotator.warning("Possible appending on map"));
        checkArrayAddValue(latticeElement.getValue(context),
                latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations(),
                latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()).getLocations());

        for (HeapLocation location : latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations()) {
            latticeElement = latticeElement.joinHeapValue(context, location, listValue);
        }
        return latticeElement;
    }

    private AnalysisLatticeElement analyse(ArrayAppendLocationSetNode node, AnalysisLatticeElement latticeElement, Context context) {

        allCheckArray(latticeElement, context, node.getVariableTempHeapName(), a -> a instanceof MapArrayLatticeElement, () -> annotator.error("Appending on map"));
        anyCheckArray(latticeElement, context, node.getVariableTempHeapName(), a -> a instanceof MapArrayLatticeElement, () -> annotator.warning("Possible appending on map"));
        latticeElement = latticeElement.setHeapTempsValue(context, node.getTargetTempHeapName(), new HeapLocationPowerSetLatticeElementImpl());
        HeapLocation newLoc = new HeapLocationImpl(context, node);
        latticeElement = latticeElement.joinHeapValue(context, newLoc, new ValueLatticeElementImpl());
        for (HeapLocation loc : latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations()) {
            latticeElement = latticeElement.joinHeapTempsValue(context, node.getTargetTempHeapName(), new HeapLocationPowerSetLatticeElementImpl(newLoc));
            latticeElement = latticeElement.joinHeapValue(context, loc, new ValueLatticeElementImpl(ArrayLatticeElement.generateList(newLoc)));
        }

        return latticeElement;
    }

    private AnalysisLatticeElement analyse(ArrayAppendStackOperationNode node, AnalysisLatticeElement l, Context context) {
        ValueLatticeElement newValue = l.getTempsValue(context, node.getValueName());
        ValueLatticeElement oldArray = l.getTempsValue(context, node.getTargetName());
        if (oldArray.getArray() instanceof MapArrayLatticeElement) {
            annotator.error("Appending on map");
        } else if (oldArray.getArray() instanceof ListArrayLatticeElement) {
            checkArrayAddValue(l.getHeap(context).getValue(((ListArrayLatticeElement) oldArray.getArray()).getLocations(), LatticeElement::join), newValue);
        }

        HeapLocation location = new HeapLocationImpl(context, node);
        ArrayLatticeElement list = ArrayLatticeElement.generateList(location);
        ValueLatticeElement newTarget = new ValueLatticeElementImpl(list);
        return l.setHeapValue(context, location, newValue).setTempsValue(context, node.getTargetName(), oldArray.join(newTarget));
    }

    private AnalysisLatticeElement analyse(ArrayWriteAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        HeapLocation location = new HeapLocationImpl(context, node);

        //Only add write error if all arrays are lists
        final AnalysisLatticeElement finalLatticeElement = latticeElement;
        Set<HeapLocation> varSet = latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations();
        boolean addError = varSet.stream()
                .map(l -> finalLatticeElement.getHeapValue(context, l).getArray())
                .allMatch(a -> a instanceof ListArrayLatticeElement);

        checkArrayAddValue(latticeElement.getValue(context), varSet, location);
        ValueLatticeElement key = latticeElement.getTempsValue(context, node.getIndexName());
        ValueLatticeElement value = latticeElement.getTempsValue(context, node.getValueName());
        latticeElement = latticeElement
                .setTempsValue(context,
                        node.getTargetName(),
                        latticeElement.getTempsValue(context, node.getValueName()))
                .setHeapValue(
                        context,
                        location,
                        value);

        HeapMapLatticeElement heap = latticeElement.getHeap(context);
        for (HeapLocation loc : latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations()) {
            for (HeapLocation loc2 : readArray(heap.getValue(loc), key, heap)) {
                latticeElement = latticeElement.joinHeapValue(context, loc2, value);
            }
        }

        for (HeapLocation loc : latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations()) {
            latticeElement = latticeElement.setHeapValue(
                    context,
                    loc,
                    writeArray(
                            latticeElement.getHeapValue(context, loc),
                            latticeElement.getTempsValue(context, node.getIndexName()),
                            location,
                            addError)
            );
        }

        return latticeElement;

    }

    private AnalysisLatticeElement analyse(
            ArrayWriteReferenceAssignmentNode node,
            AnalysisLatticeElement latticeElement,
            Context context) {

        checkArrayAddValue(latticeElement.getValue(context),
                latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations(),
                latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()).getLocations());

        latticeElement = latticeElement.setTempsValue(
                context,
                node.getTargetName(),
                latticeElement
                        .getHeap(context)
                        .getValue(
                                latticeElement
                                        .getHeapTempsValue(context, node.getValueTempHeapName())
                                        .getLocations(),
                                LatticeElement::join));

        for (HeapLocation location : latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations()) {
            latticeElement = latticeElement.setHeapValue(context, location, writeArray(
                    latticeElement.getHeapValue(context, location),
                    latticeElement.getTempsValue(context, node.getWriteArgument()),
                    latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()).getLocations()
            ));
        }

        return latticeElement;
    }


    private void checkArrayAddValue(StateLatticeElement state, Set<HeapLocation> variableLocationsSet, HeapLocation valueLocation) {
        Set<HeapLocation> set = new HashSet<>();
        set.add(valueLocation);
        checkArrayAddValue(state, variableLocationsSet, set);

    }

    private void checkArrayAddValue(StateLatticeElement state, Set<HeapLocation> variableLocationsSet, Set<HeapLocation> valueLocationSet) {
        ValueLatticeElement arrayValue = ValueLatticeElement.bottom;

        if (variableLocationsSet.stream()
                .map(state.getHeap()::getValue)
                .map(ValueLatticeElement::getArray)
                .allMatch(a -> a.equals(ArrayLatticeElement.bottom) || a.equals(ArrayLatticeElement.emptyArray))) {
            return;
        }

        for (HeapLocation l : variableLocationsSet) {
            if (arrayValue.equals(ValueLatticeElement.top)) {
                return;
            }
            ValueLatticeElement v = state.getHeap().getValue(l);
            if(!v.getString().equals(StringLatticeElement.bottom)
                    || !v.getNumber().equals(NumberLatticeElement.bottom)
                    || !v.getBoolean().equals(BooleanLatticeElement.bottom)) {
                annotator.warning("Target of write might be non-array");
            }
            if (v.getArray() instanceof ListArrayLatticeElement) {
                ValueLatticeElement newValue = state.getHeap().getValue(((ListArrayLatticeElement) v.getArray()).getLocations(), LatticeElement::join);
                arrayValue = arrayValue.join(newValue);
            }

            if (v.getArray() instanceof MapArrayLatticeElement) {

                MapArrayLatticeElement array = (MapArrayLatticeElement) v.getArray();
                HeapLocationPowerSetLatticeElement locations = array.getMap().getDomain().stream().map(array::getValue).reduce(new HeapLocationPowerSetLatticeElementImpl(), LatticeElement::join);
                ValueLatticeElement newValue = state.getHeap().getValue(locations, LatticeElement::join);
                arrayValue = arrayValue.join(newValue);
            }
        }

        ValueLatticeElement valueValue = state.getHeap().getValue(valueLocationSet, LatticeElement::join);
        checkArrayAddValue(arrayValue, valueValue);

    }


    private void checkArrayAddValue(ValueLatticeElement v1, ValueLatticeElement v2) {
        if (
                (v1.getArray().equals(ArrayLatticeElement.bottom) && !v2.getArray().equals(ArrayLatticeElement.bottom)) ||
                        (v1.getString().equals(StringLatticeElement.bottom) && !v2.getString().equals(StringLatticeElement.bottom)) ||
                        (v1.getNumber().equals(NumberLatticeElement.bottom) && !v2.getNumber().equals(NumberLatticeElement.bottom)) ||
                        (v1.getBoolean().equals(BooleanLatticeElement.bottom) && !v2.getBoolean().equals(BooleanLatticeElement.bottom)) ||
                        ((v1.getArray() instanceof ListArrayLatticeElement) && v2.getArray() instanceof MapArrayLatticeElement) ||
                        ((v2.getArray() instanceof ListArrayLatticeElement) && v1.getArray() instanceof MapArrayLatticeElement)

                ) {
            annotator.warning("Assigning value to array of different type");
        }
    }


    private AnalysisLatticeElement analyse(GlobalNode node, AnalysisLatticeElement latticeElement, Context context) {
        if (context.isEmpty()) {
            return latticeElement;
        }

        int i = -1;
        for(VariableName name : node.getVariableNames()){
            i++;
            if(!latticeElement.getGlobalsValue(context, name).getLocations().isEmpty()){
                continue;
            }
            latticeElement = latticeElement.setGlobalsValue(
                    context,
                    name,
                    new HeapLocationPowerSetLatticeElementImpl(new HeapLocationImpl(context, node, i)));

        }

        for (VariableName name : node.getVariableNames()) {
            latticeElement = latticeElement.setLocalsValue(context, name, latticeElement.getGlobalsValue(context, name));
        }

        return latticeElement;
    }


    private AnalysisLatticeElement analyse(VariableReferenceAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {

        if (context.isEmpty() || node.getVariableName().isSuperGlobal()) {
            return latticeElement
                    .setGlobalsValue(context, node.getVariableName(), latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()))
                    .setTempsValue(context, node.getTargetName(), latticeElement.getHeap(context).getValue(latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()), LatticeElement::join));
        }

        return latticeElement
                .setLocalsValue(context, node.getVariableName(), latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()))
                .setTempsValue(context, node.getTargetName(), latticeElement.getHeap(context).getValue(latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()), LatticeElement::join));
    }

    private Set<IndexLatticeElement> generateArrayIndices(ValueLatticeElement element) {
        Set<IndexLatticeElement> indexSet = new HashSet<>();
        indexSet.add(element.getBoolean().toArrayIndex());
        indexSet.add(element.getNull().toArrayIndex());
        indexSet.add(element.getString().toArrayIndex());
        indexSet.add(element.getNumber().toArrayIndex());

        indexSet.removeIf(i1 -> indexSet.stream().anyMatch(i2 -> i1 != i2 && i1.containedIn(i2)));

        return indexSet;
    }

    ValueLatticeElement writeArray(ValueLatticeElement value, ValueLatticeElement key, HeapLocation location, boolean addError) {
        Set<HeapLocation> set = new HashSet<>();
        set.add(location);
        return writeArray(value, key, set, addError);

    }
    ValueLatticeElement writeArray(ValueLatticeElement value, ValueLatticeElement key, HeapLocation location) {
        return writeArray(value, key, location, true);
    }

    ValueLatticeElement writeArray(ValueLatticeElement value, ValueLatticeElement key, Set<HeapLocation> locations) {
        return writeArray(value, key, locations, true);
    }

    ValueLatticeElement writeArray(ValueLatticeElement value, ValueLatticeElement key, Set<HeapLocation> locations, boolean addError) {
        Set<IndexLatticeElement> indices = generateArrayIndices(key);
        MapArrayLatticeElement map = ArrayLatticeElement.generateMap();
        for (IndexLatticeElement i : indices) {
            map = map.addValue(i, i1 -> new HeapLocationPowerSetLatticeElementImpl(locations));
        }
        ArrayLatticeElement array = value.getArray();

        if (array.equals(ArrayLatticeElement.top)) {
            return value;
        }

        if (array instanceof ListArrayLatticeElement) {
            if (indices.stream().anyMatch(i -> i instanceof StringIndexLatticeElement)) {
                if(indices.stream().allMatch(i -> i instanceof StringIndexLatticeElement) && addError){
                    annotator.error("Array write is with string index on list");
                } else if (addError) {
                    annotator.warning("Array write may be with string index on list");
                }
                return value.setArray(ArrayLatticeElement.generateMap(IndexLatticeElement.top, ((ListArrayLatticeElement) array).getLocations().getLocations()).join(map));
            }
            return value.setArray(((ListArrayLatticeElement) array).addLocations(locations));
        }

        if (array instanceof MapArrayLatticeElement) {
            return value.setArray(array.join(map));
        }

        if (indices.stream().anyMatch(i -> i instanceof StringIndexLatticeElement)) {
            return value.setArray(map);
        }
        return value.setArray(ArrayLatticeElement.generateList(locations));

    }


    private AnalysisLatticeElement analyse(UnaryOperationNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement value = l.getTempsValue(c, n.getOperandName());
        if (n.getOperator() == UnaryOperator.NEGATION) {
            return l.setTempsValue(c, n.getTargetName(), target -> new ValueLatticeElementImpl(value.toBoolean().negate()));
        }
        if (n.getOperator() == UnaryOperator.MINUS) {
            return l.setTempsValue(c, n.getTargetName(), target -> new ValueLatticeElementImpl(value.toNumber().minus()));
        }

        return l;
    }

    private AnalysisLatticeElement analyse(ShortCircuitBinaryOperationNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement
                targetValue;
        BooleanLatticeElement
                leftValue = latticeElement.getTempsValue(context, node.getLeftOperandName()).getBoolean(),
                rightValue = latticeElement.getTempsValue(context, node.getRightOperandName()).getBoolean();

        switch (node.getOperator()) {
            case AND:
                if (leftValue.equals(BooleanLatticeElement.boolFalse) || rightValue.equals(BooleanLatticeElement.boolFalse)) {
                    targetValue = new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);
                } else if (leftValue.equals(BooleanLatticeElement.boolTrue) && rightValue.equals(BooleanLatticeElement.boolTrue)) {
                    targetValue = new ValueLatticeElementImpl(BooleanLatticeElement.boolTrue);
                } else {
                    targetValue = new ValueLatticeElementImpl(BooleanLatticeElement.top);
                }
                break;
            case OR:
                if (leftValue.equals(BooleanLatticeElement.boolFalse) && rightValue.equals(BooleanLatticeElement.boolFalse)) {
                    targetValue = new ValueLatticeElementImpl(BooleanLatticeElement.boolFalse);
                } else if (leftValue.equals(BooleanLatticeElement.boolTrue) || rightValue.equals(BooleanLatticeElement.boolTrue)) {
                    targetValue = new ValueLatticeElementImpl(BooleanLatticeElement.boolTrue);
                } else {
                    targetValue = new ValueLatticeElementImpl(BooleanLatticeElement.top);
                }
                break;
            default:
                return latticeElement;
        }

        latticeElement = latticeElement.setTempsValue(context, node.getTargetName(), targetValue);

        return latticeElement;
    }


    private AnalysisLatticeElement analyse(ResultNode resultNode, AnalysisLatticeElement resultLattice, AnalysisLatticeElement callLattice, Context context) {
        CallArgument argument = resultNode.getCallArgument();
        AnalysisLatticeElement inputLattice = resultLattice;
        final Context exitNodeContext = context.addNode(resultNode.getCallNode());

        //Taking globals from prev. lattice and restoring locals from call lattice.
        resultLattice = resultLattice.setLocals(context, callLattice.getLocals(context)); //Setting the locals
        resultLattice = resultLattice.setTemps(context, callLattice.getTemps(context)); //Setting the stack
        resultLattice = resultLattice.setHeapTemps(context, callLattice.getHeapTemps(context)); //Setting the stack
        resultLattice = resultLattice.setGlobals(context, resultLattice.getGlobals(exitNodeContext)); //Setting the globals
        resultLattice = resultLattice.setHeap(context, resultLattice.getHeap(exitNodeContext)); //Setting the heap

        if (resultNode.getExitNode().getCallArguments().length == 0) {
            // If void method, it returns null
            if (argument instanceof TemporaryVariableCallArgument) {
                resultLattice = resultLattice.setTempsValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        (t) -> new ValueLatticeElementImpl(NullLatticeElement.top));
            }

            return resultLattice;
        }

        if (argument instanceof TemporaryHeapVariableCallArgument) {
            resultLattice = resultLattice.setHeapTempsValue(context, ((TemporaryHeapVariableCallArgument) argument).getArgument(), new HeapLocationPowerSetLatticeElementImpl());
        } else if (argument instanceof TemporaryVariableCallArgument) {
            resultLattice = resultLattice.setTempsValue(context, ((TemporaryVariableCallArgument) argument).getArgument(), new ValueLatticeElementImpl());
        }
        int i = 0;
        for (CallArgument exitArgument : resultNode.getExitNode().getCallArguments()) {
            if (argument instanceof TemporaryHeapVariableCallArgument && exitArgument instanceof TemporaryHeapVariableCallArgument) {
                //If alias method and alias return, then parse locations
                resultLattice = resultLattice.joinHeapTempsValue(context,
                        ((TemporaryHeapVariableCallArgument) argument).getArgument(),
                        inputLattice.getHeapTempsValue(exitNodeContext, ((TemporaryHeapVariableCallArgument) exitArgument).getArgument()));

            } else if (argument instanceof TemporaryHeapVariableCallArgument && exitArgument instanceof TemporaryVariableCallArgument) {
                //If alias method and stack variable. return, then create location with stack value.
                HeapLocation location = new HeapLocationImpl(context, resultNode, i);
                resultLattice = resultLattice.setHeapValue(
                        context,
                        location,
                        inputLattice.getTempsValue(exitNodeContext, ((TemporaryVariableCallArgument) exitArgument).getArgument()));
                resultLattice = resultLattice.joinHeapTempsValue(
                        context,
                        ((TemporaryHeapVariableCallArgument) argument).getArgument(),
                        location);


            } else if (argument instanceof TemporaryVariableCallArgument && exitArgument instanceof TemporaryHeapVariableCallArgument) {
                //If method and location return, then stack variable with location values.
                final TemporaryHeapVariableCallArgument finalExit = (TemporaryHeapVariableCallArgument) exitArgument;
                resultLattice = resultLattice.joinTempsValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        inputLattice.getHeap(exitNodeContext).getValue(inputLattice.getHeapTempsValue(exitNodeContext, finalExit.getArgument()), LatticeElement::join));


            } else if (argument instanceof TemporaryVariableCallArgument && exitArgument instanceof TemporaryVariableCallArgument) {
                //If method and stack variable return, then "rename" stack variable.
                final TemporaryVariableCallArgument finalExit = (TemporaryVariableCallArgument) exitArgument;
                resultLattice = resultLattice.joinTempsValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        inputLattice.getTempsValue(exitNodeContext, finalExit.getArgument()));

            }
            i++;
        }

        return resultLattice;
    }

    private AnalysisLatticeElement analyse(VariableReadNode node, AnalysisLatticeElement latticeElement, Context context) {
        MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> scope;
        if (context.isEmpty() || node.getVariableName().isSuperGlobal()) {
            scope = latticeElement.getGlobals(context);
        } else {
            scope = latticeElement.getLocals(context);
        }
        ValueLatticeElement value;
        if (scope.getValue(node.getVariableName()).getLocations().isEmpty()) {
            value = new ValueLatticeElementImpl(NullLatticeElement.top);
        } else {
            value = latticeElement.getHeap(context).getValue(scope.getValue(node.getVariableName()), LatticeElement::join);
        }

        return latticeElement.setTempsValue(context, node.getTargetName(), value);

    }

    private AnalysisLatticeElement analyse(ReadConstNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement newTarget;
        Object constant = n.getConstant().getValue();
        if (n.getConstant() instanceof StringConstantImpl)
            newTarget = new ValueLatticeElementImpl(StringLatticeElement.generateStringLatticeElement((String) constant));
        else if (n.getConstant() instanceof NumberConstantImpl)
            newTarget = new ValueLatticeElementImpl(NumberLatticeElement.generateNumberLatticeElement((Number) constant));
        else if (n.getConstant() instanceof BooleanConstantImpl)
            newTarget = new ValueLatticeElementImpl(BooleanLatticeElement.generateBooleanLatticeElement((Boolean) constant));
        else if (n.getConstant() instanceof NullConstantImpl)
            newTarget = new ValueLatticeElementImpl(NullLatticeElement.top);
        else
            newTarget = new ValueLatticeElementImpl();

        return l.setTempsValue(c, n.getTargetName(), (temp) -> l.getTempsValue(c, n.getTargetName()).join(newTarget));
    }

    private AnalysisLatticeElement analyse(IncrementDecrementOperationStackOperationNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement value, targetValue, locationValue = latticeElement.getHeap(context).getValue(
                latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()),
                LatticeElement::join);
        //Notice that PHP does not coerce when value not a number (inc,dec)
        switch (node.getOperation()) {
            case PRE_INCREMENT:
                value = targetValue = new ValueLatticeElementImpl(locationValue.toNumber().increment());
                break;
            case POST_INCREMENT:
                targetValue = locationValue;
                value = new ValueLatticeElementImpl(locationValue.toNumber().increment());
                break;
            case PRE_DECREMENT:
                value = targetValue = new ValueLatticeElementImpl(locationValue.toNumber().decrement());
                break;
            case POST_DECREMENT:
                targetValue = locationValue;
                value = new ValueLatticeElementImpl(locationValue.toNumber().decrement());
                break;
            default:
                return latticeElement;
        }

        latticeElement = latticeElement
                .setTempsValue(context, node.getTargetName(), targetValue)
                .setHeap(context, updateLocations(latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()), latticeElement.getHeap(context), value));
        return latticeElement;
    }


    private AnalysisLatticeElement analyse(CallNode node, AnalysisLatticeElement lattice, Context context) {

        if (node.getFunctionGraph() instanceof LibraryFunctionGraph) {
            return lattice;
        }

        Context newContext = context.addNode(node);

        VariableName[] argumentNames = node.getFunctionGraph().getArgumentNames();
        CallArgument[] callArguments = node.getCallArguments();

        lattice = lattice.setHeap(newContext, lattice.getHeap(context)); //Setting heap
        lattice = lattice.setGlobals(newContext, lattice.getGlobals(context)); //Setting globals

        for (int i = 0; i < argumentNames.length; i++) {
            CallArgument callArgument = callArguments[i];
            //Setting arguments in local scope
            if (callArgument instanceof TemporaryHeapVariableCallArgument) {
                lattice = lattice.setLocalsValue(
                        newContext,
                        argumentNames[i],
                        lattice.getHeapTempsValue(context, ((TemporaryHeapVariableCallArgument) callArgument).getArgument()));
            } else if (callArgument instanceof TemporaryVariableCallArgument) {
                lattice = lattice.setLocalsValue(
                        newContext,
                        node.getFunctionGraph().getEntryNode(),
                        i,
                        argumentNames[i],
                        lattice.getTempsValue(context, ((TemporaryVariableCallArgument) callArgument).getArgument()));
            }

        }

        return lattice;
    }

    private AnalysisLatticeElement analyse(BinaryOperationNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement
                leftValue = latticeElement.getTempsValue(context, node.getLeftOperandName()),
                rightValue = latticeElement.getTempsValue(context, node.getRightOperandName()),
                targetValue;
        switch (node.getOperator()) {
            case ADDITION:
                targetValue = new ValueLatticeElementImpl(leftValue.toNumber().add(rightValue.toNumber()));
                break;
            case SUBTRACTION:
                targetValue = new ValueLatticeElementImpl(leftValue.toNumber().subtract(rightValue.toNumber()));
                break;
            case MULTIPLICATION:
                targetValue = new ValueLatticeElementImpl(leftValue.toNumber().multiply(rightValue.toNumber()));
                break;
            case DIVISION:
                targetValue = leftValue.toNumber().divide(rightValue.toNumber());
                break;
            case MODULO:
                targetValue = leftValue.toNumber().modulo(rightValue.toNumber());
                break;
            case EXPONENTIATION:
                targetValue = new ValueLatticeElementImpl(leftValue.toNumber().exponent(rightValue.toNumber()));
                break;
            case EQUAL:
                targetValue = new ValueLatticeElementImpl(leftValue.equalOperation(rightValue));
                break;
            case IDENTICAL:
                targetValue = new ValueLatticeElementImpl(leftValue.identical(rightValue));
                break;
            case NOT_EQUAL:
                targetValue = new ValueLatticeElementImpl(leftValue.notEqualOperation(rightValue));
                break;
            case NOT_IDENTICAL:
                targetValue = new ValueLatticeElementImpl(leftValue.notIdentical(rightValue));
                break;
            case GREATER_THAN:
                targetValue = new ValueLatticeElementImpl(leftValue.greaterThan(rightValue));
                break;
            case LESS_THAN:
                targetValue = new ValueLatticeElementImpl(leftValue.lessThan(rightValue));
                break;
            case GREATER_THAN_OR_EQ:
                targetValue = new ValueLatticeElementImpl(leftValue.greaterThanOrEqual(rightValue));
                break;
            case LESS_THAN_OR_EQ:
                targetValue = new ValueLatticeElementImpl(leftValue.lessThanOrEqual(rightValue));
                break;
            case CONCATENATION:
                targetValue = new ValueLatticeElementImpl(leftValue.toStringLattice().concat(rightValue.toStringLattice()));
                break;
            default:
                //Other operations are covered by other methods
                return latticeElement;
        }

        latticeElement = latticeElement.setTempsValue(context, node.getTargetName(), targetValue);

        return latticeElement;
    }


    private AnalysisLatticeElement analyse(VariableAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {

        if (context.isEmpty() || node.getVariableName().isSuperGlobal()) {
            Pair<MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement>, HeapMapLatticeElement> pair =
                    writeVar(
                            context,
                            node,
                            node.getVariableName(),
                            latticeElement.getGlobals(context),
                            latticeElement.getHeap(context),
                            latticeElement.getTempsValue(context, node.getValueName()));
            return latticeElement
                    .setGlobals(context, pair.getLeft())
                    .setHeap(context, pair.getRight())
                    .setTempsValue(context, node.getTargetName(), latticeElement.getTempsValue(context, node.getValueName()));

        }

        Pair<MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement>, HeapMapLatticeElement> pair =
                writeVar(
                        context,
                        node,
                        node.getVariableName(),
                        latticeElement.getLocals(context),
                        latticeElement.getHeap(context),
                        latticeElement.getTempsValue(context, node.getValueName()));
        return latticeElement
                .setLocals(context, pair.getLeft())
                .setHeap(context, pair.getRight())
                .setTempsValue(context, node.getTargetName(), latticeElement.getTempsValue(context, node.getValueName()));

    }

    private HeapMapLatticeElement updateLocations(HeapLocationPowerSetLatticeElement variableLocations, HeapMapLatticeElement heap, ValueLatticeElement value) {
        return updateLocations(variableLocations.getLocations(), heap, value);
    }

    private HeapMapLatticeElement updateLocations(Set<HeapLocation> variableLocations, HeapMapLatticeElement heap, ValueLatticeElement value) {
        //Soft update on multiple locations
        for (HeapLocation location : variableLocations) {
            final HeapMapLatticeElement finalHeap = heap;
            heap = heap.addValue(location, (l) -> finalHeap.getValue(l).join(value));
        }

        return heap;
    }

    private AnalysisLatticeElement analyse(ArrayWriteStackOperationNode node, AnalysisLatticeElement latticeElement, Context context) {
        HeapLocation location = new HeapLocationImpl(context, node);
        ArrayLatticeElement array = latticeElement.getTempsValue(context, node.getTargetName()).getArray();
        if (array instanceof ListArrayLatticeElement) {
            checkArrayAddValue(
                    latticeElement
                            .getHeap(context)
                            .getValue(((ListArrayLatticeElement) array).getLocations(), LatticeElement::join),
                    latticeElement
                            .getTempsValue(context, node.getValueName()));
        }

        return latticeElement
                .setHeapValue(
                        context,
                        location,
                        latticeElement.getTempsValue(context, node.getValueName()))
                .setTempsValue(
                        context,
                        node.getTargetName(),
                        writeArray(
                                latticeElement.getTempsValue(context, node.getTargetName()),
                                latticeElement.getTempsValue(context, node.getKeyName()),
                                location));

    }


    private Set<HeapLocation> readArray(ValueLatticeElement value, ValueLatticeElement key, MapLatticeElement<HeapLocation, ValueLatticeElement> heap) {
        ArrayLatticeElement array = value.getArray();
        if (array.equals(ArrayLatticeElement.top)) {
            return heap.getDomain();
        }
        if (array instanceof ListArrayLatticeElement) {
            return ((ListArrayLatticeElement) array).getLocations().getLocations();
        }
        if (array instanceof MapArrayLatticeElement) {
            MapArrayLatticeElement map = (MapArrayLatticeElement) array;
            return generateArrayIndices(key).stream().map(map::getValue).reduce(new HeapLocationPowerSetLatticeElementImpl(), LatticeElement::join).getLocations();
        }

        return new HashSet<>();

    }

    private AnalysisLatticeElement analyse(ArrayReadStackOperationNode node, AnalysisLatticeElement latticeElement, Context context) {

        Set<HeapLocation> locations = readArray(
                latticeElement.getTempsValue(context, node.getArrayName()),
                latticeElement.getTempsValue(context, node.getIndexName()),
                latticeElement.getHeap(context));

        ValueLatticeElement value = new ValueLatticeElementImpl(NullLatticeElement.top).join(latticeElement.getHeap(context).getValue(locations, LatticeElement::join));
        return latticeElement.setTempsValue(context, node.getTargetName(), value);

    }

    private AnalysisLatticeElement analyse(ArrayReadLocationSetNode node, AnalysisLatticeElement latticeElement, Context context) {

        HeapLocation location = new HeapLocationImpl(context, node);

        Set<HeapLocation> targetSet = new HashSet<>();

        for (HeapLocation loc : latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()).getLocations()) {
            Set<HeapLocation> locations = readArray(
                    latticeElement.getHeapValue(context, loc),
                    latticeElement.getTempsValue(context, node.getIndexName()),
                    latticeElement.getHeap(context));
            if (locations.isEmpty()) {
                latticeElement =
                        latticeElement.setHeapValue(
                                context,
                                loc,
                                writeArray(
                                        latticeElement.getHeapValue(context, loc),
                                        latticeElement.getTempsValue(context, node.getIndexName()),
                                        location));
                if (!targetSet.contains(location)) {
                    targetSet.add(location);
                    latticeElement.setHeapValue(context, location, new ValueLatticeElementImpl(NullLatticeElement.top));
                }
            } else {
                targetSet.addAll(locations);
            }
        }
        return latticeElement.setHeapTempsValue(context, node.getTargetTempHeapName(), targetSet);

    }


    private void allCheckArray(AnalysisLatticeElement latticeElement, Context context, TemporaryHeapVariableName locationSet, Predicate<ArrayLatticeElement> predicate, Action consumer) {
        if (latticeElement.getHeapTempsValue(context, locationSet).getLocations().stream().map(l -> latticeElement.getHeapValue(context, l).getArray()).allMatch(predicate)) {
            consumer.act();
        }

    }


    private void anyCheckArray(AnalysisLatticeElement latticeElement, Context context, TemporaryHeapVariableName locationSet, Predicate<ArrayLatticeElement> predicate, Action consumer) {
        if (latticeElement.getHeapTempsValue(context, locationSet).getLocations().stream().map(l -> latticeElement.getHeapValue(context, l).getArray()).anyMatch(predicate)) {
            consumer.act();
        }

    }

    private AnalysisLatticeElement analyse(ArrayInitStackOperationNode n, AnalysisLatticeElement l, Context c) {
        return l.setTempsValue(c, n.getTargetName(), (name) -> new ValueLatticeElementImpl(ArrayLatticeElement.emptyArray));
    }

    private AnalysisLatticeElement analyse(VariableReadLocationSetNode node, AnalysisLatticeElement latticeElement, Context context) {
        if (context.isEmpty() || node.getVariableName().isSuperGlobal()) {
            MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> scope = initializeVariable(node, context, node.getVariableName(), latticeElement.getGlobals(context));
            return latticeElement
                    .setGlobals(context, scope)
                    .setHeapTempsValue(context, node.getTargetTempHeapName(), scope.getValue(node.getVariableName()));
        }
        MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> scope = initializeVariable(node, context, node.getVariableName(), latticeElement.getLocals(context));
        return latticeElement
                .setLocals(context, scope)
                .setHeapTempsValue(context, node.getTargetTempHeapName(), scope.getValue(node.getVariableName()));

    } 

    private MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> initializeVariable(
            VariableReadLocationSetNode node,
            Context context,
            VariableName variableName,
            MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> scope) {
        if (scope.getValue(variableName).getLocations().isEmpty()) {
            return scope.addValue(variableName, v -> new HeapLocationPowerSetLatticeElementImpl(new HeapLocationImpl(context, node)));
        }
        return scope;
    }


    private Pair<MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement>, HeapMapLatticeElement> writeVar(
            Context context,
            Node node,
            VariableName name,
            MapLatticeElement<VariableName, HeapLocationPowerSetLatticeElement> scope,
            HeapMapLatticeElement heap,
            ValueLatticeElement value) {
        HeapLocation location = new HeapLocationImpl(context, node);
        if(scope.getValue(name).getLocations().isEmpty()){
            return  new PairImpl<>(
                    scope.addValue(
                            name,
                            n -> new HeapLocationPowerSetLatticeElementImpl(location)),
                    heap.addValue(
                            location,
                            l -> value
                    ));
        }
        return new PairImpl<>(scope, updateLocations(scope.getValue(name), heap, value));


    }

    private interface Action {

        void act();
    }
}
