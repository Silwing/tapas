package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.FunctionReference;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.TemporaryVariableCallArgumentImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.ResultNodeImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 4/22/15.
 */
public class FunctionReferenceGraphImpl extends ExpressionGraphImpl<FunctionReference> {

    public static PsiParser.ExpressionGraphGenerator<FunctionReference> generator = FunctionReferenceGraphImpl::new;

    private final Graph subGraph;

    public FunctionReferenceGraphImpl(PsiParser parser, FunctionReference element, Graph graph, TemporaryVariableName name) {
        super(parser, element, graph, name);
        ResultNodeImpl endNode = new ResultNodeImpl(graph.getEntryNode(), new TemporaryVariableCallArgumentImpl(name), element);
        subGraph = new FunctionReferenceSubGraphImpl(parser, element, endNode);
    }



    @NotNull
    @Override
    public Node getEntryNode() {
        return subGraph.getEntryNode();
    }

    @NotNull
    @Override
    public Node getExitNode() {
        return subGraph.getExitNode();
    }
}
