package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.AssignmentExpression;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.PsiParserImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.AssignmentNodeImpl;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/22/15.
 *
 */
public class AssignmentExpressionGraphImpl extends ExpressionGraphImpl<AssignmentExpression>{

    public static PsiParserImpl.ExpressionGraphGenerator<AssignmentExpression> generator = AssignmentExpressionGraphImpl::new;
    private final Graph variableGraph;
    private final AssignmentNodeImpl assignmentNode;


    public AssignmentExpressionGraphImpl(PsiParser psiParser, AssignmentExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);
        Set<HeapLocation> locations = new HashSet<>();
        TemporaryVariableName valueName = new TemporaryVariableNameImpl();
        this.assignmentNode = new AssignmentNodeImpl(graph.getEntryNode(), name, locations, valueName);
        Graph valueGraph = psiParser.parseExpression((PhpExpression) element.getValue(), g -> g, valueName).generate(new NodeGraphImpl(assignmentNode));
        this.variableGraph = psiParser.parseVariableExpression((PhpExpression) element.getVariable(), g -> g, locations).generate(valueGraph);
    }


    @Override
    public Node getExitNode() {
        return assignmentNode;
    }

    @Override
    public Node getEntryNode() {
        return variableGraph.getEntryNode();
    }
}
