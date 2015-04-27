package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.ArrayAccessExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayAccessVariableExpressionGraphImpl extends VariableExpressionGraphImpl<ArrayAccessExpression>{

    public static PsiParser.VariableExpressionGraphGenerator<ArrayAccessExpression> generator = ArrayAccessVariableExpressionGraphImpl::new;
    private final Graph subGraph;

    public ArrayAccessVariableExpressionGraphImpl(PsiParser parser, ArrayAccessExpression element, Graph graph, Set<HeapLocation> locations) {
        super(parser, element, graph, locations);
        subGraph = new ArrayAccessExpressionSubGraphImpl(parser, element, graph, locations, (exp, loc) -> parser.parseVariableExpression(exp, g -> g, loc));

    }


    @Override
    public Node getEntryNode() {
        return subGraph.getEntryNode();
    }

    @Override
    public Node getExitNode() {
        return subGraph.getExitNode();
    }
}