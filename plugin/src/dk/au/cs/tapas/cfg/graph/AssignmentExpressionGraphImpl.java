package dk.au.cs.tapas.cfg.graph;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.*;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.PsiParserImpl;
import dk.au.cs.tapas.cfg.node.*;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.TemporaryVariableNameImpl;
import dk.au.cs.tapas.lattice.VariableNameImpl;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/22/15.
 *
 */
public class AssignmentExpressionGraphImpl extends ExpressionGraphImpl<AssignmentExpression>{

    public static PsiParserImpl.ExpressionGraphGenerator<AssignmentExpression> generator = AssignmentExpressionGraphImpl::new;
    private final Graph variableGraph;
    private final Node assignmentNode;


    public AssignmentExpressionGraphImpl(PsiParser psiParser, AssignmentExpression element, Graph graph, TemporaryVariableName name) {
        super(psiParser, element, graph, name);
        Set<HeapLocation> locations = new HashSet<>();

        if(isAlias(element.getFirstChild())){
            Set<HeapLocation> valueLocations = new HashSet<>();
            PhpPsiElement variable = element.getVariable();
            if(variable instanceof Variable){
                assignmentNode = new VariableReferenceAssignmentNodeImpl(graph.getEntryNode(), new VariableNameImpl(variable.getName()), valueLocations, name);
                variableGraph = createValueGraph(valueLocations);
            } else if(variable instanceof ArrayAccessExpression){
                PhpExpression indexValue = (PhpExpression) ((ArrayAccessExpression) variable).getIndex().getValue();
                Graph nextGraph;
                if(indexValue == null){
                    assignmentNode = new ArrayAppendReferenceAssignmentNodeImpl(graph.getEntryNode(), locations, valueLocations, name);
                    nextGraph = createValueGraph(valueLocations);
                } else {
                    TemporaryVariableName indexTemp = new TemporaryVariableNameImpl();
                    assignmentNode = new ArrayWriteReferenceAssignmentNodeImpl(graph.getEntryNode(), name, valueLocations, locations, indexTemp);
                    nextGraph = createValueGraph(valueLocations, parser.parseExpression(indexValue, g -> g, indexTemp).generate(new NodeGraphImpl(assignmentNode)));

                }
                variableGraph = psiParser.parseVariableExpression((PhpExpression) element.getVariable(), g -> g, locations).generate(nextGraph);
            } else {
                throw new UnsupportedOperationException();
            }

        } else {
            TemporaryVariableName valueName = new TemporaryVariableNameImpl();
            this.assignmentNode = new AssignmentNodeImpl(graph.getEntryNode(), name, locations, valueName);
            Graph valueGraph = psiParser.parseExpression((PhpExpression) element.getValue(), g -> g, valueName).generate(new NodeGraphImpl(assignmentNode));
            this.variableGraph = psiParser.parseVariableExpression((PhpExpression) element.getVariable(), g -> g, locations).generate(valueGraph);
        }


    }

    private Graph createValueGraph(Set<HeapLocation> valueLocations, Graph graph) {
        return parser.parseReferenceExpression((PhpExpression) element.getValue(), g -> g, valueLocations).generate(graph);
    }

    private Graph createValueGraph(Set<HeapLocation> valueLocations) {
        return createValueGraph(valueLocations, new NodeGraphImpl(assignmentNode));
    }

    private boolean isAlias(PsiElement element) {
        return element != null && (element.getText().replaceAll("\\s", "").equals("=&") || isAlias(element.getNextSibling()));

    }


    @NotNull
    @Override
    public Node getExitNode() {
        return assignmentNode;
    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return variableGraph.getEntryNode();
    }
}
