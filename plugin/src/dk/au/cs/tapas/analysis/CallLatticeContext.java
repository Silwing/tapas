package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.CallNode;
import dk.au.cs.tapas.lattice.Context;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;

import java.util.List;

/**
 * Created by budde on 5/10/15.
 */
public interface CallLatticeContext {

    List<Pair<CallNode, AnalysisLatticeElement>> getNodes();

    CallLatticeContext addNode(CallNode node, AnalysisLatticeElement analysis);

    CallLatticeContext popNode();

    Pair<CallNode, AnalysisLatticeElement> getLastCallNode();

    boolean isEmpty();

    Context toContext();



}
