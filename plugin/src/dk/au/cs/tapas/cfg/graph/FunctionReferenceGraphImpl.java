package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.cfg.*;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by budde on 4/22/15.
 */
public class FunctionReferenceGraphImpl extends ExpressionGraphImpl<FunctionReference> {

    public static PsiParser.ExpressionGraphGenerator<FunctionReference> generator = FunctionReferenceGraphImpl::new;

    private final Graph subGraph;

    public FunctionReferenceGraphImpl(PsiParser parser, FunctionReference element, Graph graph, TemporaryVariableName name) {
        super(parser, element, graph, name);
        ResultNodeImpl endNode = new ResultNodeImpl(graph.getEntryNode(), new TemporaryVariableCallArgumentImpl(name));
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
