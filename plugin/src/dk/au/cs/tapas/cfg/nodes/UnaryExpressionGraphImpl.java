package dk.au.cs.tapas.cfg.nodes;

import com.jetbrains.php.lang.psi.elements.UnaryExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.PsiParserImpl;
import dk.au.cs.tapas.cfg.nodes.ExpressionGraphImpl;
import dk.au.cs.tapas.cfg.nodes.Graph;
import dk.au.cs.tapas.cfg.nodes.SkipGraphImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/22/15.
 */
public class UnaryExpressionGraphImpl extends ExpressionGraphImpl<UnaryExpression>{

    public static PsiParser.ExpressionGraphGenerator<UnaryExpression> generator;

    public UnaryExpressionGraphImpl(PsiParserImpl psiParser, UnaryExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);
    }
}
