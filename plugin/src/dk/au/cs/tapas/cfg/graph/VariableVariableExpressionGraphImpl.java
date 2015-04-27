package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.Variable;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.LocationVariableExpressionNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class VariableVariableExpressionGraphImpl extends VariableExpressionGraphImpl<Variable>{

    public static PsiParser.VariableExpressionGraphGenerator<Variable> generator = VariableVariableExpressionGraphImpl::new;
    private final LocationVariableExpressionNodeImpl node;

    public VariableVariableExpressionGraphImpl(PsiParser parser, Variable element, Graph graph, Set<HeapLocation> locations) {
        super(parser, element, graph, locations);

        node = new LocationVariableExpressionNodeImpl(graph.getEntryNode(), element.getFQN(), locations);

    }

    @Override
    public Node getEntryNode() {
        return node;
    }

    @Override
    public Node getExitNode() {
        return node;
    }
}
