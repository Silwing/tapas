package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.Global;
import com.jetbrains.php.lang.psi.elements.Variable;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.GlobalNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.VariableName;
import dk.au.cs.tapas.lattice.VariableNameImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 5/7/15.
 */
public class GlobalGraphImpl extends StatementGraphImpl<Global> {
    public static PsiParser.StatementGraphGenerator<Global> generator = GlobalGraphImpl::new;
    private final GlobalNodeImpl node;

    public GlobalGraphImpl(PsiParser parser, Global element, Graph targetGraph) {
        super(parser, element, targetGraph);
        VariableName[] names = new VariableName[element.getVariables().length];
        int counter = 0;
        for (Variable variable : element.getVariables()) {
            names[counter] = new VariableNameImpl(variable);

            counter++;
        }

        this.node = new GlobalNodeImpl(targetGraph.getEntryNode(), names, element);

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
