package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.Else;
import com.jetbrains.php.lang.psi.elements.If;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.IfNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.SkipNodeImpl;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

/**
 * Created by budde on 4/22/15.
 *
 */
public class IfGraphImpl extends StatementGraphImpl<If> {

    public static PsiParser.StatementGraphGenerator<If> generator = IfGraphImpl::new;
    private final SkipNodeImpl exitNode;
    private Node entryNode;


    public IfGraphImpl(PsiParser parser, If element, Graph targetGraph) {
        super(parser, element, targetGraph);

        this.exitNode = new SkipNodeImpl(targetGraph.getEntryNode());
        Graph ifGraph = parser.parseElement(element.getStatement(), g -> g).generate(new NodeGraphImpl(exitNode));
        TemporaryVariableName condition = new TemporaryVariableNameImpl();
        Else branch = element.getElseBranch();
        IfNodeImpl ifNode;
        if(branch == null){
            ifNode = new IfNodeImpl(condition, ifGraph.getEntryNode(), exitNode);
        } else {
            Graph elseGraph = parser.parseElement(branch.getStatement(), g -> g).generate(new NodeGraphImpl(exitNode));
            ifNode = new IfNodeImpl(condition, ifGraph.getEntryNode(), elseGraph.getEntryNode());
        }

        Graph conditionGraph = parser.parseExpression((PhpExpression) element.getCondition(), g -> g, condition).generate(new NodeGraphImpl(ifNode));
        entryNode = conditionGraph.getEntryNode();
    }


    @Override
    public Node getExitNode() {
        return exitNode;
    }

    @Override
    public Node getEntryNode() {
        return entryNode;
    }
}
