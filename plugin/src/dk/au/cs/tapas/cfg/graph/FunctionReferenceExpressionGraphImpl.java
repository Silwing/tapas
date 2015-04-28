package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.FunctionReference;
import dk.au.cs.tapas.cfg.*;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.ResultNodeImpl;
import dk.au.cs.tapas.lattice.HeapLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class FunctionReferenceExpressionGraphImpl extends ReferenceExpressionGraphImpl<FunctionReference>{
    public static PsiParser.ReferenceExpressionGraphGenerator<FunctionReference> generator = FunctionReferenceExpressionGraphImpl::new;

    private final Graph subGraph;

    public FunctionReferenceExpressionGraphImpl(PsiParser parser, FunctionReference element, Graph graph, Set<HeapLocation> locations) {
        super(parser, element, graph, locations);
        ResultNodeImpl endNode = new ResultNodeImpl(graph.getEntryNode(), new HeapLocationSetCallArgumentImpl(locations));
        subGraph = new FunctionReferenceSubGraphImpl(parser, element, endNode);
    }



    @NotNull
    @Override
    public Node getEntryNode() {
        return subGraph.getEntryNode();
    }

    @NotNull
    @Override
    public Node getExitNode() {
        return subGraph.getExitNode();
    }
}
