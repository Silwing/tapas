package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.While;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.IfNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.SkipNodeImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

/**
 * Created by budde on 4/23/15.
 */
public class WhileGraphImpl extends StatementGraphImpl<While> {


    public final static PsiParser.StatementGraphGenerator<While> generator = WhileGraphImpl::new;
    private final SkipNodeImpl exitNode;
    private final Graph conditionGraph;

    public WhileGraphImpl(PsiParser parser, While element, Graph targetGraph) {
        super(parser, element, targetGraph);

        exitNode = new SkipNodeImpl(graph.getEntryNode());
        TemporaryVariableName condition_result = new TemporaryVariableNameImpl();

        IfNodeImpl ifNode = new IfNodeImpl(condition_result, exitNode, null);
        conditionGraph = parser.parseExpression((PhpExpression) element.getCondition(), g -> g, condition_result).generate(new NodeGraphImpl(ifNode));
        Graph statementGraph = parser.parseElement(element.getStatement(), g -> g).generate(conditionGraph);
        ifNode.setSuccessor2(statementGraph.getEntryNode());

    }

    @Override
    public Node getExitNode() {
        return exitNode;
    }

    @Override
    public Node getEntryNode() {
        return conditionGraph.getEntryNode();
    }
}
