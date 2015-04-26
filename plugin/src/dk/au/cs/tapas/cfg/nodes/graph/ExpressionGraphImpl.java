package dk.au.cs.tapas.cfg.nodes.graph;

import com.jetbrains.php.lang.psi.elements.PhpExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/22/15.
 *
 */
public class ExpressionGraphImpl<T extends PhpExpression> extends SkipGraphImpl {
    
    protected final PsiParser parser;
    protected final T element;
    protected final Graph graph;
    protected final TemporaryVariableName name;

    public ExpressionGraphImpl(PsiParser psiParser, T element, Graph graph, TemporaryVariableName name) {
        super(graph);
        this.parser = psiParser;
        this.element = element;
        this.graph = graph;
        this.name = name;

    }
}
