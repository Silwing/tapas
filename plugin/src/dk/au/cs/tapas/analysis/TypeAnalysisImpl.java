package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.*;
import dk.au.cs.tapas.cfg.graph.LibraryFunctionGraph;
import dk.au.cs.tapas.cfg.graph.NumberConstantImpl;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Silwing on 28-04-2015.
 */
public class TypeAnalysisImpl implements Analysis {
    @Override
    public AnalysisLatticeElement getEmptyLattice() {
        return new AnalysisLatticeElementImpl();
    }

    @Override
    public AnalysisLatticeElement getStartLattice() {
        return new AnalysisLatticeElementImpl();
    }

    @Override
    public AnalysisLatticeElement analyse(ContextNodePair nc, AnalysisLatticeElement l) {
        l.print(new PrintStreamLatticePrinter(System.out));
        Node n = nc.getNode();
        Context c = nc.getContext();
        if (n instanceof LocationVariableExpressionNode) {
            return analyseNodeLocalVariableExpressionNode((LocationVariableExpressionNode) n, l, c);
        }
        if (n instanceof ArrayInitExpressionNode) {
            return analyseNodeArrayInitExpressionNode((ArrayInitExpressionNode) n, l, c);
        }
        if (n instanceof ArrayAppendExpressionNode) {
            return analyseArrayAppendExpressionNode((ArrayAppendExpressionNode) n, l, c);
        }
        if (n instanceof ArrayAppendLocationVariableExpressionNode) {
            return analyseArrayAppendLocationVariableExpressionNode((ArrayAppendLocationVariableExpressionNode) n, l, c);
        }
        if (n instanceof ArrayLocationVariableExpressionNode) {
            return analyseArrayLocationVariableExpressionNode((ArrayLocationVariableExpressionNode) n, l, c);
        }
        if (n instanceof ArrayReadExpressionNode) {
            return analyseArrayReadExpressionNode((ArrayReadExpressionNode) n, l, c);
        }
        if (n instanceof ArrayWriteExpressionNode) {
            return analyseArrayWriteExpressionNode((ArrayWriteExpressionNode) n, l, c);
        }
        if (n instanceof AssignmentNode) {
            return analyseAssignmentNode((AssignmentNode) n, l, c);
        }
        if (n instanceof ShortCircuitBinaryOperationNode) {
            return analyseShortCircuitBinaryOperationNode((ShortCircuitBinaryOperationNode) n, l, c);
        }
        if (n instanceof BinaryOperationNode) {
            return analyseBinaryOperationNode((BinaryOperationNode) n, l, c);
        }
        if (n instanceof CallNode) {
            return analyseCallNode((CallNode) n, l, c);
        }
        if (n instanceof EndNode) {
            return analyseEndNode((EndNode) n, l, c);
        }
        if (n instanceof ExitNode) {
            return analyseExitNode((ExitNode) n, l, c);
        }
        if (n instanceof IfNode) {
            return analyseIfNode((IfNode) n, l, c);
        }
        if (n instanceof IncrementDecrementOperationExpressionNode) {
            return analyseIncrementDecrementOperationExpressionNode((IncrementDecrementOperationExpressionNode) n, l, c);
        }
        if (n instanceof ReadConstNode) {
            return analyseReadConstNode((ReadConstNode) n, l, c);
        }
        if (n instanceof ReadNode) {
            return analyseReadNode((ReadNode) n, l, c);
        }
        if (n instanceof ArrayWriteReferenceAssignmentNode) {
            return analyseArrayReferenceAssignmentNode((ArrayWriteReferenceAssignmentNode) n, l, c);
        }
        if (n instanceof ArrayAppendReferenceAssignmentNode) {
            return analyseArrayAppendReferenceAssignmentNode((ArrayAppendReferenceAssignmentNode) n, l, c);
        }
        if (n instanceof VariableReferenceAssignmentNode) {
            return analyseVariableReferenceAssignmentNode((VariableReferenceAssignmentNode) n, l, c);
        }
        if (n instanceof ResultNode) {
            return analyseResultNode((ResultNode) n, l, c);
        }
        if (n instanceof StartNode) {
            return analyseStartNode((StartNode) n, l, c);
        }
        if (n instanceof UnaryOperationNode) {
            return analyseUnaryOperationNode((UnaryOperationNode) n, l, c);
        }


        // Fallback to identity function for unhandled nodes
        return l;
    }

    private AnalysisLatticeElement analyseArrayAppendReferenceAssignmentNode(ArrayAppendReferenceAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement listValue = new ValueLatticeElementImpl(new ListArrayLatticeElementImpl(node.getValueLocationSet()));


        latticeElement = latticeElement.setStackValue(context, node.getTargetName(), latticeElement.getHeap(context).getValue(node.getValueLocationSet(), LatticeElement::join));
        for (HeapLocation location : node.getVariableLocationSet()) {
            latticeElement = latticeElement.joinHeapValue(context, location, listValue);
        }
        return latticeElement;
    }

    private AnalysisLatticeElement analyseVariableReferenceAssignmentNode(VariableReferenceAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        latticeElement = latticeElement.setStackValue(context, node.getTargetName(), latticeElement.getHeap(context).getValue(node.getValueLocationSet(), LatticeElement::join));
        if (context.isEmpty()) {
            latticeElement = latticeElement.setGlobalsValue(context, node.getVariableName(), node.getValueLocationSet());
        } else {
            latticeElement = latticeElement.setLocalsValue(context, node.getVariableName(), node.getValueLocationSet());
        }

        return latticeElement;
    }

    private IndexLatticeElement[] generateArrayIndices(ValueLatticeElement element) {
        return new IndexLatticeElement[]{element.getBoolean().toArrayIndex(), element.getNull().toArrayIndex(), element.getString().toArrayIndex(), element.getNumber().toArrayIndex()};
    }


    private AnalysisLatticeElement analyseArrayReferenceAssignmentNode(
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
            latticeElement = latticeElement.setHeapValue(context,location,value.setArray(array));

        }


        return latticeElement;
    }


    ArrayLatticeElement writeArray(ArrayLatticeElement array, Set<HeapLocation> valueLocationSet, IndexLatticeElement[] arrayIndices) {
        //If location is top array, do nothing
        if (array.equals(ArrayLatticeElement.top)) {
            return array;
        }
        if (array.equals(ArrayLatticeElement.bottom) || array.equals(ArrayLatticeElement.emptyArray)) {
            //For every possible index
            for (IndexLatticeElement index : arrayIndices) {
                //If not initialized as array, or empty array, and index is 0. Then create list.
                if (index.equals(IndexLatticeElement.generateIntegerIndex(IntegerLatticeElement.generateElement(0)))) {
                    array = array.join(ArrayLatticeElement.generateList(valueLocationSet));
                } else {
                    //If not initialized as array, or empty array, and index is not 0. Then create map.
                    array = array.join(ArrayLatticeElement.generateMap(index, valueLocationSet));
                }
            }
        } else if (array instanceof ListArrayLatticeElement) {
            array = ((ListArrayLatticeElement) array).addLocations(valueLocationSet);

        } else if (array instanceof MapArrayLatticeElement) {
            for (IndexLatticeElement index : arrayIndices) {
                array = array.join(ArrayLatticeElement.generateMap(index, valueLocationSet));
            }

        }
        return array;
    }


    private AnalysisLatticeElement analyseUnaryOperationNode(UnaryOperationNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement value = l.getStackValue(c, n.getOperandName());
        if (n.getOperator() == UnaryOperator.NEGATION) {
            return l.setStackValue(c, n.getTargetName(), target -> new ValueLatticeElementImpl(value.toBoolean().negate()));
        }
        return l;
    }

    private AnalysisLatticeElement analyseStartNode(StartNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseShortCircuitBinaryOperationNode(ShortCircuitBinaryOperationNode node, AnalysisLatticeElement latticeElement, Context context) {
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

    private AnalysisLatticeElement analyseResultNode(ResultNode resultNode, AnalysisLatticeElement resultLattice, Context context) {
        CallArgument argument = resultNode.getCallArgument();
        final AnalysisLatticeElement inputLattice = resultLattice;

        AnalysisLatticeElement callLattice = resultNode.getCallLattice(context);

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
                        h ->
                                inputLattice.getStackValue(
                                        exitNodeContext,
                                        ((TemporaryVariableCallArgument) exitArgument).getArgument()));
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
                resultLattice = resultLattice.setStackValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        v -> inputLattice.getStackValue(exitNodeContext, finalExit.getArgument()));
            }

        }

        return resultLattice;
    }


    private AnalysisLatticeElement analyseReadNode(ReadNode n, AnalysisLatticeElement l, Context c) {
        HeapLocationPowerSetLatticeElement locations;
        if (c.isEmpty()) {
            locations = l.getGlobalsValue(c, n.getVariableName());
        } else {
            locations = l.getLocalsValue(c, n.getVariableName());
        }
        //Reading the joint value from heap to stack
        return l.setStackValue(c, n.getTargetName(), name -> l.getHeap(c).getValue(locations.getLocations(), LatticeElement::join));
    }

    private AnalysisLatticeElement analyseReadConstNode(ReadConstNode n, AnalysisLatticeElement l, Context c) {
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

    private AnalysisLatticeElement analyseIncrementDecrementOperationExpressionNode(IncrementDecrementOperationExpressionNode node, AnalysisLatticeElement latticeElement, Context context) {
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

    private AnalysisLatticeElement analyseIfNode(IfNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseExitNode(ExitNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseEndNode(EndNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseCallNode(CallNode node, AnalysisLatticeElement lattice, Context context) {

        if (node instanceof LibraryFunctionGraph) {
            //Should be handled by dedicated transfer function
            throw new UnsupportedOperationException();
        }

        //Plan: Move heap from current context to new context. Set arguments in local scope.

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
                lattice.setLocalsValue(
                        newContext,
                        argumentNames[i],
                        lattice.getStackValue(context, ((TemporaryVariableCallArgument) callArgument).getArgument()));
            }

        }

        return lattice;
    }

    private AnalysisLatticeElement analyseBinaryOperationNode(BinaryOperationNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement
                leftValue = latticeElement.getStackValue(context, node.getLeftOperandName()),
                rightValue = latticeElement.getStackValue(context, node.getRightOperandName()),
                targetValue;
        //TODO more precision
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
                targetValue = new ValueLatticeElementImpl(leftValue.toNumber().divide(rightValue.toNumber()));
                break;
            case MODULO:
                targetValue = new ValueLatticeElementImpl(leftValue.toNumber().modulo(rightValue.toNumber()));
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
                targetValue = new ValueLatticeElementImpl(leftValue.toNumber().greaterThan(rightValue.toNumber()));
                break;
            case LESS_THAN:
                targetValue = new ValueLatticeElementImpl(leftValue.toNumber().lessThan(rightValue.toNumber()));
                break;
            case GREATER_THAN_OR_EQ:
                targetValue = new ValueLatticeElementImpl(leftValue.toNumber().greaterThanOrEqual(rightValue.toNumber()));
                break;
            case LESS_THAN_OR_EQ:
                targetValue = new ValueLatticeElementImpl(leftValue.toNumber().lessThanOrEqual(rightValue.toNumber()));
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

    private AnalysisLatticeElement analyseAssignmentNode(AssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {
        ValueLatticeElement value = latticeElement.getStackValue(context, node.getValueName());


        latticeElement = updateLocations(latticeElement, context, node.getVariableLocations(), value);

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

    private AnalysisLatticeElement analyseArrayWriteExpressionNode(ArrayWriteExpressionNode n, AnalysisLatticeElement l, Context c) {


        //TODO implement
        return l;
    }

    private AnalysisLatticeElement analyseArrayReadExpressionNode(ArrayReadExpressionNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement array = l.getStackValue(c, n.getArrayName());
        ValueLatticeElement value;
        if (array.getArray() instanceof MapArrayLatticeElement) {
            MapArrayLatticeElement map = (MapArrayLatticeElement) array.getArray();
            ValueLatticeElement index = l.getStackValue(c, n.getIndexName());
            Set<HeapLocation> locations = new HashSet<>();
            for (IndexLatticeElement arrayIndex : generateArrayIndices(index)) {
                locations.addAll(map.getValue(arrayIndex).getLocations());
            }
            value = l.getHeap(c).getValue(locations, LatticeElement::join);
        } else if(array.getArray() instanceof ListArrayLatticeElement) {
            ListArrayLatticeElement list = (ListArrayLatticeElement)array.getArray();
            value = l.getHeap(c).getValue(list.getLocations().getLocations(), LatticeElement::join);
        } else if(array.getArray() instanceof EmptyArrayLatticeElement) {
            value = new ValueLatticeElementImpl(NullLatticeElement.top);
        } else {
            value = ValueLatticeElement.top;
        }
        l = l.joinStackValue(c, n.getTargetName(), value);

        return l;
    }

    private AnalysisLatticeElement analyseArrayLocationVariableExpressionNode(ArrayLocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        Set<HeapLocation> target = n.getTargetLocationSet(); //TODO clear

        ValueLatticeElement index = l.getStackValue(c, n.getIndexName());
        IndexLatticeElement sindex = IndexLatticeElement.generateStringLIndex(index.getString());
        IndexLatticeElement iindex = IndexLatticeElement.generateIntegerIndex(index.getNumber().toInteger());

        for (HeapLocation loc : n.getValueHeapLocationSet()) {
            ValueLatticeElement array = l.getHeapValue(c, loc);
            if (array.getArray() instanceof MapArrayLatticeElement) {
                MapArrayLatticeElement map = (MapArrayLatticeElement) array.getArray();
                target.addAll(map.getValue(sindex).getLocations());
                target.addAll(map.getValue(iindex).getLocations());
                target.addAll(map.getValue(IndexLatticeElement.top).getLocations());
            } else if (array.getArray() instanceof ListArrayLatticeElement) {
                ListArrayLatticeElement list = (ListArrayLatticeElement) array.getArray();
                target.addAll(list.getLocations().getLocations());
            }
        }

        return l;
    }

    private AnalysisLatticeElement analyseArrayAppendLocationVariableExpressionNode(ArrayAppendLocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        ListArrayLatticeElement list = n.getValueHeapLocationSet().stream().reduce(new ListArrayLatticeElementImpl(), ListArrayLatticeElement::addLocation, (l1, l2) -> (ListArrayLatticeElement) l1.join(l2));
        ValueLatticeElement newTarget = new ValueLatticeElementImpl(list);
        // TODO: this is completely wrong I think :S
        return n.getTargetLocationSet().stream().reduce(l, (acc, h) -> acc.setHeapValue(c, h, acc.getHeapValue(c, h).join(newTarget)), (l1, l2) -> l1.join(l2));
    }

    private AnalysisLatticeElement analyseArrayAppendExpressionNode(ArrayAppendExpressionNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement newValue = l.getStackValue(c, n.getValueName());
        HeapLocation location = new HeapLocationImpl();
        ListArrayLatticeElement list = new ListArrayLatticeElementImpl().addLocation(location);
        ValueLatticeElement newTarget = new ValueLatticeElementImpl(list);
        return l.setHeapValue(c, location, (loc) -> newValue).setStackValue(c, n.getTargetName(), l.getStackValue(c, n.getTargetName()).join(newTarget));
    }

    private AnalysisLatticeElement analyseNodeArrayInitExpressionNode(ArrayInitExpressionNode n, AnalysisLatticeElement l, Context c) {
        return l.setStackValue(c, n.getTargetName(), (name) -> new ValueLatticeElementImpl(ArrayLatticeElement.emptyArray));
    }

    private AnalysisLatticeElement analyseNodeLocalVariableExpressionNode(LocationVariableExpressionNode n, AnalysisLatticeElement latticeElement, Context context) {
        VariableName name = n.getVariableName();
        Set<HeapLocation> newLocations;
        //If no locations, create one.
        if (context.isEmpty()) {
            if (latticeElement.getGlobalsValue(context, name).getLocations().size() == 0) {
                HeapLocation location = new HeapLocationImpl();
                latticeElement = latticeElement
                        .addLocationToGlobal(context, name, location)
                        .setHeapValue(context, location, new ValueLatticeElementImpl(NullLatticeElement.top)); // Initialize to Null
            }
            newLocations = latticeElement.getGlobalsValue(context, name).getLocations();
        } else {
            if (latticeElement.getGlobalsValue(context, name).getLocations().size() == 0) {
                HeapLocation location = new HeapLocationImpl();
                latticeElement = latticeElement
                        .addLocationToLocal(context, name, location)
                        .setHeapValue(context, location, new ValueLatticeElementImpl(NullLatticeElement.top)); // Initialize to Null
            }
            newLocations = latticeElement.getLocalsValue(context, name).getLocations();
        }


        n.getTargetLocationSet().clear(); // TODO: is this right?
        n.getTargetLocationSet().addAll(newLocations);

        return latticeElement;
    }

    //TODO implement support for super-globals
}
