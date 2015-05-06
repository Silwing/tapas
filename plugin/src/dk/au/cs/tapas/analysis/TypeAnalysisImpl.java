package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.*;
import dk.au.cs.tapas.cfg.graph.LibraryFunctionGraph;
import dk.au.cs.tapas.cfg.graph.NumberConstantImpl;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.*;
import org.jetbrains.annotations.NotNull;

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
        if (n instanceof ReferenceAssignmentNode) {
            return analyseReferenceAssignmentNode((ReferenceAssignmentNode) n, l, c);
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

    private AnalysisLatticeElement analyseResultNode(ResultNode resultNode, AnalysisLatticeElement latticeElement, Context context) {
        CallArgument argument = resultNode.getCallArgument();

        AnalysisLatticeElement resultLattice = resultNode.getCallLattice(context);

        Set<HeapLocation> argumentSet = null;
        if (argument instanceof HeapLocationSetCallArgument) {
            argumentSet = ((HeapLocationSetCallArgument) argument).getArgument();
            argumentSet.clear();
        }

        final Context exitNodeContext = context.addNode(resultNode.getCallNode());
        resultLattice = resultLattice.setHeap(context, resultLattice.getHeap(exitNodeContext)); //Setting the heap
        resultLattice = resultLattice.setGlobals(context, resultLattice.getGlobals(exitNodeContext)); //Setting the globals

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
                for (HeapLocation location : ((HeapLocationSetCallArgument) exitArgument).getArgument()) {
                    argumentSet.add(location);
                    resultLattice = resultLattice.setHeapValue(context, location, h -> latticeElement.getHeapValue(exitNodeContext, h));
                }

            } else if (argument instanceof HeapLocationSetCallArgument && exitArgument instanceof TemporaryVariableCallArgument) {
                //If alias method and stack variable. return, then create location with stack value.
                HeapLocation location = new HeapLocationImpl();
                resultLattice = resultLattice.setHeapValue(
                        context,
                        location,
                        h ->
                                latticeElement.getStackValue(
                                        exitNodeContext,
                                        ((TemporaryVariableCallArgument) exitArgument).getArgument()));
                argumentSet.add(location);

            } else if (argument instanceof TemporaryVariableCallArgument && exitArgument instanceof HeapLocationSetCallArgument) {
                //If method and location return, then stack variable with location values.
                final HeapLocationSetCallArgument finalExit = (HeapLocationSetCallArgument) exitArgument;
                resultLattice = resultLattice.joinStackValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        locationSetToValue(latticeElement.getValue(exitNodeContext), finalExit.getArgument()));
                //TODO check this


            } else if (argument instanceof TemporaryVariableCallArgument && exitArgument instanceof TemporaryVariableCallArgument) {
                //If method and stack variable return, then "rename" stack variable.
                final TemporaryVariableCallArgument finalExit = (TemporaryVariableCallArgument) exitArgument;
                resultLattice = resultLattice.setStackValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        v -> latticeElement.getStackValue(exitNodeContext, finalExit.getArgument()));
            }

        }

        //Plan: Take locals + stack from call-node lattice. Take globals + heap from exit.


        //TODO migrate heap! Take locals + globals addresses from old scope and add heap values from new scope. This should be done recursive on arrays and the like
        //Need to see book

        return resultLattice;
    }


    private ValueLatticeElement locationSetToValue(StateLatticeElement latticeElement, Set<HeapLocation> locations) {
        return latticeElement.getHeap().getValue(locations, LatticeElement::join);
    }


    private AnalysisLatticeElement analyseReferenceAssignmentNode(ReferenceAssignmentNode node, AnalysisLatticeElement latticeElement, Context context) {

        //TODO this is not going to work... We need a variable name or array heap location

        return latticeElement;
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
            } else if (callArgument instanceof TemporaryVariableCallArgument){
                lattice.setLocalsValue(
                        newContext,
                        argumentNames[i],
                        lattice.getStackValue(context, ((TemporaryVariableCallArgument) callArgument).getArgument()));
            }

        }


        //TODO implement
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
        return l;
    }

    private AnalysisLatticeElement analyseArrayReadExpressionNode(ArrayReadExpressionNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement val = l.getStackValue(c, n.getArrayName());
        if (val.getArray() instanceof MapArrayLatticeElement) {
            ValueLatticeElement index = l.getStackValue(c, n.getIndexName());
            IndexLatticeElement sindex = IndexLatticeElement.generateStringLIndex(index.getString());
            IndexLatticeElement iindex = IndexLatticeElement.generateIntegerIndex(index.getNumber().toInteger());

        }
        return l;
    }

    private AnalysisLatticeElement analyseArrayLocationVariableExpressionNode(ArrayLocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        Set<HeapLocation> target = n.getTargetLocationSet();

        ValueLatticeElement index = l.getStackValue(c, n.getIndexName());
        IndexLatticeElement sindex = IndexLatticeElement.generateStringLIndex(index.getString());
        IndexLatticeElement iindex = IndexLatticeElement.generateIntegerIndex(index.getNumber().toInteger());

        for (HeapLocation loc : n.getValueHeapLocationSet()) {
            ValueLatticeElement array = l.getHeapValue(c, loc);
            if (array.getArray() instanceof MapArrayLatticeElement) {
                MapArrayLatticeElement map = (MapArrayLatticeElement) array.getArray();
                target.addAll(map.getValue(sindex).getLocations());
                target.addAll(map.getValue(iindex).getLocations());
            } else if (array.getArray() instanceof ListArrayLatticeElement) {
                ListArrayLatticeElement list = (ListArrayLatticeElement) array.getArray();
                target.addAll(list.getLocations().getLocations());
            }
        }

        return l;
    }

    private AnalysisLatticeElement analyseArrayAppendLocationVariableExpressionNode(ArrayAppendLocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        ListArrayLatticeElement list = n.getValueHeapLocationSet().stream().reduce((ListArrayLatticeElement) new ListArrayLatticeElementImpl(), ListArrayLatticeElement::addLocation, (l1, l2) -> (ListArrayLatticeElement) l1.join(l2));
        ValueLatticeElement newTarget = new ValueLatticeElementImpl(list);
        return n.getTargetLocationSet().stream().reduce(l, (acc, h) -> acc.setHeapValue(c, h, acc.getHeapValue(c, h).join(newTarget)), (l1, l2) -> l1.join(l2));
    }

    private AnalysisLatticeElement analyseArrayAppendExpressionNode(ArrayAppendExpressionNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement newValue = l.getStackValue(c, n.getValueName());
        newValue.print(new PrintStreamLatticePrinter(System.out));
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
                latticeElement = latticeElement.addLocationToGlobal(context, name, location);
            }
            newLocations = latticeElement.getGlobalsValue(context, name).getLocations();
        } else {
            if (latticeElement.getGlobalsValue(context, name).getLocations().size() == 0) {
                HeapLocation location = new HeapLocationImpl();
                latticeElement = latticeElement.addLocationToLocal(context, name, location);
            }
            newLocations = latticeElement.getLocalsValue(context, name).getLocations();
        }


        n.getTargetLocationSet().clear(); // TODO: is this right?
        n.getTargetLocationSet().addAll(newLocations);
        //TODO initialize to NULL?

        return latticeElement;
    }

    //TODO implement support for super-globals
}
