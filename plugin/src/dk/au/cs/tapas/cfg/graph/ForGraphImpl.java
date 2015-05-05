package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.For;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.IfNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.SkipNodeImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 4/26/15.
 *
 */
public class ForGraphImpl extends StatementGraphImpl<For>{


    public static PsiParser.StatementGraphGenerator<For> generator = ForGraphImpl::new;
    private final SkipNodeImpl endNode;
    private final Graph initialGraph;

    public ForGraphImpl(PsiParser parser, For element, Graph targetGraph) {
        super(parser, element, targetGraph);

        endNode = new SkipNodeImpl(targetGraph.getEntryNode());

        TemporaryVariableName conditionName = new TemporaryVariableNameImpl();
        IfNodeImpl ifNode = new IfNodeImpl(conditionName, null, endNode);

        Graph conditionalExpression = parseExpressionList(element.getConditionalExpressions(), conditionName).generate(new NodeGraphImpl(ifNode));

        initialGraph = parseExpressionList(element.getInitialExpressions()).generate(conditionalExpression);

        Graph repeatedGraph = parseExpressionList(element.getRepeatedExpressions()).generate(conditionalExpression);

        Graph statementGraph = parser.parseElement(element.getStatement(), g -> g).generate(repeatedGraph);

        ifNode.setTrueSuccessor(statementGraph.getEntryNode());


    }

    PsiParser.GraphGenerator parseExpressionList(PhpPsiElement[] expressions){
        return parseExpressionList(expressions, new TemporaryVariableNameImpl());
    }

    PsiParser.GraphGenerator parseExpressionList(PhpPsiElement[] expressions, TemporaryVariableName name){
        return  parser.parseExpression(
                expressions.length == 0?
                        null:
                        (PhpExpression) expressions[0],
                g -> g,
                name);
    }


    @NotNull
    @Override
    public Node getExitNode() {
        return endNode;
    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return initialGraph.getEntryNode();
    }
}
