package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.UnaryExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.IncrementDecrementOperationExpressionNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.UnaryOperationNodeImpl;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;
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
        if(operationText.contains("++") || operationText.contains("--")){

            Set<HeapLocation> operandLocations = new HashSet<>();
            operationNode = new IncrementDecrementOperationExpressionNodeImpl(graph.getEntryNode(), operandLocations, operationText, name);
            operandGraph = parser.parseVariableExpression((PhpExpression) element.getValue(), g -> g, operandLocations).generate(new NodeGraphImpl(operationNode));


        } else {
            TemporaryVariableName operandName = new TemporaryVariableNameImpl();
            operationNode = new UnaryOperationNodeImpl(graph.getEntryNode(), operandName, operationText, name);
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
