package dk.au.cs.tapas.cfg.node;

import dk.au.cs.tapas.cfg.CallArgument;
import dk.au.cs.tapas.cfg.graph.FunctionGraph;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.Context;

/**
 * Created by budde on 4/26/15.
 */
public interface ResultNode extends Node{

    CallArgument getCallArgument();

    CallNode getCallNode();

    ExitNode getExitNode();

    FunctionGraph getFunctionGraph();

    void addCallLattice(Context context, AnalysisLatticeElement lattice);

    AnalysisLatticeElement getCallLattice(Context context);

}
