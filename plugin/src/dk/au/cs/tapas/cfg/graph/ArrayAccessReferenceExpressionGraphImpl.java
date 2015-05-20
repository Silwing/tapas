package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.ArrayAccessExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayAccessReferenceExpressionGraphImpl extends ReferenceExpressionGraphImpl<ArrayAccessExpression> {


    public static PsiParser.ReferenceExpressionGraphGenerator<ArrayAccessExpression> generator = ArrayAccessReferenceExpressionGraphImpl::new;

    private final Graph subGraph;

    public ArrayAccessReferenceExpressionGraphImpl(PsiParser parser, ArrayAccessExpression element, Graph graph, TemporaryHeapVariableName locations) {
        super(parser, element, graph, locations);
        subGraph = new ArrayAccessExpressionSubGraphImpl(parser, element, graph, locations, (exp, loc) -> parser.parseReferenceExpression(exp, g -> g, loc));

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
