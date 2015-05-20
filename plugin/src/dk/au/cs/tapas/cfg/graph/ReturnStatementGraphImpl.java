package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import com.jetbrains.php.lang.psi.elements.PhpReturn;
import dk.au.cs.tapas.cfg.TemporaryHeapVariableCallArgumentImpl;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgumentImpl;
import dk.au.cs.tapas.cfg.node.ExitNode;
import dk.au.cs.tapas.cfg.node.Node;
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
            if (currentFunctionGraph.isAliasReturn()) {
                TemporaryHeapVariableName locationSet = new TemporaryHeapVariableNameImpl();
                expressionGraph = parser.parseVariableExpression((PhpExpression) firstBorn, g -> g, locationSet).generate(exitNodeGraph);
                functionExitNode.addCallArgument(new TemporaryHeapVariableCallArgumentImpl(locationSet));
            } else {
                TemporaryVariableName name = new TemporaryVariableNameImpl();
                expressionGraph = parser.parseExpression((PhpExpression) firstBorn, g -> g, name).generate(exitNodeGraph);
                functionExitNode.addCallArgument(new TemporaryVariableCallArgumentImpl(name));
            }
            entryNode = expressionGraph.getEntryNode();

        } else {
            entryNode = functionExitNode;
        }
        exitNode = functionExitNode;

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
