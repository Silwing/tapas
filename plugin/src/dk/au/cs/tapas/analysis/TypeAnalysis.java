package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.VariableNameImpl;

import java.util.Set;

/**
 * Created by Silwing on 28-04-2015.
 *
 */
public class TypeAnalysis implements Analysis {
    @Override
    public AnalysisLatticeElement getEmptyLattice() {
        return null;
    }

    @Override
    public AnalysisLatticeElement getStartLattice() {
        return null;
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
        if(n instanceof ReferenceAssignemntNode){
            return analyseReferenceAssignemntNode((ReferenceAssignemntNode) n, l, c);
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
        return null;
    }

    private AnalysisLatticeElement analyseStartNode(StartNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseShortCircuitBinaryOperationNode(ShortCircuitBinaryOperationNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseResultNode(ResultNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseReferenceAssignemntNode(ReferenceAssignemntNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseReadNode(ReadNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseReadConstNode(ReadConstNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseIncrementDecrementOperationExpressionNode(IncrementDecrementOperationExpressionNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseIfNode(IfNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseExitNode(ExitNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseEndNode(EndNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseCallNode(CallNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseBinaryOperationNode(BinaryOperationNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseAssignmentNode(AssignmentNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseArrayWriteExpressionNode(ArrayWriteExpressionNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseArrayReadExpressionNode(ArrayReadExpressionNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseArrayLocationVariableExpressionNode(ArrayLocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseArrayAppendLocationVariableExpressionNode(ArrayAppendLocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseArrayAppendExpressionNode(ArrayAppendExpressionNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseNodeArrayInitExpressionNode(ArrayInitExpressionNode n, AnalysisLatticeElement l, Context c) {
        return null;
    }

    private AnalysisLatticeElement analyseNodeLocalVariableExpressionNode(LocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        Set<HeapLocation> newLocations = l.getValue(c).getLocals().getValue(new VariableNameImpl(n.getVariableName())).getValues();
        n.getTargetLocationSet().clear(); // TODO: is this right?
        n.getTargetLocationSet().addAll(newLocations);

        return l;
    }
}
