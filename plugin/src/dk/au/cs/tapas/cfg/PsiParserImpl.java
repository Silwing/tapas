package dk.au.cs.tapas.cfg;

import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.*;
import dk.au.cs.tapas.cfg.graph.*;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by budde on 4/22/15.
 *
 */
public class PsiParserImpl implements PsiParser {

    Map<String, FunctionGraph> functionGraphs = new HashMap<>();
    private FunctionGraphImpl currentFunctionGraph;

    public PsiParserImpl() {
        functionGraphs.put("\\array_pop", new LibraryFunctionGraphImpl(new boolean[]{true}, false));
    }


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
        } else if (element instanceof While) {
            generator = parseWhile((While) element, generator);
        } else if (element instanceof For) {
            generator = parseFor((For) element, generator);
        } else if(element instanceof PhpReturn){
            generator = parseReturn((PhpReturn) element, generator);
        }   else {
            generator = parseElementNeighbourhood(element.getFirstPsiChild(), generator);
        }
        return generator;
    }

    private GraphGenerator parseReturn(PhpReturn element, GraphGenerator generator) {
        return buildStatementGenerator(ReturnStatementGraphImpl.generator, generator, element);
    }

    private GraphGenerator parseFor(For element, GraphGenerator generator) {
        return buildStatementGenerator(ForGraphImpl.generator, generator, element);
    }

    private GraphGenerator parseWhile(While element, GraphGenerator generator) {
        return buildStatementGenerator(WhileGraphImpl.generator, generator, element);
    }

    public GraphGenerator parseIf(If element, GraphGenerator generator) {
        return buildStatementGenerator(IfGraphImpl.generator, generator, element);
    }

    public GraphGenerator parseFunction(Function element, GraphGenerator generator) {
        functionGraphs.put(element.getFQN(), createFunctionGraph(element));
        return generator;
    }

    private FunctionGraph createFunctionGraph(Function element) {
        return new FunctionGraphImpl(this, element);
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
        if(element instanceof Variable){
            return buildExpressionGenerator(VariableReadExpressionGraphImpl.generator, generator, element, name);
        }
        if(element instanceof ArrayCreationExpression){
            return buildExpressionGenerator(ArrayInitExpressionGraphImpl.generator, generator, element, name);
        }
        if(element instanceof ArrayAccessExpression){
            return buildExpressionGenerator(ArrayAccessExpressionGraphImpl.generator, generator, element, name);
        }


        if(ConstExpressionGraphImpl.isConst(element)){
            return buildExpressionGenerator(ConstExpressionGraphImpl.generator, generator, element, name);
        }

        return generator;
    }

    private GraphGenerator buildExpressionGenerator(ExpressionGraphGenerator expGenerator, GraphGenerator generator, PhpExpression expression, TemporaryVariableName name) {
        return (Graph g) -> generator.generate(expGenerator.generate(this, expression, g, name));
    }

    private GraphGenerator buildStatementGenerator(StatementGraphGenerator stmGenerator, GraphGenerator generator, Statement statement) {
        return (Graph g) -> generator.generate(stmGenerator.generate(this, statement, g));
    }

    private GraphGenerator buildVariableExpressionGenerator(VariableExpressionGraphGenerator expGenerator, GraphGenerator generator, PhpExpression expression, Set<HeapLocation> locations) {
        return (Graph g) -> generator.generate(expGenerator.generate(this, expression, g, locations));
    }

    private GraphGenerator buildReferenceExpressionGenerator(ReferenceExpressionGraphGenerator expGenerator, GraphGenerator generator, PhpExpression expression, Set<HeapLocation> locations) {
        return (Graph g) -> generator.generate(expGenerator.generate(this, expression, g, locations));
    }

    public GraphGenerator parseVariableExpression(PhpExpression target, GraphGenerator generator) {
        return parseVariableExpression(target, generator, new HashSet<>());
    }

    public GraphGenerator parseVariableExpression(PhpExpression target, GraphGenerator generator, Set<HeapLocation> locations) {
        if(target instanceof ArrayAccessExpression){
            return buildVariableExpressionGenerator(ArrayAccessVariableExpressionGraphImpl.generator, generator, target, locations);
        }

        if(target instanceof Variable){
            return buildVariableExpressionGenerator(VariableVariableExpressionGraphImpl.generator, generator, target, locations);
        }

        return generator;

    }

    @Override
    public GraphGenerator parseReferenceExpression(PhpExpression target, GraphGenerator generator) {
        return parseReferenceExpression(target, generator, new HashSet<>());
    }

    @Override
    public GraphGenerator parseReferenceExpression(PhpExpression element, GraphGenerator generator, Set<HeapLocation> locations) {
        if(element instanceof FunctionReference){
            return buildReferenceExpressionGenerator(FunctionReferenceExpressionGraphImpl.generator, generator, element, locations);
        }

        if(element instanceof ArrayAccessExpression){
            return buildReferenceExpressionGenerator(ArrayAccessReferenceExpressionGraphImpl.generator, generator, element, locations);
        }

        if(element instanceof Variable){
            return buildReferenceExpressionGenerator(VariableReferenceExpressionGraphImpl.generator, generator, element, locations);
        }

        return  parseVariableExpression(element, generator, locations);
    }

    @Override
    public Map<String, FunctionGraph> getFunctions() {
        return functionGraphs;
    }

    @Override
    public void setCurrentFunctionGraph(FunctionGraphImpl functionGraph) {
        this.currentFunctionGraph = functionGraph;
    }

    @Override
    public FunctionGraph getCurrentFunctionGraph() {
        return this.currentFunctionGraph;
    }


    @Override
    public Graph parseFile(PhpFile element) {
        GraphGenerator generator = StartGraphImpl::new;
        generator = parseElementNeighbourhood(element, generator);
        Graph g = generator.generate(new EndGraphImpl());
        return new FinalGraphImpl(g, getFunctions());
    }


}
