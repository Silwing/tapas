package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.CallArgument;
import dk.au.cs.tapas.cfg.HeapLocationSetCallArgument;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgument;
import dk.au.cs.tapas.cfg.BooleanConstantImpl;
import dk.au.cs.tapas.cfg.NullConstantImpl;
import dk.au.cs.tapas.cfg.StringConstantImpl;
import dk.au.cs.tapas.cfg.graph.NumberConstantImpl;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.*;
import org.jetbrains.annotations.NotNull;

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
        return l;
    }

    private AnalysisLatticeElement analyseStartNode(StartNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseShortCircuitBinaryOperationNode(ShortCircuitBinaryOperationNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseResultNode(ResultNode resultNode, AnalysisLatticeElement latticeElement, Context context) {
        CallArgument argument = resultNode.getCallArgument();

        AnalysisLatticeElement resultLattice = resultNode.getCallLattice(context);

        Set<HeapLocation> argumentSet = null;
        if (argument instanceof HeapLocationSetCallArgument) {
            argumentSet = ((HeapLocationSetCallArgument) argument).getArgument();
            argumentSet.clear();
        }

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

        final Context exitNodeContext = context.addNode(resultNode.getCallNode());

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
                resultLattice = resultLattice.setStackValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        v -> locationSetToValue(latticeElement.getValue(exitNodeContext), finalExit.getArgument()));


            } else if (argument instanceof TemporaryVariableCallArgument && exitArgument instanceof TemporaryVariableCallArgument) {
                //If method and stack variable return, then "rename" stack variable.
                final TemporaryVariableCallArgument finalExit = (TemporaryVariableCallArgument) exitArgument;
                resultLattice = resultLattice.setStackValue(
                        context,
                        ((TemporaryVariableCallArgument) argument).getArgument(),
                        v -> latticeElement.getStackValue(exitNodeContext, finalExit.getArgument()));
            }

        }

        //Updating locals
        resultLattice = updateGlobalsLocals(resultLattice.getLocals(context), latticeElement.getValue(exitNodeContext));
        resultLattice = updateGlobalsLocals(resultLattice.getGlobals(context), latticeElement.getValue(exitNodeContext));


        //TODO migrate heap! Take locals + globals addresses from old scope and add heap values from new scope. This should be done recursive on arrays and the like


        return resultLattice;
    }

    @NotNull
    private AnalysisLatticeElement updateGlobalsLocals(MapLatticeElement<VariableName, PowerSetLatticeElement<HeapLocation>> mapLatticeElement, StateLatticeElement value) {
        return new AnalysisLatticeElementImpl();
    }

    private ValueLatticeElement locationSetToValue(StateLatticeElement latticeElement, Set<HeapLocation> locations) {
        return null;
    }

    //TODO by keeping creating heap locations, how are we ensured that the algorithm will stop?

    private AnalysisLatticeElement analyseReferenceAssignmentNode(ReferenceAssignmentNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseReadNode(ReadNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseReadConstNode(ReadConstNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement newTarget;
        Object constant = n.getConstant().getValue();
        if(n.getConstant() instanceof StringConstantImpl)
            newTarget = new ValueLatticeElementImpl(StringLatticeElement.generateStringLatticeElement((String)constant));
        else if(n.getConstant() instanceof NumberConstantImpl)
            newTarget = new ValueLatticeElementImpl(NumberLatticeElement.generateNumberLatticeElement((Integer)constant));
        else if(n.getConstant() instanceof BooleanConstantImpl)
            newTarget = new ValueLatticeElementImpl(BooleanLatticeElement.generateBooleanLatticeElement((Boolean)constant));
        else if(n.getConstant() instanceof NullConstantImpl)
            newTarget = new ValueLatticeElementImpl(NullLatticeElement.top);
        else
            newTarget = new ValueLatticeElementImpl();

        return l.setStackValue(c, n.getTargetName(), (temp) -> l.getStackValue(c, n.getTargetName()).join(newTarget));
    }

    private AnalysisLatticeElement analyseIncrementDecrementOperationExpressionNode(IncrementDecrementOperationExpressionNode n, AnalysisLatticeElement l, Context c) {
        return l;
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

    private AnalysisLatticeElement analyseCallNode(CallNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseBinaryOperationNode(BinaryOperationNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseAssignmentNode(AssignmentNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseArrayWriteExpressionNode(ArrayWriteExpressionNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseArrayReadExpressionNode(ArrayReadExpressionNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseArrayLocationVariableExpressionNode(ArrayLocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseArrayAppendLocationVariableExpressionNode(ArrayAppendLocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        ListArrayLatticeElement list = n.getValueHeapLocationSet().stream().reduce((ListArrayLatticeElement)new ListArrayLatticeElementImpl(), (acc, h) -> acc.addLocation(h), (l1, l2) -> (ListArrayLatticeElement)l1.join(l2));
        ValueLatticeElement newTarget = new ValueLatticeElementImpl(list);
        return n.getTargetLocationSet().stream().reduce(l, (acc, h) -> acc.setHeapValue(c, h, (loc) -> acc.getHeapValue(c, loc).join(newTarget)), (l1, l2) -> l1.join(l2));
    }

    private AnalysisLatticeElement analyseArrayAppendExpressionNode(ArrayAppendExpressionNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement newValue = l.getStackValue(c, n.getValueName());
        newValue.print(new PrintStreamLatticePrinter(System.out));
        HeapLocation location = new HeapLocationImpl();
        ListArrayLatticeElement list = new ListArrayLatticeElementImpl().addLocation(location);
        ValueLatticeElement newTarget = new ValueLatticeElementImpl(list);
        return l.setHeapValue(c, location, (loc) -> newValue).setStackValue(c, n.getTargetName(), (name) -> l.getStackValue(c, n.getTargetName()).join(newTarget));
    }

    private AnalysisLatticeElement analyseNodeArrayInitExpressionNode(ArrayInitExpressionNode n, AnalysisLatticeElement l, Context c) {
        return l.setStackValue(c, n.getTargetName(), (name) -> new ValueLatticeElementImpl(ArrayLatticeElement.emptyArray));
    }

    private AnalysisLatticeElement analyseNodeLocalVariableExpressionNode(LocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        VariableName name = new VariableNameImpl(n.getVariableName());
        Set<HeapLocation> newLocations;
        if(c.isEmpty())
            newLocations = l.getGlobalsValue(c, name).getValues();
        else
            newLocations = l.getLocalsValue(c, name).getValues();
        n.getTargetLocationSet().clear(); // TODO: is this right?
        n.getTargetLocationSet().addAll(newLocations);

        return l;
    }
}
