package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.*;
import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.cfg.graph.LibraryFunctionGraph;
import dk.au.cs.tapas.cfg.graph.NumberConstantImpl;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.*;
import dk.au.cs.tapas.lattice.element.*;

import java.util.*;
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
        AnalysisLatticeElement lattice = new AnalysisLatticeElementImpl();
        // TODO: init as maps
        for (VariableName name : VariableName.superGlobals) {
            lattice = lattice.setGlobalsValue(new ContextImpl(), graph.getEntryNode(), name, new ValueLatticeElementImpl(ArrayLatticeElement.top));
        }
        return lattice;
    }

    @Override
    public AnalysisLatticeElement analyse(AnalysisTarget target, AnalysisLatticeElement latticeElement) {
        Node node = target.getNode();
        annotator.setNode(node);
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
        checkArrayAddValue(latticeElement.getValue(context), latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations(), listLocation);

        for (HeapLocation location : latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations()) {
            latticeElement = latticeElement.joinHeapValue(context, location, new ValueLatticeElementImpl(ArrayLatticeElement.generateList(listLocation)));
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
        checkArrayAddValue(latticeElement.getValue(context),
                latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations(),
                latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()).getLocations());

        for (HeapLocation location : latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations()) {
            latticeElement = latticeElement.joinHeapValue(context, location, listValue);
        }
        return latticeElement;
    }

    private AnalysisLatticeElement analyse(ArrayAppendLocationSetNode node, AnalysisLatticeElement latticeElement, Context context) {

        allCheckArray(latticeElement, context, node.getValueTempHeapName(), a -> a instanceof MapArrayLatticeElement, () -> annotator.error("Appending on map"));
        latticeElement = latticeElement.setHeapTempsValue(context, node.getTargetTempHeapName(), new HeapLocationPowerSetLatticeElementImpl());
        //TODO should we clear?
        for (HeapLocation loc : latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()).getLocations()) {
            HeapLocation newLoc = new HeapLocationImpl(context, node);
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

        HeapLocation location = new HeapLocationImpl(context,node);
        ArrayLatticeElement list = ArrayLatticeElement.generateList(location);
        ValueLatticeElement newTarget = new ValueLatticeElementImpl(list);
        return l.setHeapValue(context, location, newValue).setTempsValue(context, node.getTargetName(), oldArray.join(newTarget));
    }

    private AnalysisLatticeElement analyse(ArrayWriteAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        //Setting target
        latticeElement = latticeElement.setTempsValue(context, node.getTargetName(), latticeElement.getTempsValue(context, node.getValueName()));
        //For each possible location
        HeapLocation valueLocation = new HeapLocationImpl(context, node);
        latticeElement = latticeElement.setHeapValue(context, valueLocation, latticeElement.getTempsValue(context, node.getValueName()));

        final AnalysisLatticeElement finalLatticeElement = latticeElement;
        //Only add write error if all arrays are lists
        Set<HeapLocation> varSet = latticeElement.getHeapTempsValue(context,node.getVariableTempHeapName()).getLocations();
        boolean addError = varSet
                .stream()
                .map(l -> finalLatticeElement.getHeapValue(context, l).getArray()).allMatch(a -> a instanceof ListArrayLatticeElement);

        checkArrayAddValue(latticeElement.getValue(context), varSet, valueLocation);

        for (HeapLocation location : varSet) {
            ValueLatticeElement value = latticeElement.getHeapValue(context, location);
            ArrayLatticeElement array = writeArray(
                    value.getArray(),
                    valueLocation,
                    generateArrayIndices(
                            latticeElement.getTempsValue(
                                    context,
                                    node.getIndexName())), addError);

            //If you "array write" to something that is initialized, but not an array, you get a warning and the variable is unchanged.
            latticeElement = latticeElement.setHeapValue(context, location, value.setArray(array));

        }

        return latticeElement;
    }

    private AnalysisLatticeElement analyse(
            ArrayWriteReferenceAssignmentNode node,
            AnalysisLatticeElement latticeElement, Context context) {

        Set<HeapLocation> valueLocations = latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()).getLocations();
        Set<HeapLocation> varLocations  = latticeElement.getHeapTempsValue(context, node.getVariableTempHeapName()).getLocations();
        latticeElement = latticeElement.setTempsValue(context, node.getTargetName(), latticeElement.getHeap(context).getValue(valueLocations, LatticeElement::join));
        //For each possible location
        for (HeapLocation location : varLocations) {
            ValueLatticeElement value = latticeElement.getHeapValue(context, location);
            ArrayLatticeElement array = writeArray(
                    value.getArray(),
                    valueLocations,
                    generateArrayIndices(
                            latticeElement.getTempsValue(
                                    context,
                                    node.getWriteArgument())));

            //If you "array write" to something that is initialized, but not an array, you get a warning and the variable is unchanged.
            latticeElement = latticeElement.setHeapValue(context, location, value.setArray(array));

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
        for (HeapLocation l : variableLocationsSet) {
            if (arrayValue.equals(ValueLatticeElement.top)) {
                return;
            }
            ArrayLatticeElement v = state.getHeap().getValue(l).getArray();
            if (v instanceof ListArrayLatticeElement) {
                ValueLatticeElement newValue = state.getHeap().getValue(((ListArrayLatticeElement) v).getLocations(), LatticeElement::join);
                arrayValue = arrayValue.join(newValue);
            } else {
                return;
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
            annotator.warning("Assigning value to list of different type");
        }
    }


    private AnalysisLatticeElement analyse(GlobalNode node, AnalysisLatticeElement latticeElement, Context context) {
        if (context.isEmpty()) {
            return latticeElement;
        }

        for (VariableName name : node.getVariableNames()) {
            latticeElement = latticeElement.setLocalsValue(context, name, latticeElement.getGlobalsValue(context, name));
        }

        return latticeElement;
    }


    private AnalysisLatticeElement analyse(VariableReferenceAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        Set<HeapLocation> valueLocations = latticeElement.getHeapTempsValue(context,node.getValueTempHeapName()).getLocations();
                latticeElement = latticeElement.setTempsValue(context, node.getTargetName(), latticeElement.getHeap(context).getValue(valueLocations, LatticeElement::join));
        latticeElement = updateVariable(node.getVariableName(), context, latticeElement, m -> new HeapLocationPowerSetLatticeElementImpl(valueLocations));

        return latticeElement;
    }

    private Set<IndexLatticeElement> generateArrayIndices(ValueLatticeElement element) {
        Set<IndexLatticeElement> list = new HashSet<>();
        list.add(element.getBoolean().toArrayIndex());
        list.add(element.getNull().toArrayIndex());
        list.add(element.getString().toArrayIndex());
        list.add(element.getNumber().toArrayIndex());
        return list;
    }

    ArrayLatticeElement writeArray(ArrayLatticeElement array, HeapLocation valueLocation, Collection<IndexLatticeElement> arrayIndices) {
        return writeArray(array, valueLocation, arrayIndices, true);
    }

    ArrayLatticeElement writeArray(ArrayLatticeElement array, HeapLocation valueLocation, Collection<IndexLatticeElement> arrayIndices, boolean addError) {
        Set<HeapLocation> set = new HashSet<>();
        set.add(valueLocation);
        return writeArray(array, set, arrayIndices, addError);
    }


    ArrayLatticeElement writeArray(ArrayLatticeElement array, Set<HeapLocation> valueLocationSet, Collection<IndexLatticeElement> arrayIndices) {
        return writeArray(array, valueLocationSet, arrayIndices, true);
    }

    ArrayLatticeElement writeArray(ArrayLatticeElement array, Set<HeapLocation> valueLocationSet, Collection<IndexLatticeElement> arrayIndices, boolean addError) {


        //If location is top array, do nothing
        if (array.equals(ArrayLatticeElement.top)) {
            return array;
        }
        if (array.equals(ArrayLatticeElement.bottom) || array.equals(ArrayLatticeElement.emptyArray)) {
            //For every possible index
            for (IndexLatticeElement index : arrayIndices) {
                //For monotonicity, we need to make every initializing write a map.
                array = array.join(ArrayLatticeElement.generateMap(index, valueLocationSet));

            }
        } else if (array instanceof ListArrayLatticeElement) {
            if (arrayIndices.stream().allMatch(i -> i instanceof StringIndexLatticeElement || i.equals(IndexLatticeElement.bottom))) {
                if (addError) {
                    annotator.error("Array write with string index on list");
                }
                //Needs to be monotone, so will need to convert to map
                array = ArrayLatticeElement
                        .generateMap(IndexLatticeElement.top, ((ListArrayLatticeElement) array).getLocations().getLocations());
                return writeArray(array, valueLocationSet, arrayIndices);
            }
            array = ((ListArrayLatticeElement) array).addLocations(valueLocationSet);

        } else if (array instanceof MapArrayLatticeElement) {
            for (IndexLatticeElement index : arrayIndices) {
                array = array.join(ArrayLatticeElement.generateMap(index, valueLocationSet));
            }

        }
        return array;
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

        if(argument instanceof TemporaryHeapVariableCallArgument){
            resultLattice = resultLattice.setHeapTempsValue(context, ((TemporaryHeapVariableCallArgument) argument).getArgument(), new HeapLocationPowerSetLatticeElementImpl());
        } else if ( argument instanceof  TemporaryVariableCallArgument){
            resultLattice = resultLattice.setTempsValue(context, ((TemporaryVariableCallArgument) argument).getArgument(), new ValueLatticeElementImpl());
        }


        if (argument instanceof TemporaryVariableCallArgument) {
            //Clearing stack variable before iteration. Just in case
            resultLattice = resultLattice.setTempsValue(context, ((TemporaryVariableCallArgument) argument).getArgument(), new ValueLatticeElementImpl());
        }
        for (CallArgument exitArgument : resultNode.getExitNode().getCallArguments()) {
            if (argument instanceof TemporaryHeapVariableCallArgument && exitArgument instanceof TemporaryHeapVariableCallArgument) {
                //If alias method and alias return, then parse locations
                resultLattice = resultLattice.joinHeapTempsValue(context,
                        ((TemporaryHeapVariableCallArgument) argument).getArgument(),
                        inputLattice.getHeapTempsValue(context, ((TemporaryHeapVariableCallArgument) exitArgument).getArgument()));
                //TODO set or join?

            } else if (argument instanceof TemporaryHeapVariableCallArgument && exitArgument instanceof TemporaryVariableCallArgument) {
                //If alias method and stack variable. return, then create location with stack value.
                HeapLocation location = new HeapLocationImpl(context, resultNode);
                resultLattice = resultLattice.setHeapValue(
                        context,
                        location,
                        inputLattice.getTempsValue(exitNodeContext, ((TemporaryVariableCallArgument) exitArgument).getArgument()));
                resultLattice = resultLattice.joinHeapTempsValue(
                        context,
                        ((TemporaryHeapVariableCallArgument) argument).getArgument(),
                        location);
                //TODO set or join?


            } else if (argument instanceof TemporaryVariableCallArgument && exitArgument instanceof TemporaryHeapVariableCallArgument) {
                //If method and location return, then stack variable with location values.
                final TemporaryHeapVariableCallArgument finalExit = (TemporaryHeapVariableCallArgument) exitArgument;
                resultLattice = resultLattice.joinTempsValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        inputLattice.getHeap(exitNodeContext).getValue(inputLattice.getHeapTempsValue(context, finalExit.getArgument()), LatticeElement::join));
                //TODO set or join?


            } else if (argument instanceof TemporaryVariableCallArgument && exitArgument instanceof TemporaryVariableCallArgument) {
                //If method and stack variable return, then "rename" stack variable.
                final TemporaryVariableCallArgument finalExit = (TemporaryVariableCallArgument) exitArgument;
                resultLattice = resultLattice.joinTempsValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        inputLattice.getTempsValue(exitNodeContext, finalExit.getArgument()));
                //TODO set or join?

            }

        }

        return resultLattice;
    }

    private AnalysisLatticeElement analyse(VariableReadNode n, AnalysisLatticeElement l, Context c) {
        HeapLocationPowerSetLatticeElement locations = getVariableLocation(n.getVariableName(), c, l);
        //Reading the joint value from heap to stack
        return l.setTempsValue(c, n.getTargetName(), name -> l.getHeap(c).getValue(locations.getLocations(), LatticeElement::join));
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
                latticeElement.getHeapTempsValue(context,node.getValueTempHeapName()),
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

        latticeElement = latticeElement.setTempsValue(context, node.getTargetName(), targetValue);
        latticeElement = updateLocations(latticeElement, context, node.getValueTempHeapName(), value);
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
                        node,
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
        ValueLatticeElement value = latticeElement.getTempsValue(context, node.getValueName());
        HeapLocationPowerSetLatticeElement variableLocations = getVariableLocation(node.getVariableName(), context, latticeElement);

        latticeElement = updateLocations(latticeElement, context, variableLocations.getLocations(), value);

        //Remember to update target stack
        latticeElement = latticeElement.setTempsValue(context, node.getTargetName(), value);


        return latticeElement;
    }

    private AnalysisLatticeElement updateLocations(AnalysisLatticeElement latticeElement, Context context, TemporaryHeapVariableName variableName, ValueLatticeElement value) {
        return updateLocations(latticeElement, context, latticeElement.getHeapTempsValue(context, variableName).getLocations(), value);
    }

    private AnalysisLatticeElement updateLocations(AnalysisLatticeElement latticeElement, Context context, Set<HeapLocation> variableLocations, ValueLatticeElement value) {
        if (variableLocations.size() == 1) {
            //Hard update on single heap location
            latticeElement = latticeElement.setHeapValue(
                    context,
                    variableLocations.iterator().next(),
                    value);
        } else {
            //Soft update on multiple locations
            for (HeapLocation location : variableLocations) {
                latticeElement = latticeElement.joinHeapValue(context, location, value);
            }
        }
        return latticeElement;
    }

    private AnalysisLatticeElement analyse(ArrayWriteStackOperationNode node, AnalysisLatticeElement latticeElement, Context context) {

        ValueLatticeElement
                arrayValue = latticeElement.getTempsValue(context, node.getTargetName()),
                entryValue = latticeElement.getTempsValue(context, node.getValueName()),
                keyValue = latticeElement.getTempsValue(context, node.getKeyName());
        ArrayLatticeElement array = arrayValue.getArray();
        HeapLocation newLocation = new HeapLocationImpl(context, node);

        if (array instanceof ListArrayLatticeElement) {
            checkArrayAddValue(latticeElement.getHeap(context).getValue(((ListArrayLatticeElement) array).getLocations(), LatticeElement::join), entryValue);
        }

        array = writeArray(array, newLocation, generateArrayIndices(keyValue));

        return latticeElement
                .setTempsValue(context, node.getTargetName(), arrayValue.setArray(array)) //Update the new stack value
                .setHeapValue(context, newLocation, entryValue); //Add the entry to the heap
    }

    private AnalysisLatticeElement analyse(ArrayReadStackOperationNode n, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement array = latticeElement.getTempsValue(context, n.getArrayName());
        ValueLatticeElement value;
        Set<IndexLatticeElement> indices = generateArrayIndices(latticeElement.getTempsValue(context, n.getIndexName()));
        if (array.getArray() instanceof MapArrayLatticeElement) {
            MapArrayLatticeElement map = (MapArrayLatticeElement) array.getArray();
            Set<HeapLocation> locations = new HashSet<>();
            for (IndexLatticeElement arrayIndex : indices) {
                locations.addAll(map.getValue(arrayIndex).getLocations());
            }
            value = latticeElement.getHeap(context).getValue(locations, LatticeElement::join);
        } else if (array.getArray() instanceof ListArrayLatticeElement) {
            if (indices.stream().allMatch(i -> i instanceof StringIndexLatticeElement || i.equals(IndexLatticeElement.bottom))) {
                annotator.error("Array string access on list");
            }

            ListArrayLatticeElement list = (ListArrayLatticeElement) array.getArray();
            value = latticeElement.getHeap(context).getValue(list.getLocations().getLocations(), LatticeElement::join);
        } else if (array.getArray() instanceof EmptyArrayLatticeElement) {
            value = new ValueLatticeElementImpl(NullLatticeElement.top);
        } else {
            value = ValueLatticeElement.top;
        }
        latticeElement = latticeElement.setTempsValue(context, n.getTargetName(), value);

        return latticeElement;
    }

    private AnalysisLatticeElement analyse(ArrayReadLocationSetNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement indexValue = latticeElement.getTempsValue(context, node.getIndexName());
        Collection<IndexLatticeElement> indices = generateArrayIndices(indexValue);
        latticeElement = latticeElement.setHeapTempsValue(context, node.getTargetTempHeapName(), new HeapLocationPowerSetLatticeElementImpl());
        if (indices.stream().allMatch(i -> i instanceof StringIndexLatticeElement || i.equals(IndexLatticeElement.bottom))) {
            allCheckArray(latticeElement, context, node.getValueTempHeapName(), a -> a instanceof ListArrayLatticeElement, () -> annotator.error("Array string access on list"));

        }
        for (HeapLocation loc : latticeElement.getHeapTempsValue(context, node.getValueTempHeapName()).getLocations()) {
            ValueLatticeElement array = latticeElement.getHeapValue(context, loc);
            if (array.getArray() instanceof MapArrayLatticeElement) {
                MapArrayLatticeElement map = (MapArrayLatticeElement) array.getArray();
                for (IndexLatticeElement index : indices) {
                    latticeElement = latticeElement.joinHeapTempsValue(context, node.getTargetTempHeapName(), map.getValue(index));
                }
            } else if (array.getArray() instanceof ListArrayLatticeElement) {

                ListArrayLatticeElement list = (ListArrayLatticeElement) array.getArray();
                latticeElement = latticeElement.joinHeapTempsValue(context, node.getTargetTempHeapName(), list.getLocations());

            } else if (!array.getArray().equals(ArrayLatticeElement.top)) {
                //Initialize new array if empty or not array
                ArrayLatticeElement arrayLattice;
                HeapLocation location = new HeapLocationImpl(context, node);

                arrayLattice = ArrayLatticeElement.generateMap(indexValue.toArrayIndex(), location);

                latticeElement = latticeElement.setHeapTempsValue(context, node.getTargetTempHeapName(), location);
                latticeElement = latticeElement.setHeapValue(context, loc, new ValueLatticeElementImpl(arrayLattice));
            }
            //TODO what if top? Should return all HeapLocations
        }

        return latticeElement;
    }


    private void allCheckArray(AnalysisLatticeElement latticeElement, Context context, TemporaryHeapVariableName locationSet, Predicate<ArrayLatticeElement> predicate, Action consumer) {
        if (latticeElement.getHeapTempsValue(context, locationSet).getLocations().stream().map(l -> latticeElement.getHeapValue(context, l).getArray()).allMatch(predicate)) {
            consumer.act();
        }

    }

    private AnalysisLatticeElement analyse(ArrayInitStackOperationNode n, AnalysisLatticeElement l, Context c) {
        return l.setTempsValue(c, n.getTargetName(), (name) -> new ValueLatticeElementImpl(ArrayLatticeElement.emptyArray));
    }

    private AnalysisLatticeElement analyse(VariableReadLocationSetNode node, AnalysisLatticeElement latticeElement, Context context) {
        VariableName name = node.getVariableName();
        Set<HeapLocation> newLocations = getVariableLocation(name, context, latticeElement).getLocations();
        if (newLocations.isEmpty()) {
            HeapLocation location = new HeapLocationImpl(context, node);
            latticeElement = updateVariable(
                    name,
                    context,
                    latticeElement,
                    m -> m.addLocation(location))
                    .setHeapValue(context, location, new ValueLatticeElementImpl(NullLatticeElement.top));
            newLocations.add(location);
        }

        latticeElement = latticeElement.setHeapTempsValue(context, node.getTargetTempHeapName(), newLocations);
        return latticeElement;
    }

    private AnalysisLatticeElement updateVariable(VariableName name, Context context, AnalysisLatticeElement latticeElement, Function<HeapLocationPowerSetLatticeElement, HeapLocationPowerSetLatticeElement> updater) {

        if (name.isSuperGlobal() || context.isEmpty()) {
            return latticeElement.setGlobalsValue(context, name, updater.apply(latticeElement.getGlobalsValue(context, name)));
        }
        return latticeElement.setLocalsValue(context, name, updater.apply(latticeElement.getLocalsValue(context, name)));
    }

    private HeapLocationPowerSetLatticeElement getVariableLocation(VariableName name, Context context, AnalysisLatticeElement latticeElement) {
        if (name.isSuperGlobal() || context.isEmpty()) {
            return latticeElement.getGlobalsValue(context, name);
        }

        return latticeElement.getLocalsValue(context, name);
    }

    private interface Action {

        void act();
    }
}
