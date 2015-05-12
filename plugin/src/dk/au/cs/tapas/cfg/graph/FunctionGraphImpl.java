package dk.au.cs.tapas.cfg.graph;

import com.jetbrains.php.lang.psi.elements.Function;
import com.jetbrains.php.lang.psi.elements.Parameter;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.analysis.AnalysisTarget;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.VariableName;
import dk.au.cs.tapas.lattice.VariableNameImpl;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class FunctionGraphImpl  implements FunctionGraph {
    private final boolean[] arguments;
    private final ExitNode exitNode;
    private final PsiParser parser;
    private StartNode entryNode = null;
    private final Function element;
    private final VariableName[] argumentNames;

    public FunctionGraphImpl(PsiParser parser, Function element) {
        this.parser = parser;
        Parameter[] parameters = element.getParameters();
        this.element = element;
        arguments = new boolean[parameters.length];
        argumentNames = new VariableName[parameters.length];
        for(int i = 0; i < parameters.length; i ++){
            arguments[i] = parameters[i].isPassByRef();
            argumentNames[i] = new VariableNameImpl(parameters[i].getName());
        }
        exitNode = new ExitNodeImpl(element);





    }

    @Override
    public boolean[] getArguments() {
        return arguments;
    }

    @Override
    public boolean isAliasReturn() {
        return element.getText().replaceAll("\\s", "").startsWith("function&");
    }

    @Override
    public VariableName[] getArgumentNames() {
        return argumentNames;
    }

    @NotNull
    @Override
    public ExitNode getExitNode() {
        return exitNode;
    }

    @NotNull
    @Override
    public StartNode getEntryNode() {

        setUp();

        return entryNode;
    }

    private void setUp() {
        if(entryNode != null){
            return;
        }
        parser.pushCurrentFunctionGraph(this);

        Graph body = parser.parseElement((PhpPsiElement) element.getLastChild(), g -> g).generate(new NodeGraphImpl(exitNode));

        parser.popCurrentFunctionGraph();

        entryNode = new StartNodeImpl(body.getEntryNode(), element);
    }

    @Override
    public Set<AnalysisTarget> getFlow(AnalysisLatticeElement latticeElement, AnalysisTarget cn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<AnalysisTarget> getFlow(AnalysisTarget analysisTarget) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Node> getNodes() {
        return new HashSet<>();
    }

}
