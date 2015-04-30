package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.*;

import java.util.Set;

/**
 * Created by Silwing on 28-04-2015.
 *
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
        if(n instanceof LocationVariableExpressionNode) {
            return analyseNodeLocalVariableExpressionNode((LocationVariableExpressionNode) n, l, c);
        }
        if(n instanceof ArrayInitExpressionNode){
            return analyseNodeArrayInitExpressionNode((ArrayInitExpressionNode) n, l, c);
        }
        if(n instanceof ArrayAppendExpressionNode){
            return analyseArrayAppendExpressionNode((ArrayAppendExpressionNode) n, l, c);
        }
        if(n instanceof ArrayAppendLocationVariableExpressionNode){
            return analyseArrayAppendLocationVariableExpressionNode((ArrayAppendLocationVariableExpressionNode) n, l, c);
        }
        if(n instanceof ArrayLocationVariableExpressionNode){
            return analyseArrayLocationVariableExpressionNode((ArrayLocationVariableExpressionNode) n, l, c);
        }
        if(n instanceof ArrayReadExpressionNode){
            return analyseArrayReadExpressionNode((ArrayReadExpressionNode) n, l, c);
        }
        if(n instanceof ArrayWriteExpressionNode){
            return analyseArrayWriteExpressionNode((ArrayWriteExpressionNode) n, l, c);
        }
        if(n instanceof AssignmentNode){
            return analyseAssignmentNode((AssignmentNode) n, l, c);
        }
        if(n instanceof ShortCircuitBinaryOperationNode){
            return analyseShortCircuitBinaryOperationNode((ShortCircuitBinaryOperationNode) n, l, c);
        }
        if(n instanceof BinaryOperationNode){
            return analyseBinaryOperationNode((BinaryOperationNode) n, l, c);
        }
        if(n instanceof CallNode){
            return analyseCallNode((CallNode) n, l, c);
        }
        if(n instanceof EndNode){
            return analyseEndNode((EndNode) n, l, c);
        }
        if(n instanceof ExitNode){
            return analyseExitNode((ExitNode) n, l, c);
        }
        if(n instanceof IfNode){
            return analyseIfNode((IfNode) n, l, c);
        }
        if(n instanceof IncrementDecrementOperationExpressionNode){
            return  analyseIncrementDecrementOperationExpressionNode((IncrementDecrementOperationExpressionNode) n, l, c);
        }
        if(n instanceof ReadConstNode){
            return analyseReadConstNode((ReadConstNode) n, l, c);
        }
        if(n instanceof ReadNode){
            return analyseReadNode((ReadNode) n, l, c);
        }
        if(n instanceof ReferenceAssignmentNode){
            return analyseReferenceAssignmentNode((ReferenceAssignmentNode) n, l, c);
        }
        if(n instanceof ResultNode){
            return analyseResultNode((ResultNode) n, l, c);
        }
        if(n instanceof StartNode){
            return analyseStartNode((StartNode) n, l, c);
        }
        if(n instanceof UnaryOperationNode){
            return analyseUnaryOperationNode((UnaryOperationNode) n, l,c);
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

    private AnalysisLatticeElement analyseResultNode(ResultNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseReferenceAssignmentNode(ReferenceAssignmentNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseReadNode(ReadNode n, AnalysisLatticeElement l, Context c) {
        return l;
    }

    private AnalysisLatticeElement analyseReadConstNode(ReadConstNode n, AnalysisLatticeElement l, Context c) {
        return l;
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
        return l;
    }

    private AnalysisLatticeElement analyseArrayAppendExpressionNode(ArrayAppendExpressionNode n, AnalysisLatticeElement l, Context c) {
        ValueLatticeElement newValue = l.getStackValue(c, n.getValueName());
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
        Set<HeapLocation> newLocations = l.getLocalsValue(c, name).getValues();
        if(newLocations.isEmpty() && !c.isEmpty())
            newLocations = l.getGlobalsValue(c, name).getValues();
        n.getTargetLocationSet().clear(); // TODO: is this right?
        n.getTargetLocationSet().addAll(newLocations);

        return l;
    }
}
