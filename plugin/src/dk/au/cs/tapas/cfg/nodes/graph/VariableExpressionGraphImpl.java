package dk.au.cs.tapas.cfg.nodes.graph;

import com.jetbrains.php.lang.psi.elements.PhpExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public abstract class VariableExpressionGraphImpl<T extends PhpExpression> extends SkipGraphImpl{

    protected final PsiParser parser;
    protected final T element;
    protected final Graph graph;
    protected final Set<HeapLocation> locations;


    public VariableExpressionGraphImpl(PsiParser parser, T element, Graph graph, Set<HeapLocation> locations) {
        super(graph);
        this.element = element;
        this.parser = parser;
        this.graph = graph;
        this.locations = locations;
    }
}
