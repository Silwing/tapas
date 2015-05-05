package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.Variable;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.ReadNodeImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.VariableNameImpl;
import org.jetbrains.annotations.NotNull;


/**
 * Created by budde on 4/26/15.
 *
 */
public class VariableReadExpressionGraphImpl extends ExpressionGraphImpl<Variable> {

    public final static PsiParser.ExpressionGraphGenerator<Variable> generator = VariableReadExpressionGraphImpl::new;
    private final ReadNodeImpl entryNode;


    public VariableReadExpressionGraphImpl(PsiParser psiParser, Variable element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);
        this.entryNode = new ReadNodeImpl(new VariableNameImpl(element.getName()), name, graph.getEntryNode());

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
