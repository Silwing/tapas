package dk.au.cs.tapas.cfg.nodes.graph;

import com.jetbrains.php.lang.psi.elements.ConstantReference;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.nodes.BooleanConstantImpl;
import dk.au.cs.tapas.cfg.nodes.Constant;
import dk.au.cs.tapas.cfg.nodes.NullConstantImpl;
import dk.au.cs.tapas.cfg.nodes.StringConstantImpl;
import dk.au.cs.tapas.cfg.nodes.node.Node;
import dk.au.cs.tapas.cfg.nodes.node.ReadConstNodeImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public class ConstExpressionGraphImpl extends ExpressionGraphImpl<PhpExpression>{
    public static PsiParser.ExpressionGraphGenerator<PhpExpression> generator = ConstExpressionGraphImpl::new;
    private final ReadConstNodeImpl entryNode;

    public ConstExpressionGraphImpl(PsiParser psiParser, PhpExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);

        Constant constant = getConst(element);

        entryNode = new ReadConstNodeImpl(graph.getEntryNode(), constant, name);


    }

    @Override
    public Node getExitNode() {
        return entryNode;
    }

    @Override
    public Node getEntryNode() {
        return entryNode;
    }

    public static boolean isConst(PhpExpression element) {
        return getConst(element) != null;
    }

    private static Constant getConst(PhpExpression element) {
        if(element == null){
            return null;
        }

        if(element instanceof StringLiteralExpression){
            return new StringConstantImpl(((StringLiteralExpression) element).getContents());
        }

        if(element instanceof ConstantReference){
            String fqn = ((ConstantReference) element).getFQN();
            if(fqn == null){
                return  null;
            }
            if(fqn.equals("\\true")){
               return new BooleanConstantImpl(true);
            }

            if(fqn.equals("\\false")){
               return new BooleanConstantImpl(false);
            }

            if(fqn.equals("\\null")){
               return new NullConstantImpl();
            }

            return  null;
        }

        if(element.getFirstPsiChild() == null){
            if(element.getText().matches("^[1-9][0-9]*$")){
                return new NumberConstantImpl(Integer.parseInt(element.getText()));
            }
            //TODO check for other numbers: binary, hex, float, exp ... See part
        }

        return null;
    }


}
