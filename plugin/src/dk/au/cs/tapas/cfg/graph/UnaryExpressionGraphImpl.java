package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.UnaryExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.UnaryOperator;
import dk.au.cs.tapas.cfg.node.IncrementDecrementOperationStackOperationNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.UnaryOperationNodeImpl;
import dk.au.cs.tapas.lattice.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/22/15.
 *
 */
public class UnaryExpressionGraphImpl extends ExpressionGraphImpl<UnaryExpression>{

    public static PsiParser.ExpressionGraphGenerator<UnaryExpression> generator = UnaryExpressionGraphImpl::new;
    private final Node operationNode;
    private final Graph operandGraph;

    public UnaryExpressionGraphImpl(PsiParser psiParser, UnaryExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);

        String operationText = element.getOperation().getText();
        if(!element.getText().startsWith(operationText)){
            operationText = "_"+operationText;
        } else {
            operationText  = operationText + "_";
        }
        UnaryOperator operator = UnaryOperator.fromString(operationText);
        if(operationText.contains("++") || operationText.contains("--")){

            TemporaryHeapVariableName operandLocations = new TemporaryHeapVariableNameImpl();
            operationNode = new IncrementDecrementOperationStackOperationNodeImpl(graph.getEntryNode(), operandLocations, operator, name, element);
            operandGraph = parser.parseVariableExpression((PhpExpression) element.getValue(), g -> g, operandLocations).generate(new NodeGraphImpl(operationNode));


        } else {
            TemporaryVariableName operandName = new TemporaryVariableNameImpl();
            operationNode = new UnaryOperationNodeImpl(graph.getEntryNode(), operandName, operator, name, element);
            operandGraph = parser.parseExpression((PhpExpression) element.getValue(), g -> g, operandName).generate(new NodeGraphImpl(operationNode));

        }





    }


    @NotNull
    @Override
    public Node getExitNode() {
        return operationNode;
    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return operandGraph.getEntryNode();
    }
}
