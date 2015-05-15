package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.Variable;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.VariableLocationSetNodeImpl;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.VariableNameImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class VariableVariableExpressionGraphImpl extends VariableExpressionGraphImpl<Variable>{

    public static PsiParser.VariableExpressionGraphGenerator<Variable> generator = VariableVariableExpressionGraphImpl::new;
    private final VariableLocationSetNodeImpl node;

    public VariableVariableExpressionGraphImpl(PsiParser parser, Variable element, Graph graph, Set<HeapLocation> locations) {
        super(parser, element, graph, locations);

        node = new VariableLocationSetNodeImpl(graph.getEntryNode(), new VariableNameImpl(element.getName()), locations, element);

    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return node;
    }

    @NotNull
    @Override
    public Node getExitNode() {
        return node;
    }
}
