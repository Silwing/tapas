package dk.au.cs.tapas.cfg;

import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.*;
import dk.au.cs.tapas.cfg.graph.*;
import dk.au.cs.tapas.lattice.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by budde on 4/22/15.
 *
 */
public class PsiParserImpl implements PsiParser {

    Map<String, Supplier<FunctionGraph>> functionGraphSuppliers = new HashMap<>();
    Map<String, FunctionGraph> functionGraphs = new HashMap<>();
    private LinkedList<FunctionGraph> currentFunctionGraph = new LinkedList<>();

    public PsiParserImpl() {
        functionGraphSuppliers.put("\\array_pop", ArrayPopLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\count", CountLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\array_merge", ArrayMergeLibraryFunctionGraphImpl::TwoArgsGraph);
        functionGraphSuppliers.put("\\array_values", ArrayValuesLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\checkdate", CheckdateLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\empty", EmptyLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\var_dump", VarDumpLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\opendir", SingleArgTopResultLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\closedir", SingleArgTopResultLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\is_dir", SingleArgTopResultLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\readdir", ReaddirLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\array_search", ArraySearchLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\array_key_exists", ArrayKeyExistsLibraryFunctionGraphImpl::new);
        functionGraphSuppliers.put("\\implode", ImplodeLibraryFunctionGraphImpl::new);
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
        }  else if(element instanceof Global){
            generator = parseGlobal((Global) element, generator);
        }  else {
            generator = parseElementNeighbourhood(element.getFirstPsiChild(), generator);
        }
        return generator;
    }

    private GraphGenerator parseGlobal(Global element, GraphGenerator generator) {
        return buildStatementGenerator(GlobalGraphImpl.generator, generator, element);
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
        functionGraphSuppliers.put(element.getFQN(), createFunctionGraphSupplier(element));
        return generator;
    }

    private Supplier<FunctionGraph> createFunctionGraphSupplier(Function element) {
        return () -> new FunctionGraphImpl(this, element);
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
        if(element instanceof ParenthesizedExpression){
            return parseExpression((PhpExpression) ((ParenthesizedExpression) element).getArgument(), generator, name);
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

    private GraphGenerator buildReferenceExpressionGenerator(ReferenceExpressionGraphGenerator expGenerator, GraphGenerator generator, PhpExpression expression, TemporaryHeapVariableName locations) {
        return (Graph g) -> generator.generate(expGenerator.generate(this, expression, g, locations));
    }

    public GraphGenerator parseVariableExpression(PhpExpression target, GraphGenerator generator) {
        return parseVariableExpression(target, generator, new TemporaryHeapVariableNameImpl());
    }

    public GraphGenerator parseVariableExpression(PhpExpression target, GraphGenerator generator, TemporaryHeapVariableName locations) {
        if(target instanceof ArrayAccessExpression){
            return buildReferenceExpressionGenerator(ArrayAccessReferenceExpressionGraphImpl.generator, generator, target, locations);
        }

        if(target instanceof Variable){
            return buildReferenceExpressionGenerator(VariableReferenceExpressionGraphImpl.generator, generator, target, locations);
        }

        return generator;

    }

    @Override
    public GraphGenerator parseReferenceExpression(PhpExpression target, GraphGenerator generator) {
        return parseReferenceExpression(target, generator, new TemporaryHeapVariableNameImpl());
    }

    @Override
    public GraphGenerator parseReferenceExpression(PhpExpression element, GraphGenerator generator, TemporaryHeapVariableName locations) {
        if(element instanceof FunctionReference){
            return buildReferenceExpressionGenerator(FunctionReferenceExpressionGraphImpl.generator, generator, element, locations);
        }

        if(element instanceof ArrayAccessExpression){
            return buildReferenceExpressionGenerator(ArrayAccessReferenceExpressionGraphImpl.generator, generator, element, locations);
        }

        if(element instanceof Variable){
            return buildReferenceExpressionGenerator(VariableReferenceExpressionGraphImpl.generator, generator, element, locations);
        }

        return generator;
    }

    @Override
    public Map<String, FunctionGraph> getFunctions() {
        return new Map<String, FunctionGraph>() {
            @Override
            public int size() {
                return functionGraphSuppliers.size();
            }

            @Override
            public boolean isEmpty() {
                return functionGraphSuppliers.isEmpty();
            }

            @Override
            public boolean containsKey(Object key) {
                return functionGraphSuppliers.containsKey(key);
            }

            @Override
            public boolean containsValue(Object value) {
                return value instanceof FunctionGraph && values().contains(value);
            }

            @Override
            public FunctionGraph get(Object key) {
                if(!(key instanceof String)){
                    return null;
                }
                if(!functionGraphSuppliers.containsKey(key)){
                    return null;
                }
                if(functionGraphs.containsKey(key)){
                    return functionGraphs.get(key);
                }
                FunctionGraph graph = functionGraphSuppliers.get(key).get();
                functionGraphs.put((String) key, graph);
                return graph;
            }

            @Override
            public FunctionGraph put(String key, FunctionGraph value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public FunctionGraph remove(Object key) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void putAll(@NotNull Map<? extends String, ? extends FunctionGraph> m) {
                throw new UnsupportedOperationException();

            }

            @Override
            public void clear() {
                throw new UnsupportedOperationException();

            }

            @NotNull
            @Override
            public Set<String> keySet() {
                return functionGraphSuppliers.keySet();
            }

            @NotNull
            @Override
            public Collection<FunctionGraph> values() {
                return keySet().stream().map(this::get).collect(Collectors.toCollection(LinkedList::new));
            }

            @NotNull
            @Override
            public Set<Entry<String, FunctionGraph>> entrySet() {
                return keySet().stream().map(key -> new AbstractMap.SimpleEntry<>(key, get(key))).collect(Collectors.toSet());
            }
        };
    }

    @Override
    public void pushCurrentFunctionGraph(FunctionGraph functionGraph) {
        this.currentFunctionGraph.addLast(functionGraph);
    }

    @Override
    public FunctionGraph getCurrentFunctionGraph() {
        return this.currentFunctionGraph.getLast();
    }

    @Override
    public void popCurrentFunctionGraph() {
        this.currentFunctionGraph.removeLast();
    }


    @Override
    public Graph parseFile(PhpFile element) {
        TemporaryVariableNameImpl.COUNTER = 0;
        GraphGenerator generator = StartGraphImpl::new;
        generator = parseElementNeighbourhood(element, generator);
        Graph g = generator.generate(new EndGraphImpl());
        return new FinalGraphImpl(g, getFunctions());
    }


}
