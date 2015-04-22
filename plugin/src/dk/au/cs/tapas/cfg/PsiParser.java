package dk.au.cs.tapas.cfg;

import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.*;
import dk.au.cs.tapas.cfg.nodes.AssignmentExpressionGraphImpl;
import dk.au.cs.tapas.cfg.nodes.Graph;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

/**
 * Created by budde on 4/22/15.
 */
public interface PsiParser {




    public Graph parseFile(PhpFile element);

    public GraphGenerator parseElementNeighbourhood(PhpPsiElement element, GraphGenerator generator);

    public GraphGenerator parseElement(PhpPsiElement element, GraphGenerator generator);

    public GraphGenerator parseIf(If element, GraphGenerator generator);

    public GraphGenerator parseFunction(Function element, GraphGenerator generator);

    public GraphGenerator parseExpression(PhpExpression element, GraphGenerator generator) ;

    public GraphGenerator parseExpression(PhpExpression element, GraphGenerator generator, TemporaryVariableName name);

    public GraphGenerator parseVariableExpression(PhpExpression element, GraphGenerator generator, HeapLocation location);

    public interface GraphGenerator {
        Graph generate(Graph graph);
    }

    public interface StatementGraphGenerator<T extends Statement>{
        Graph generate(PsiParser parser, T statement, Graph graph);
    }


    public interface ExpressionGraphGenerator<T extends PhpExpression>{
        Graph generate(PsiParser parser, T expression, Graph graph, TemporaryVariableName targetName);
    }
}
