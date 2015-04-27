package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.BinaryExpression;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.BinaryOperationNodeImpl;
import dk.au.cs.tapas.cfg.node.IfNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.ShortCircuitBinaryOperationNodeImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

/**
 * Created by budde on 4/22/15.
 *
 */
public class BinaryExpressionGraphImpl extends ExpressionGraphImpl<BinaryExpression> {
    public static PsiParser.ExpressionGraphGenerator<BinaryExpression> generator = BinaryExpressionGraphImpl::new;
    private final BinaryOperationNodeImpl endNode;
    private final Graph leftGraph;

    public BinaryExpressionGraphImpl(PsiParser psiParser, BinaryExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);

        TemporaryVariableName leftName = new TemporaryVariableNameImpl(), rightName = new TemporaryVariableNameImpl();
        String operator = element.getOperation().getText();
        if(operator.equals("&&") || operator.equals("||")){
            endNode = new ShortCircuitBinaryOperationNodeImpl(graph.getEntryNode(), leftName, operator, rightName, name);
            Graph rightGraph = parser.parseExpression((PhpExpression) element.getRightOperand(), g -> g, rightName).generate(new NodeGraphImpl(endNode));
            Node ifNode = new IfNodeImpl(leftName, rightGraph.getEntryNode(), endNode);
            leftGraph = parser.parseExpression((PhpExpression) element.getLeftOperand(), g -> g, leftName).generate(new NodeGraphImpl(ifNode));

        } else {
            endNode = new BinaryOperationNodeImpl(graph.getEntryNode(), leftName, operator, rightName, name);

            Graph rightGraph = parser.parseExpression((PhpExpression) element.getRightOperand(), g -> g, rightName).generate(new NodeGraphImpl(endNode));
            leftGraph = parser.parseExpression((PhpExpression) element.getLeftOperand(), g -> g, leftName).generate(rightGraph);
        }


    }

    @Override
    public Node getExitNode() {
        return endNode;
    }

    @Override
    public Node getEntryNode() {
        return leftGraph.getEntryNode();
    }
}
