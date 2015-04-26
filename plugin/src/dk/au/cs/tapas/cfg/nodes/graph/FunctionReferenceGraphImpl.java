package dk.au.cs.tapas.cfg.nodes.graph;

import com.jetbrains.php.lang.psi.elements.FunctionReference;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/22/15.
 *
 */
public class FunctionReferenceGraphImpl extends ExpressionGraphImpl<FunctionReference>{

    public static PsiParser.ExpressionGraphGenerator<FunctionReference> generator = FunctionReferenceGraphImpl::new;

    public FunctionReferenceGraphImpl(PsiParser psiParser, FunctionReference element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);
    }


}
