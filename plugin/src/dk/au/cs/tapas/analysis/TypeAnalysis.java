package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.LocationVariableExpressionNode;
import dk.au.cs.tapas.cfg.node.Node;
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
    public AnalysisLatticeElement analyse(Node n, AnalysisLatticeElement l, Context c) {
        if(n instanceof LocationVariableExpressionNode) {
            return analyseNode((LocationVariableExpressionNode)n, l, c);
        }

        // Fallback to identity function for unhandled nodes
        return l;
    }

    private AnalysisLatticeElement analyseNode(LocationVariableExpressionNode n, AnalysisLatticeElement l, Context c) {
        Set<HeapLocation> newLocations = l.getValue(c).getLocals().getValue(new VariableNameImpl(n.getVariableName())).getValues();
        //TODO: Do something if newLocations is empty
        n.getTargetLocationSet().addAll(newLocations);

        return l;
    }
}
