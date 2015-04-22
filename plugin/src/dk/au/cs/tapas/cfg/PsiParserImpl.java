package dk.au.cs.tapas.cfg;

import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.*;
import dk.au.cs.tapas.cfg.nodes.*;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by budde on 4/22/15.
 */
public class PsiParserImpl implements PsiParser {

    Map<String, Function> functions = new HashMap<>();


    public GraphGenerator parseElementNeighbourhood(PhpPsiElement element, GraphGenerator generator) {
        if (element == null) {
            return generator;
        }

        generator = parseElement(element, generator);

        generator = parseElementNeighbourhood(element.getNextPsiSibling(), generator);
        return generator;
    }

    public GraphGenerator parseElement(PhpPsiElement element, GraphGenerator generator) {
        if (element instanceof Function) {
            generator = parseFunction((Function) element, generator);
        } else if (element instanceof PhpExpression) {
            generator = parseExpression((PhpExpression) element, generator);
        } else if (element instanceof If) {
            generator = parseIf((If) element, generator);
        } else {
            generator = parseElementNeighbourhood(element.getFirstPsiChild(), generator);
        }
        return  generator;
    }

    public GraphGenerator parseIf(If element, GraphGenerator generator) {
        return buildStatementGenerator(IfGraph.generator, generator, element);
    }

    public GraphGenerator parseFunction(Function element, GraphGenerator generator) {
        functions.put(element.getFQN(), element);
        return generator;
    }

    public GraphGenerator parseExpression(PhpExpression element, GraphGenerator generator) {
        return parseExpression(element, generator, new TemporaryVariableNameImpl());
    }


    public GraphGenerator parseExpression(PhpExpression element, GraphGenerator generator, TemporaryVariableName name) {
        if (element instanceof AssignmentExpression) {
            return buildExpressionGenerator(AssignmentExpressionGraphImpl.generator, generator, element, name);
        }
        if (element instanceof FunctionReference) {
            return buildExpressionGenerator(FunctionReferenceGraphImpl.generator, generator, element, name);
        }
        if (element instanceof BinaryExpression) {
            return buildExpressionGenerator(BinaryExpressionGraphImpl.generator, generator, element, name);
        }
        if (element instanceof UnaryExpression) {
            return buildExpressionGenerator(UnaryExpressionGraphImpl.generator, generator, element, name);
        }

        return generator;
    }

    private GraphGenerator buildExpressionGenerator(ExpressionGraphGenerator expGenerator, GraphGenerator generator, PhpExpression expression, TemporaryVariableName name){
        return (Graph g) -> generator.generate(expGenerator.generate(this, expression, g, name));
    }

    private GraphGenerator buildStatementGenerator(StatementGraphGenerator stmGenerator, GraphGenerator generator, Statement statement){
        return (Graph g) -> generator.generate(stmGenerator.generate(this, statement, g));
    }

    public GraphGenerator parseVariableExpression(PhpExpression target, GraphGenerator generator, HeapLocation location) {
        return generator;
    }

    @Override
    public Graph parseFile(PhpFile element) {
        GraphGenerator generator = StartGraphImpl::new;
        generator = parseElementNeighbourhood(element, generator);
        Graph g = generator.generate(new EndGraphImpl());

        String dot = g.getExitNode().toDot().stream().reduce("", (s1, s2) -> s1+s2);
        return g;
    }


}
