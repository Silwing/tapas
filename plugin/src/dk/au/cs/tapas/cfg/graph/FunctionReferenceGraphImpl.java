package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.cfg.*;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

/**
 * Created by budde on 4/22/15.
 */
public class FunctionReferenceGraphImpl extends ExpressionGraphImpl<FunctionReference> {

    public static PsiParser.ExpressionGraphGenerator<FunctionReference> generator = FunctionReferenceGraphImpl::new;
    private final ResultNodeImpl endNode;
    private final Graph entryGraph;

    public FunctionReferenceGraphImpl(PsiParser psiParser, FunctionReference element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);
        TemporaryVariableName targetName = new TemporaryVariableNameImpl();
        endNode = new ResultNodeImpl(graph.getEntryNode(), targetName);

        FunctionGraph functionGraph = parser.getFunctions().get(element.getFQN());
        boolean[] arguments = functionGraph.getArguments();
        int argumentsSize = element.getParameters().length;
        CallArgument[] callArguments = new CallArgument[argumentsSize];
        for (int i = 0; i < argumentsSize; i++) {
            if (arguments.length > i && arguments[i]) {
                callArguments[i] = new HeapLocationSetCallArgumentImpl();
            } else {
                callArguments[i] = new TemporaryVariableCallArgumentImpl();
            }
        }
        CallNode callNode = new CallNodeImpl(endNode, element.getFQN(), callArguments, endNode);
        Graph callNodeGraph = new NodeGraphImpl(callNode);
        if (element.getParameters().length > 0) {
            entryGraph = buildParameterGraph((PhpPsiElement) element.getParameters()[0], callArguments).generate(callNodeGraph);
        } else {
            entryGraph = callNodeGraph;
        }

        endNode.setCallNode(callNode);
    }

    private PsiParser.GraphGenerator buildParameterGraph(PhpPsiElement parameter, CallArgument[] callArguments) {
        return buildParameterGraph(parameter, callArguments, 0);
    }

    private PsiParser.GraphGenerator buildParameterGraph(PhpPsiElement parameter, CallArgument[] callArguments, int counter) {
        if (parameter == null) {
            return g -> g;
        }
        CallArgument callArgument = callArguments[counter];
        if (callArgument instanceof HeapLocationSetCallArgument) {
            return parser.parseVariableExpression(
                    (PhpExpression) parameter,
                    buildParameterGraph(parameter.getNextPsiSibling(), callArguments, counter + 1),
                    ((HeapLocationSetCallArgument) callArgument).getArgument());
        } else if (callArgument instanceof TemporaryVariableCallArgument) {
            return parser.parseExpression(
                    (PhpExpression) parameter,
                    buildParameterGraph(parameter.getNextPsiSibling(), callArguments, counter + 1),
                    ((TemporaryVariableCallArgument) callArgument).getArgument());
        }
        return g -> g;
    }


    @Override
    public Node getEntryNode() {
        return entryGraph.getEntryNode();
    }

    @Override
    public Node getExitNode() {
        return endNode;
    }
}
