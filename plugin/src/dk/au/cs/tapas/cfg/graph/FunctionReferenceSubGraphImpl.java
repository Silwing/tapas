package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.cfg.*;
import dk.au.cs.tapas.cfg.node.CallNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.ResultNodeImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 4/27/15.
 */
class FunctionReferenceSubGraphImpl extends GraphImpl{

    private final Graph entryGraph;
    private final PsiParser parser;
    private final ResultNodeImpl endNode;

    public FunctionReferenceSubGraphImpl(PsiParser parser, FunctionReference element, ResultNodeImpl endNode) {
        this.endNode = endNode;
        this.parser = parser;
        FunctionGraph functionGraph = parser.getFunctions().get(element.getFQN());
        boolean[] arguments = functionGraph.getArguments();
        int argumentsSize = element.getParameters().length;
        CallArgument[] callArguments = new CallArgument[argumentsSize];
        for (int i = 0; i < argumentsSize; i++) {
            if (arguments.length > i && arguments[i]) {
                callArguments[i] = new TemporaryHeapVariableCallArgumentImpl();
            } else {
                callArguments[i] = new TemporaryVariableCallArgumentImpl();
            }
        }
        CallNodeImpl callNode = new CallNodeImpl(endNode, element.getFQN(), callArguments, endNode, element);
        Graph callNodeGraph = new NodeGraphImpl(callNode);
        if (element.getParameters().length > 0) {
            entryGraph = buildParameterGraph((PhpPsiElement) element.getParameters()[0], callArguments).generate(callNodeGraph);
        } else {
            entryGraph = callNodeGraph;
        }
        callNode.setFunctionGraph(functionGraph);
        endNode.setExitNode(functionGraph.getExitNode());
        endNode.setFunctionGraph(functionGraph);
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
        if (callArgument instanceof TemporaryHeapVariableCallArgument) {
            return parser.parseReferenceExpression(
                    (PhpExpression) parameter,
                    buildParameterGraph(parameter.getNextPsiSibling(), callArguments, counter + 1),
                    ((TemporaryHeapVariableCallArgument) callArgument).getArgument());
        } else if (callArgument instanceof TemporaryVariableCallArgument) {
            return parser.parseExpression(
                    (PhpExpression) parameter,
                    buildParameterGraph(parameter.getNextPsiSibling(), callArguments, counter + 1),
                    ((TemporaryVariableCallArgument) callArgument).getArgument());
        }
        return g -> g;
    }

    @NotNull
    @Override
    public Node getExitNode() {
        return endNode;
    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return entryGraph.getEntryNode();
    }
}
