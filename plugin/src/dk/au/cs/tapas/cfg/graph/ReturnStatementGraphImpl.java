package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.*;
import dk.au.cs.tapas.cfg.*;
import dk.au.cs.tapas.cfg.node.ExitNode;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.ReadConstNodeImpl;
import dk.au.cs.tapas.cfg.node.SkipNodeImpl;
import dk.au.cs.tapas.lattice.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/28/15.
 */
public class ReturnStatementGraphImpl extends StatementGraphImpl<PhpReturn>{


    public static PsiParser.StatementGraphGenerator<PhpReturn> generator = ReturnStatementGraphImpl::new;
    private final Node entryNode, exitNode;

    public ReturnStatementGraphImpl(PsiParser parser, PhpReturn element, Graph targetGraph) {
        super(parser, element, targetGraph);
        FunctionGraph currentFunctionGraph = parser.getCurrentFunctionGraph();
        if(currentFunctionGraph == null){
            entryNode = exitNode = targetGraph.getEntryNode();
            return;
        }
        PhpPsiElement firstBorn = element.getFirstPsiChild();
        Graph expressionGraph;
        ExitNode functionExitNode = currentFunctionGraph.getExitNode();
        Graph exitNodeGraph = new NodeGraphImpl(functionExitNode);
        if(firstBorn != null) {
            if (currentFunctionGraph.isAliasReturn() && isReferenceable(firstBorn)) {
                TemporaryHeapVariableName locationSet = new TemporaryHeapVariableNameImpl();
                expressionGraph = parser.parseReferenceExpression((PhpExpression) firstBorn, g -> g, locationSet).generate(exitNodeGraph);
                functionExitNode.addCallArgument(new TemporaryHeapVariableCallArgumentImpl(locationSet));
            } else {
                TemporaryVariableName name = new TemporaryVariableNameImpl();
                expressionGraph = parser.parseExpression((PhpExpression) firstBorn, g -> g, name).generate(exitNodeGraph);
                functionExitNode.addCallArgument(new TemporaryVariableCallArgumentImpl(name));
            }
            entryNode = expressionGraph.getEntryNode();

        } else {
            TemporaryVariableName name = new TemporaryVariableNameImpl();
            entryNode = new ReadConstNodeImpl(functionExitNode, new NullConstantImpl(), name, element);
            functionExitNode.addCallArgument(new TemporaryVariableCallArgumentImpl(name));

        }
        exitNode = new SkipNodeImpl(targetGraph.getEntryNode(), element);

    }

    private boolean isReferenceable(PhpPsiElement firstBorn) {
        return firstBorn instanceof Variable || firstBorn instanceof FunctionReference || firstBorn instanceof ArrayAccessExpression;
    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return entryNode;
    }

    @NotNull
    @Override
    public Node getExitNode() {
        return exitNode;
    }
}
