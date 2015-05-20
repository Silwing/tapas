package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.Variable;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.VariableReadLocationSetNodeImpl;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;
import dk.au.cs.tapas.lattice.VariableNameImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class VariableReferenceExpressionGraphImpl extends VariableVariableExpressionGraphImpl {

    public static PsiParser.ReferenceExpressionGraphGenerator<Variable> generator = VariableReferenceExpressionGraphImpl::new;

    private final VariableReadLocationSetNodeImpl node;

    public VariableReferenceExpressionGraphImpl(PsiParser parser, Variable element, Graph graph, TemporaryHeapVariableName locations) {
        super(parser, element, graph, locations);

        node = new VariableReadLocationSetNodeImpl(graph.getEntryNode(), new VariableNameImpl(element.getName()), locations, element);

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
