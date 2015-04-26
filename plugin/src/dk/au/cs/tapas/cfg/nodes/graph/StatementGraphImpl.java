package dk.au.cs.tapas.cfg.nodes.graph;

import com.jetbrains.php.lang.psi.elements.Statement;
import dk.au.cs.tapas.cfg.PsiParser;

/**
 * Created by budde on 4/22/15.
 *
 */
public class StatementGraphImpl<T extends Statement> extends SkipGraphImpl{


    protected final Graph graph;
    protected final T element;
    protected final PsiParser parser;

    public StatementGraphImpl(PsiParser parser, T element, Graph targetGraph) {
        super(targetGraph);
        this.parser = parser;
        this.element = element;
        this.graph = targetGraph;
    }
}
