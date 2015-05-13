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
 */
public class AssignmentExpressionGraphImpl extends ExpressionGraphImpl<AssignmentExpression> {

    public static PsiParserImpl.ExpressionGraphGenerator<AssignmentExpression> generator = AssignmentExpressionGraphImpl::new;
    private final Graph variableGraph;
    private final Node assignmentNode;


    public AssignmentExpressionGraphImpl(PsiParser psiParser, AssignmentExpression element, Graph graph, TemporaryVariableName targetName) {
        super(psiParser, element, graph, targetName);
        Set<HeapLocation> variableLocations = new HashSet<>();
        PhpPsiElement variable = element.getVariable();
        Set<HeapLocation> valueLocations = new HashSet<>();

        if (isAlias(element.getFirstChild())) {

            if (variable instanceof ArrayAccessExpression) {
                PhpExpression indexValue = (PhpExpression) ((ArrayAccessExpression) variable).getIndex().getValue();
                Graph nextGraph;
                if (indexValue != null) {
                    TemporaryVariableName indexTemp = new TemporaryVariableNameImpl();
                    assignmentNode = new ArrayWriteReferenceAssignmentNodeImpl(graph.getEntryNode(), targetName, valueLocations, variableLocations, indexTemp, element);
                    nextGraph = createValueGraph(valueLocations, parser.parseExpression(indexValue, g -> g, indexTemp).generate(new NodeGraphImpl(assignmentNode)));
                } else {
                    assignmentNode = new ArrayAppendReferenceAssignmentNodeImpl(graph.getEntryNode(), variableLocations, valueLocations, targetName, element);
                    nextGraph = createValueGraph(valueLocations);
                }
                variableGraph = psiParser.parseVariableExpression((PhpExpression) ((ArrayAccessExpression) variable).getValue(), g -> g, variableLocations).generate(nextGraph);
            } else {
                assignmentNode = new VariableReferenceAssignmentNodeImpl(graph.getEntryNode(), new VariableNameImpl(variable.getName()), valueLocations, targetName, element);
                variableGraph = createValueGraph(valueLocations);
            }

        } else {
            TemporaryVariableName valueName = new TemporaryVariableNameImpl();
            if (variable instanceof ArrayAccessExpression) {
                PhpExpression indexValue = (PhpExpression) ((ArrayAccessExpression) variable).getIndex().getValue();
                Graph nextGraph;
                if(indexValue != null){
                    TemporaryVariableName indexName = new TemporaryVariableNameImpl();
                    assignmentNode = new ArrayWriteAssignmentNodeImpl(graph.getEntryNode(), targetName, valueName, indexName, variableLocations, element);
                    Graph valueGraph = psiParser.parseExpression((PhpExpression) element.getValue(), g -> g, valueName).generate(new NodeGraphImpl(assignmentNode));
                    nextGraph = psiParser.parseExpression(indexValue, g -> g, indexName).generate(valueGraph);
                } else {
                    assignmentNode = new ArrayAppendAssignmentNodeImpl(graph.getEntryNode(), targetName, valueName, variableLocations, element);
                    nextGraph = psiParser.parseExpression((PhpExpression) element.getValue(), g -> g, valueName).generate(new NodeGraphImpl(assignmentNode));
                }
                this.variableGraph = psiParser.parseVariableExpression(
                        (PhpExpression) ((ArrayAccessExpression) variable).getValue(),
                        g -> g,
                        variableLocations).generate(nextGraph);

            } else {
                this.assignmentNode = new AssignmentNodeImpl(graph.getEntryNode(), targetName, variableLocations, valueName, element);
                Graph valueGraph = psiParser.parseExpression((PhpExpression) element.getValue(), g -> g, valueName).generate(new NodeGraphImpl(assignmentNode));
                this.variableGraph = psiParser.parseVariableExpression((PhpExpression) variable, g -> g, variableLocations).generate(valueGraph);
            }


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
