package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.Variable;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.VariableReadNodeImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.VariableNameImpl;
import org.jetbrains.annotations.NotNull;


/**
 * Created by budde on 4/26/15.
 *
 */
public class VariableReadExpressionGraphImpl extends ExpressionGraphImpl<Variable> {

    public final static PsiParser.ExpressionGraphGenerator<Variable> generator = VariableReadExpressionGraphImpl::new;
    private final VariableReadNodeImpl entryNode;


    public VariableReadExpressionGraphImpl(PsiParser psiParser, Variable element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);
        this.entryNode = new VariableReadNodeImpl(new VariableNameImpl(element.getName()), name, graph.getEntryNode(), element);

    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return entryNode;
    }

    @NotNull
    @Override
    public Node getExitNode() {
        return entryNode;
    }
}
