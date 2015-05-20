package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.PhpExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryHeapVariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public abstract class VariableExpressionGraphImpl<T extends PhpExpression> extends SkipGraphImpl{

    protected final PsiParser parser;
    protected final T element;
    protected final Graph graph;
    protected final TemporaryHeapVariableName locations;


    public VariableExpressionGraphImpl(PsiParser parser, T element, Graph graph, TemporaryHeapVariableName locations) {
        super(graph);
        this.element = element;
        this.parser = parser;
        this.graph = graph;
        this.locations = locations;
    }
}
