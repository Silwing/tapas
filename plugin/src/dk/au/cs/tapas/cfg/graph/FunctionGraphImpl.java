package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.Function;
import com.jetbrains.php.lang.psi.elements.Parameter;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.analysis.ContextNodePair;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class FunctionGraphImpl  implements FunctionGraph {
    private final boolean[] arguments;
    private final ExitNode exitNode;
    private final StartNode entryNode;
    private final Function element;

    public FunctionGraphImpl(PsiParser parser, Function element) {
        Parameter[] parameters = element.getParameters();
        this.element = element;
        arguments = new boolean[parameters.length];

        for(int i = 0; i < parameters.length; i ++){
            arguments[i] = parameters[i].isPassByRef();
        }
        exitNode = new ExitNodeImpl();

        parser.setCurrentFunctionGraph(this);

        Graph body = parser.parseElement((PhpPsiElement) element.getLastChild(), g -> g).generate(new NodeGraphImpl(exitNode));

        parser.setCurrentFunctionGraph(null);

        entryNode = new StartNodeImpl(body.getEntryNode());



    }

    @Override
    public boolean[] getArguments() {
        return arguments;
    }

    @Override
    public boolean isAliasReturn() {
        return element.getText().replaceAll("\\s", "").startsWith("function&");
    }

    @NotNull
    @Override
    public ExitNode getExitNode() {
        return exitNode;
    }

    @NotNull
    @Override
    public StartNode getEntryNode() {
        return entryNode;
    }

    @Override
    public Set<ContextNodePair> getFlow(ContextNodePair cn) {
        return null;
    }

    @Override
    public Set<Node> getNodes() {
        return null;
    }

}
