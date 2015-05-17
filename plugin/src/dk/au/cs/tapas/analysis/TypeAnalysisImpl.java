package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.*;
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
    public AnalysisLatticeElement getStartLattice() {
        AnalysisLatticeElement lattice = new AnalysisLatticeElementImpl();
        // TODO: init as maps
        for (VariableName name : VariableName.superGlobals) {
            lattice = lattice.setGlobalsValue(new ContextImpl(), name, new ValueLatticeElementImpl(ArrayLatticeElement.top));
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
        ValueLatticeElement listValue = latticeElement.getStackValue(context, node.getValueName());
        HeapLocation listLocation = new HeapLocationImpl();

        latticeElement = latticeElement
                .setStackValue(context, node.getTargetName(), listValue)
                .setHeapValue(context, listLocation, listValue);

        allCheckArray(latticeElement, context, node.getVariableLocationSet(), a -> a instanceof MapArrayLatticeElement, () -> annotator.error("Appending on map"));
        checkArrayAddValue(latticeElement.getValue(context), node.getVariableLocationSet(), listLocation);

        for (HeapLocation location : node.getVariableLocationSet()) {
            latticeElement = latticeElement.joinHeapValue(context, location, new ValueLatticeElementImpl(ArrayLatticeElement.generateList(listLocation)));
        }
        return latticeElement;
    }


    private AnalysisLatticeElement analyse(ArrayAppendReferenceAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement listValue = new ValueLatticeElementImpl(ArrayLatticeElement.generateList(node.getValueLocationSet()));

        latticeElement = latticeElement
                .setStackValue(context, node.getTargetName(), latticeElement.getHeap(context).getValue(node.getValueLocationSet(), LatticeElement::join));

        allCheckArray(latticeElement, context, node.getVariableLocationSet(), a -> a instanceof MapArrayLatticeElement, () -> annotator.error("Appending on map"));
        checkArrayAddValue(latticeElement.getValue(context), node.getVariableLocationSet(), node.getValueLocationSet());

        for (HeapLocation location : node.getVariableLocationSet()) {
            latticeElement = latticeElement.joinHeapValue(context, location, listValue);
        }
        return latticeElement;
    }

    private AnalysisLatticeElement analyse(ArrayAppendLocationSetNode n, AnalysisLatticeElement latticeElement, Context context) {
        Set<HeapLocation> target = n.getTargetLocationSet();
        target.clear();

        allCheckArray(latticeElement, context, n.getValueHeapLocationSet(), a -> a instanceof MapArrayLatticeElement, () -> annotator.error("Appending on map"));


        for (HeapLocation loc : n.getValueHeapLocationSet()) {
            HeapLocation newLoc = new HeapLocationImpl();
            target.add(newLoc);
            latticeElement = latticeElement.joinHeapValue(context, loc, new ValueLatticeElementImpl(ArrayLatticeElement.generateList(newLoc)));
        }

        return latticeElement;
    }

    private AnalysisLatticeElement analyse(ArrayAppendStackOperationNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement newValue = l.getStackValue(c, n.getValueName());
        ValueLatticeElement oldArray = l.getStackValue(c, n.getTargetName());
        if (oldArray.getArray() instanceof MapArrayLatticeElement) {
            annotator.error("Appending on map");
        } else if (oldArray.getArray() instanceof ListArrayLatticeElement) {
            checkArrayAddValue(l.getHeap(c).getValue(((ListArrayLatticeElement) oldArray.getArray()).getLocations(), LatticeElement::join), newValue);
        }

        HeapLocation location = new HeapLocationImpl();
        ArrayLatticeElement list = ArrayLatticeElement.generateList(location);
        ValueLatticeElement newTarget = new ValueLatticeElementImpl(list);
        return l.setHeapValue(c, location, newValue).setStackValue(c, n.getTargetName(), oldArray.join(newTarget));
    }

    private AnalysisLatticeElement analyse(ArrayWriteAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        //Setting target
        latticeElement = latticeElement.setStackValue(context, node.getTargetName(), latticeElement.getStackValue(context, node.getValueName()));
        //For each possible location
        HeapLocation valueLocation = new HeapLocationImpl();
        latticeElement = latticeElement.setHeapValue(context, valueLocation, latticeElement.getStackValue(context, node.getValueName()));

        final AnalysisLatticeElement finalLatticeElement = latticeElement;
        //Only add write error if all arrays are lists
        boolean addError = node.getVariableLocationSet().stream().map(l -> finalLatticeElement.getHeapValue(context, l).getArray()).allMatch(a -> a instanceof ListArrayLatticeElement);

        checkArrayAddValue(latticeElement.getValue(context), node.getVariableLocationSet(), valueLocation);

        for (HeapLocation location : node.getVariableLocationSet()) {
            ValueLatticeElement value = latticeElement.getHeapValue(context, location);
            ArrayLatticeElement array = writeArray(
                    value.getArray(),
                    valueLocation,
                    generateArrayIndices(
                            latticeElement.getStackValue(
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

        Set<HeapLocation> valueLocations = node.getValueLocationSet();
        latticeElement = latticeElement.setStackValue(context, node.getTargetName(), latticeElement.getHeap(context).getValue(valueLocations, LatticeElement::join));
        //For each possible location
        for (HeapLocation location : node.getVariableLocationSet()) {
            ValueLatticeElement value = latticeElement.getHeapValue(context, location);
            ArrayLatticeElement array = writeArray(
                    value.getArray(),
                    valueLocations,
                    generateArrayIndices(
                            latticeElement.getStackValue(
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
        latticeElement = latticeElement.setStackValue(context, node.getTargetName(), latticeElement.getHeap(context).getValue(node.getValueLocationSet(), LatticeElement::join));
        latticeElement = updateVariable(node.getVariableName(), context, latticeElement, m -> new HeapLocationPowerSetLatticeElementImpl(node.getValueLocationSet()));

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
        ValueLatticeElement value = l.getStackValue(c, n.getOperandName());
        if (n.getOperator() == UnaryOperator.NEGATION) {
            return l.setStackValue(c, n.getTargetName(), target -> new ValueLatticeElementImpl(value.toBoolean().negate()));
        }
        if (n.getOperator() == UnaryOperator.MINUS) {
            return l.setStackValue(c, n.getTargetName(), target -> new ValueLatticeElementImpl(value.toNumber().minus()));
        }

        return l;
    }


    private AnalysisLatticeElement analyse(ShortCircuitBinaryOperationNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement
                targetValue;
        BooleanLatticeElement
                leftValue = latticeElement.getStackValue(context, node.getLeftOperandName()).getBoolean(),
                rightValue = latticeElement.getStackValue(context, node.getRightOperandName()).getBoolean();

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

        latticeElement = latticeElement.setStackValue(context, node.getTargetName(), targetValue);

        return latticeElement;
    }


    private AnalysisLatticeElement analyse(ResultNode resultNode, AnalysisLatticeElement resultLattice, AnalysisLatticeElement callLattice, Context context) {
        CallArgument argument = resultNode.getCallArgument();
        final AnalysisLatticeElement inputLattice = resultLattice;

        Set<HeapLocation> argumentSet = null;
        if (argument instanceof HeapLocationSetCallArgument) {
            argumentSet = ((HeapLocationSetCallArgument) argument).getArgument();
            argumentSet.clear();
        }

        final Context exitNodeContext = context.addNode(resultNode.getCallNode());

        //Taking globals from prev. lattice and restoring locals from call lattice.
        resultLattice = resultLattice.setHeap(context, resultLattice.getHeap(exitNodeContext)); //Setting the heap
        resultLattice = resultLattice.setGlobals(context, resultLattice.getGlobals(exitNodeContext)); //Setting the globals
        resultLattice = resultLattice.setLocals(context, callLattice.getLocals(context)); //Setting the locals
        resultLattice = resultLattice.setStack(context, callLattice.getStack(context)); //Setting the stack

        if (resultNode.getExitNode().getCallArguments().length == 0) {
            // If void method, it returns null
            if (argument instanceof TemporaryVariableCallArgument) {
                resultLattice = resultLattice.setStackValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        (t) -> new ValueLatticeElementImpl(NullLatticeElement.top));
            }

            return resultLattice;
        }

        if (argument instanceof TemporaryVariableCallArgument) {
            //Clearing stack variable before iteration. Just in case
            resultLattice = resultLattice.setStackValue(context, ((TemporaryVariableCallArgument) argument).getArgument(), new ValueLatticeElementImpl());
        }
        for (CallArgument exitArgument : resultNode.getExitNode().getCallArguments()) {
            if (argument instanceof HeapLocationSetCallArgument && exitArgument instanceof HeapLocationSetCallArgument) {
                //If alias method and alias return, then parse locations
                argumentSet.addAll(((HeapLocationSetCallArgument) exitArgument).getArgument());


            } else if (argument instanceof HeapLocationSetCallArgument && exitArgument instanceof TemporaryVariableCallArgument) {
                //If alias method and stack variable. return, then create location with stack value.
                HeapLocation location = new HeapLocationImpl();
                resultLattice = resultLattice.setHeapValue(
                        context,
                        location,
                        inputLattice.getStackValue(exitNodeContext, ((TemporaryVariableCallArgument) exitArgument).getArgument()));

                argumentSet.add(location);

            } else if (argument instanceof TemporaryVariableCallArgument && exitArgument instanceof HeapLocationSetCallArgument) {
                //If method and location return, then stack variable with location values.
                final HeapLocationSetCallArgument finalExit = (HeapLocationSetCallArgument) exitArgument;
                resultLattice = resultLattice.joinStackValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        inputLattice.getHeap(exitNodeContext).getValue(finalExit.getArgument(), LatticeElement::join));


            } else if (argument instanceof TemporaryVariableCallArgument && exitArgument instanceof TemporaryVariableCallArgument) {
                //If method and stack variable return, then "rename" stack variable.
                final TemporaryVariableCallArgument finalExit = (TemporaryVariableCallArgument) exitArgument;
                resultLattice = resultLattice.joinStackValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        inputLattice.getStackValue(exitNodeContext, finalExit.getArgument()));
            }

        }

        return resultLattice;
    }

    private AnalysisLatticeElement analyse(VariableReadNode n, AnalysisLatticeElement l, Context c) {
        HeapLocationPowerSetLatticeElement locations = getVariableLocation(n.getVariableName(), c, l);
        //Reading the joint value from heap to stack
        return l.setStackValue(c, n.getTargetName(), name -> l.getHeap(c).getValue(locations.getLocations(), LatticeElement::join));
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

        return l.setStackValue(c, n.getTargetName(), (temp) -> l.getStackValue(c, n.getTargetName()).join(newTarget));
    }

    private AnalysisLatticeElement analyse(IncrementDecrementOperationStackOperationNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement value, targetValue, locationValue = latticeElement.getHeap(context).getValue(node.getHeapLocationSet(), LatticeElement::join);
        //Notice that PHP does not coerce when value not a number (inc,dec)
        switch (node.getOperation()) {
            case PRE_INCREMENT:
                value = targetValue = new ValueLatticeElementImpl(locationValue.getNumber().increment());
                break;
            case POST_INCREMENT:
                targetValue = locationValue;
                value = new ValueLatticeElementImpl(locationValue.getNumber().increment());
                break;
            case PRE_DECREMENT:
                value = targetValue = new ValueLatticeElementImpl(locationValue.getNumber().decrement());
                break;
            case POST_DECREMENT:
                targetValue = locationValue;
                value = new ValueLatticeElementImpl(locationValue.getNumber().decrement());
                break;
            default:
                return latticeElement;
        }

        latticeElement = latticeElement.setStackValue(context, node.getTargetName(), targetValue);
        latticeElement = updateLocations(latticeElement, context, node.getHeapLocationSet(), value);
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
            if (callArgument instanceof HeapLocationSetCallArgument) {
                lattice = lattice.setLocalsValue(
                        newContext,
                        argumentNames[i],
                        ((HeapLocationSetCallArgument) callArgument).getArgument());
            } else if (callArgument instanceof TemporaryVariableCallArgument) {
                lattice = lattice.setLocalsValue(
                        newContext,
                        argumentNames[i],
                        lattice.getStackValue(context, ((TemporaryVariableCallArgument) callArgument).getArgument()));
            }

        }

        return lattice;
    }

    private AnalysisLatticeElement analyse(BinaryOperationNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement
                leftValue = latticeElement.getStackValue(context, node.getLeftOperandName()),
                rightValue = latticeElement.getStackValue(context, node.getRightOperandName()),
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

        latticeElement = latticeElement.setStackValue(context, node.getTargetName(), targetValue);

        return latticeElement;
    }


    private AnalysisLatticeElement analyse(VariableAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement value = latticeElement.getStackValue(context, node.getValueName());
        HeapLocationPowerSetLatticeElement variableLocations = getVariableLocation(node.getVariableName(), context, latticeElement);

        latticeElement = updateLocations(latticeElement, context, variableLocations.getLocations(), value);

        //Remember to update target stack
        latticeElement = latticeElement.setStackValue(context, node.getTargetName(), value);


        return latticeElement;
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
                arrayValue = latticeElement.getStackValue(context, node.getTargetName()),
                entryValue = latticeElement.getStackValue(context, node.getValueName()),
                keyValue = latticeElement.getStackValue(context, node.getKeyName());
        ArrayLatticeElement array = arrayValue.getArray();
        HeapLocation newLocation = new HeapLocationImpl();

        if (array instanceof ListArrayLatticeElement) {
            checkArrayAddValue(latticeElement.getHeap(context).getValue(((ListArrayLatticeElement) array).getLocations(), LatticeElement::join), entryValue);
        }

        array = writeArray(array, newLocation, generateArrayIndices(keyValue));

        return latticeElement
                .setStackValue(context, node.getTargetName(), arrayValue.setArray(array)) //Update the new stack value
                .setHeapValue(context, newLocation, entryValue); //Add the entry to the heap
    }

    private AnalysisLatticeElement analyse(ArrayReadStackOperationNode n, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement array = latticeElement.getStackValue(context, n.getArrayName());
        ValueLatticeElement value;
        Set<IndexLatticeElement> indices = generateArrayIndices(latticeElement.getStackValue(context, n.getIndexName()));
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
        latticeElement = latticeElement.joinStackValue(context, n.getTargetName(), value);

        return latticeElement;
    }

    private AnalysisLatticeElement analyse(ArrayReadLocationSetNode n, AnalysisLatticeElement latticeElement, Context context) {
        Set<HeapLocation> target = n.getTargetLocationSet();
        target.clear();
        ValueLatticeElement indexValue = latticeElement.getStackValue(context, n.getIndexName());
        Collection<IndexLatticeElement> indices = generateArrayIndices(indexValue);

        if (indices.stream().allMatch(i -> i instanceof StringIndexLatticeElement || i.equals(IndexLatticeElement.bottom))) {
            allCheckArray(latticeElement, context, n.getValueHeapLocationSet(), a -> a instanceof ListArrayLatticeElement, () -> annotator.error("Array string access on list"));

        }
        for (HeapLocation loc : n.getValueHeapLocationSet()) {
            ValueLatticeElement array = latticeElement.getHeapValue(context, loc);
            if (array.getArray() instanceof MapArrayLatticeElement) {
                MapArrayLatticeElement map = (MapArrayLatticeElement) array.getArray();
                for (IndexLatticeElement index : indices) {
                    target.addAll(map.getValue(index).getLocations());
                }
            } else if (array.getArray() instanceof ListArrayLatticeElement) {

                ListArrayLatticeElement list = (ListArrayLatticeElement) array.getArray();
                target.addAll(list.getLocations().getLocations());
            } else if (!array.getArray().equals(ArrayLatticeElement.top)) {
                //Initialize new array if empty or not array
                ArrayLatticeElement arrayLattice;
                HeapLocation location = new HeapLocationImpl();

                arrayLattice = ArrayLatticeElement.generateMap(indexValue.toArrayIndex(), location);

                target.add(location);
                latticeElement = latticeElement.setHeapValue(context, loc, new ValueLatticeElementImpl(arrayLattice));
            }
        }

        return latticeElement;
    }


    private void allCheckArray(AnalysisLatticeElement latticeElement, Context context, Set<HeapLocation> locationSet, Predicate<ArrayLatticeElement> predicate, Action consumer) {
        if (locationSet.stream().map(l -> latticeElement.getHeapValue(context, l).getArray()).allMatch(predicate)) {
            consumer.act();
        }

    }

    private AnalysisLatticeElement analyse(ArrayInitStackOperationNode n, AnalysisLatticeElement l, Context c) {
        return l.setStackValue(c, n.getTargetName(), (name) -> new ValueLatticeElementImpl(ArrayLatticeElement.emptyArray));
    }

    private AnalysisLatticeElement analyse(VariableReadLocationSetNode node, AnalysisLatticeElement latticeElement, Context context) {
        VariableName name = node.getVariableName();
        Set<HeapLocation> newLocations = getVariableLocation(name, context, latticeElement).getLocations();
        if (newLocations.isEmpty()) {
            HeapLocation location = new HeapLocationImpl();
            latticeElement = updateVariable(
                    name,
                    context,
                    latticeElement,
                    m -> m.addLocation(location))
                    .setHeapValue(context, location, new ValueLatticeElementImpl(NullLatticeElement.top));
            newLocations.add(location);
        }
        node.getTargetLocationSet().clear();
        node.getTargetLocationSet().addAll(newLocations);

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
