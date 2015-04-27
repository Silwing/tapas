package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.Function;
import com.jetbrains.php.lang.psi.elements.Parameter;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.*;
import org.jetbrains.annotations.Nullable;

/**
 * Created by budde on 4/27/15.
 */
public class FunctionGraphImpl implements FunctionGraph {
    private final boolean[] arguments;
    private final Node exitNode;
    private final Node entryNode;

    public FunctionGraphImpl(PsiParser parser, Function element) {
        Parameter[] parameters = element.getParameters();
        arguments = new boolean[parameters.length];

        for(int i = 0; i < parameters.length; i ++){
            arguments[i] = parameters[i].isPassByRef();
        }
        exitNode = new EndNodeImpl();

        Graph body = parser.parseElement((PhpPsiElement) element.getLastChild(), g -> g).generate(new NodeGraphImpl(exitNode));

        entryNode = new StartNodeImpl(body.getEntryNode());



    }

    @Override
    public boolean[] getArguments() {
        return arguments;
    }

    @Nullable
    @Override
    public Node getExitNode() {
        return exitNode;
    }

    @Nullable
    @Override
    public Node getEntryNode() {
        return entryNode;
    }

}
