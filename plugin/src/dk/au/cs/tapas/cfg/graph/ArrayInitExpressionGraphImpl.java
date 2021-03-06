package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression;
import com.jetbrains.php.lang.psi.elements.ArrayHashElement;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.ArrayAppendStackOperationNodeImpl;
import dk.au.cs.tapas.cfg.node.ArrayInitStackOperationNodeImpl;
import dk.au.cs.tapas.cfg.node.ArrayWriteStackOperationNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 4/26/15.
 */
public class ArrayInitExpressionGraphImpl extends ExpressionGraphImpl<ArrayCreationExpression> {
    public static PsiParser.ExpressionGraphGenerator<ArrayCreationExpression> generator = ArrayInitExpressionGraphImpl::new;
    private final Graph childGraph;
    private final ArrayInitStackOperationNodeImpl entryNode;

    public ArrayInitExpressionGraphImpl(PsiParser psiParser, ArrayCreationExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);

        childGraph = buildGraphFromSiblings(element.getFirstPsiChild(), name, graph);

        entryNode = new ArrayInitStackOperationNodeImpl(childGraph.getEntryNode(), name, element);

    }

    @NotNull
    @Override
    public Node getExitNode() {
        return childGraph.getExitNode();
    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return entryNode;
    }

    private Graph buildGraphFromSiblings(PhpPsiElement element, TemporaryVariableName target, Graph carry) {
        if (element == null) {
            return carry;
        }

        Graph targetGraph = buildGraphFromSiblings(element.getNextPsiSibling(), target, carry);
        if (element instanceof ArrayHashElement) {
            TemporaryVariableName keyName = new TemporaryVariableNameImpl(), valueName = new TemporaryVariableNameImpl();

            Node writeNode = new ArrayWriteStackOperationNodeImpl(targetGraph.getEntryNode(), keyName, valueName, target, element);
            Graph valueGraph = parser.parseExpression((PhpExpression) ((ArrayHashElement) element).getValue(), g -> g, valueName).generate(new NodeGraphImpl(writeNode));
            return parser.parseExpression((PhpExpression) ((ArrayHashElement) element).getKey(), g -> g, keyName).generate(valueGraph);
        }
        TemporaryVariableName valueName = new TemporaryVariableNameImpl();
        Node appendNode = new ArrayAppendStackOperationNodeImpl(targetGraph.getEntryNode(), valueName, target, element);
        return parser.parseExpression((PhpExpression) element.getFirstPsiChild(), g -> g, valueName).generate(new NodeGraphImpl(appendNode));
    }
}
