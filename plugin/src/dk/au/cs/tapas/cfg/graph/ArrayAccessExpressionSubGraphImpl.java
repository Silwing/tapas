package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.ArrayAccessExpression;
import com.jetbrains.php.lang.psi.elements.PhpExpression;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.ArrayAppendLocationSetNodeImpl;
import dk.au.cs.tapas.cfg.node.ArrayReadLocationSetNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
class ArrayAccessExpressionSubGraphImpl extends GraphImpl{

    private final Node endNode;
    private final Graph variableGraph;

    public ArrayAccessExpressionSubGraphImpl(PsiParser parser, ArrayAccessExpression element, Graph graph, TemporaryHeapVariableName locations, ParserGenerator generator) {
        PhpPsiElement indexValue = element.getIndex().getValue();
        Graph nextGraph;
        TemporaryHeapVariableName valueLocations = new TemporaryHeapVariableNameImpl();
        if(indexValue == null){
            endNode = new ArrayAppendLocationSetNodeImpl(graph.getEntryNode(), valueLocations, locations, element);
            nextGraph = new NodeGraphImpl(endNode);
        } else {
            TemporaryVariableName indexName = new TemporaryVariableNameImpl();
            endNode = new ArrayReadLocationSetNodeImpl(graph.getEntryNode(), indexName, valueLocations, locations, element);
            nextGraph = parser.parseExpression((PhpExpression) indexValue, g -> g, indexName).generate(new NodeGraphImpl(endNode));
        }

        variableGraph = generator.generate((PhpExpression) element.getValue(), valueLocations).generate(nextGraph);

    }


    public interface ParserGenerator{
        PsiParser.GraphGenerator generate(PhpExpression expression, TemporaryHeapVariableName locations);
    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return variableGraph.getEntryNode();
    }

    @NotNull
    @Override
    public Node getExitNode() {
        return endNode;
    }
}
