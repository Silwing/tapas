package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.ArrayAccessExpression;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.ArrayAppendLocationVariableExpressionNodeImpl;
import dk.au.cs.tapas.cfg.node.ArrayLocationVariableExpressionNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
class ArrayAccessExpressionSubGraphImpl implements Graph{

    private final Node endNode;
    private final Graph variableGraph;

    public ArrayAccessExpressionSubGraphImpl(PsiParser parser, ArrayAccessExpression element, Graph graph, Set<HeapLocation> locations, ParserGenerator generator) {
        PhpPsiElement indexValue = element.getIndex().getValue();
        Graph nextGraph;
        Set<HeapLocation> variableLocations = new HashSet<>();
        if(indexValue == null){
            endNode = new ArrayAppendLocationVariableExpressionNodeImpl(graph.getEntryNode(), variableLocations, locations);
            nextGraph = new NodeGraphImpl(endNode);
        } else {
            TemporaryVariableName indexName = new TemporaryVariableNameImpl();
            endNode = new ArrayLocationVariableExpressionNodeImpl(graph.getEntryNode(), indexName, variableLocations, locations);
            nextGraph = parser.parseExpression((PhpExpression) indexValue, g -> g, indexName).generate(new NodeGraphImpl(endNode));
        }

        variableGraph = generator.generate((PhpExpression) element.getValue(), locations).generate(nextGraph);

    }


    public interface ParserGenerator{
        PsiParser.GraphGenerator generate(PhpExpression expression, Set<HeapLocation> locations);
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
