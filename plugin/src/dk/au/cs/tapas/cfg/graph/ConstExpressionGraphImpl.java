package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.ConstantReference;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.BooleanConstantImpl;
import dk.au.cs.tapas.cfg.Constant;
import dk.au.cs.tapas.cfg.NullConstantImpl;
import dk.au.cs.tapas.cfg.StringConstantImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.ReadConstNodeImpl;
import dk.au.cs.tapas.lattice.element.NumberLatticeElement;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 4/26/15.
 */
public class ConstExpressionGraphImpl extends ExpressionGraphImpl<PhpExpression> {
    public static PsiParser.ExpressionGraphGenerator<PhpExpression> generator = ConstExpressionGraphImpl::new;
    private final ReadConstNodeImpl entryNode;

    public ConstExpressionGraphImpl(PsiParser psiParser, PhpExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);

        Constant constant = getConst(element);

        entryNode = new ReadConstNodeImpl(graph.getEntryNode(), constant, name, element);


    }

    @NotNull
    @Override
    public Node getExitNode() {
        return entryNode;
    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return entryNode;
    }

    public static boolean isConst(PhpExpression element) {
        return getConst(element) != null;
    }

    private static Constant getConst(PhpExpression element) {
        if (element == null) {
            return null;
        }

        if (element instanceof StringLiteralExpression) {
            return new StringConstantImpl(((StringLiteralExpression) element).getContents());
        }

        if (element instanceof ConstantReference) {
            String fqn = ((ConstantReference) element).getFQN();
            if (fqn == null) {
                return null;
            }
            fqn = fqn.toLowerCase();
            if (fqn.equals("\\true")) {
                return new BooleanConstantImpl(true);
            }

            if (fqn.equals("\\false")) {
                return new BooleanConstantImpl(false);
            }

            if (fqn.equals("\\null")) {
                return new NullConstantImpl();
            }

            return null;
        }

        if (element.getFirstPsiChild() == null) {
            Number number = NumberLatticeElement.parseNumberString(element.getText());
            return number == null?null:new NumberConstantImpl(number);
        }

        return null;
    }


}
