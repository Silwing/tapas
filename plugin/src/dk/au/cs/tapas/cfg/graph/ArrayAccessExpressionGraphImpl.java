package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.ArrayAccessExpression;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.ArrayReadExpressionNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayAccessExpressionGraphImpl extends ExpressionGraphImpl<ArrayAccessExpression>{
    public static PsiParser.ExpressionGraphGenerator<ArrayAccessExpression> generator = ArrayAccessExpressionGraphImpl::new;
    private final Node endNode;
    private final Graph entryGraph;

    public ArrayAccessExpressionGraphImpl(PsiParser psiParser, ArrayAccessExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);
        TemporaryVariableName arrayName = new TemporaryVariableNameImpl(), indexName = new TemporaryVariableNameImpl();
        endNode = new ArrayReadExpressionNodeImpl(graph.getEntryNode(), arrayName, indexName, name, element);

        Graph indexGraph = parser.parseExpression((PhpExpression) element.getIndex().getValue(), g -> g, indexName).generate(new NodeGraphImpl(endNode));

        entryGraph = parser.parseExpression((PhpExpression) element.getValue(), g -> g, arrayName).generate(indexGraph);

    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return entryGraph.getEntryNode();
    }

    @NotNull
    @Override
    public Node getExitNode() {
        return endNode;
    }
}
