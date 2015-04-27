package dk.au.cs.tapas.cfg.nodes.graph;

import com.jetbrains.php.lang.psi.elements.ArrayAccessExpression;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.nodes.node.ArrayAppendLocationVariableExpressionNodeImpl;
import dk.au.cs.tapas.cfg.nodes.node.ArrayLocationVariableExpressionNodeImpl;
import dk.au.cs.tapas.cfg.nodes.node.Node;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class ArrayAccessVariableExpressionGraphImpl extends VariableExpressionGraphImpl<ArrayAccessExpression>{

    public static PsiParser.VariableExpressionGraphGenerator<ArrayAccessExpression> generator = ArrayAccessVariableExpressionGraphImpl::new;
    private final Node endNode;
    private final Graph variableGraph;

    public ArrayAccessVariableExpressionGraphImpl(PsiParser parser, ArrayAccessExpression element, Graph graph, Set<HeapLocation> locations) {
        super(parser, element, graph, locations);

        PhpPsiElement indexValue = element.getIndex().getValue();
        Graph nextGraph;
        Set<HeapLocation> variableLocations = new HashSet<>();
        if(indexValue == null){
            endNode = new ArrayAppendLocationVariableExpressionNodeImpl(graph.getEntryNode(), variableLocations, locations);
            nextGraph = new NodeGraphImpl(endNode);
        } else {
            TemporaryVariableName indexName = new TemporaryVariableNameImpl();
            endNode = new ArrayLocationVariableExpressionNodeImpl(graph.getEntryNode(), indexName, variableLocations, locations);
            nextGraph = parser.parseExpression(element.getIndex(), g -> g, indexName).generate(new NodeGraphImpl(endNode));
        }

        variableGraph = parser.parseVariableExpression((PhpExpression) element.getValue(), g -> g, variableLocations).generate(nextGraph);

    }


    @Override
    public Node getEntryNode() {
        return variableGraph.getEntryNode();
    }

    @Override
    public Node getExitNode() {
        return endNode;
    }
}
