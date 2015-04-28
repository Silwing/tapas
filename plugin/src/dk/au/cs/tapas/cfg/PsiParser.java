package dk.au.cs.tapas.cfg;

import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.*;
import dk.au.cs.tapas.cfg.graph.FunctionGraph;
import dk.au.cs.tapas.cfg.graph.FunctionGraphImpl;
import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Map;
import java.util.Set;

/**
 * Created by budde on 4/22/15.
 */
public interface PsiParser {


    Graph parseFile(PhpFile element);

    GraphGenerator parseElementNeighbourhood(PhpPsiElement element, GraphGenerator generator);

    GraphGenerator parseElement(PhpPsiElement element, GraphGenerator generator);

    GraphGenerator parseIf(If element, GraphGenerator generator);

    GraphGenerator parseFunction(Function element, GraphGenerator generator);

    GraphGenerator parseExpression(PhpExpression element, GraphGenerator generator);

    GraphGenerator parseExpression(PhpExpression element, GraphGenerator generator, TemporaryVariableName name);

    GraphGenerator parseVariableExpression(PhpExpression target, GraphGenerator generator);

    GraphGenerator parseVariableExpression(PhpExpression element, GraphGenerator generator, Set<HeapLocation> locations);

    GraphGenerator parseReferenceExpression(PhpExpression target, GraphGenerator generator);

    GraphGenerator parseReferenceExpression(PhpExpression element, GraphGenerator generator, Set<HeapLocation> locations);

    Map<String, FunctionGraph> getFunctions();

    void setCurrentFunctionGraph(FunctionGraphImpl functionGraph);

    FunctionGraph getCurrentFunctionGraph();

    interface GraphGenerator {
        Graph generate(Graph graph);
    }

    interface StatementGraphGenerator<T extends Statement> {
        Graph generate(PsiParser parser, T statement, Graph graph);
    }


    interface ExpressionGraphGenerator<T extends PhpExpression> {
        Graph generate(PsiParser parser, T expression, Graph graph, TemporaryVariableName targetName);
    }

    interface VariableExpressionGraphGenerator<T extends PhpExpression> {
        Graph generate(PsiParser parser, T expression, Graph graph, Set<HeapLocation> locations);
    }
    interface ReferenceExpressionGraphGenerator<T extends PhpExpression> {
        Graph generate(PsiParser parser, T expression, Graph graph, Set<HeapLocation> locations);
    }

}
