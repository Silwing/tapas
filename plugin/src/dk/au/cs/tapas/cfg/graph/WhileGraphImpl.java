package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.While;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.IfNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.SkipNodeImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 4/23/15.
 */
public class WhileGraphImpl extends StatementGraphImpl<While> {


    public final static PsiParser.StatementGraphGenerator<While> generator = WhileGraphImpl::new;
    private final SkipNodeImpl exitNode;
    private final Graph conditionGraph;

    public WhileGraphImpl(PsiParser parser, While element, Graph targetGraph) {
        super(parser, element, targetGraph);

        exitNode = new SkipNodeImpl(graph.getEntryNode(), element);
        TemporaryVariableName condition_result = new TemporaryVariableNameImpl();

        IfNodeImpl ifNode = new IfNodeImpl(condition_result, null, exitNode, element);
        conditionGraph = parser.parseExpression((PhpExpression) element.getCondition(), g -> g, condition_result).generate(new NodeGraphImpl(ifNode));
        Graph statementGraph = parser.parseElement(element.getStatement(), g -> g).generate(conditionGraph);
        ifNode.setTrueSuccessor(statementGraph.getEntryNode());

    }

    @NotNull
    @Override
    public Node getExitNode() {
        return exitNode;
    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return conditionGraph.getEntryNode();
    }
}
