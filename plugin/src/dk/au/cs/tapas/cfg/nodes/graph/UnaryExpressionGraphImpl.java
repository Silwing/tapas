package dk.au.cs.tapas.cfg.nodes.graph;

import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.UnaryExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.nodes.node.Node;
import dk.au.cs.tapas.cfg.nodes.node.UnaryOperationNodeImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

/**
 * Created by budde on 4/22/15.
 *
 */
public class UnaryExpressionGraphImpl extends ExpressionGraphImpl<UnaryExpression>{

    public static PsiParser.ExpressionGraphGenerator<UnaryExpression> generator = UnaryExpressionGraphImpl::new;
    private final UnaryOperationNodeImpl operationNode;
    private final Graph operandGraph;

    public UnaryExpressionGraphImpl(PsiParser psiParser, UnaryExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);

        TemporaryVariableName operandName = new TemporaryVariableNameImpl();
        operationNode = new UnaryOperationNodeImpl(graph.getEntryNode(), operandName, element.getOperation().getText(), name);


        operandGraph = parser.parseExpression((PhpExpression) element.getValue(), g -> g, operandName).generate(new NodeGraphImpl(operationNode));




    }


    @Override
    public Node getExitNode() {
        return operationNode;
    }

    @Override
    public Node getEntryNode() {
        return operandGraph.getEntryNode();
    }
}
