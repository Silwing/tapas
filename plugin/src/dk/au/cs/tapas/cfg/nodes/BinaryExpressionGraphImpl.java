package dk.au.cs.tapas.cfg.nodes;

import com.jetbrains.php.lang.psi.elements.BinaryExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/22/15.
 */
public class BinaryExpressionGraphImpl extends ExpressionGraphImpl<BinaryExpression>{
    public static PsiParser.ExpressionGraphGenerator<BinaryExpression> generator = BinaryExpressionGraphImpl::new;

    public BinaryExpressionGraphImpl(PsiParser psiParser, BinaryExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);

    }

}
