package dk.au.cs.tapas.analysis;

import com.intellij.javaee.model.xml.converters.ContextParamNameConverter;
import com.sun.jndi.cosnaming.CNNameParser;
import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.AnalysisLatticeElement;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Silwing on 28-04-2015.
 *
 */
public class AnalyseImpl implements Analyse {
    private final Map<ContextNodePair, AnalysisLatticeElement> inLatticeMap = new HashMap<>();
    private final Map<ContextNodePair, AnalysisLatticeElement> outLatticeMap = new HashMap<>();
    private final Queue<PairImpl<ContextNodePair, ContextNodePair>> worklist = new LinkedList<>();

    private final Stack<Node> resultNodes = new Stack<>();

    private final Graph graph;
    private final Analysis analysis;

    public AnalyseImpl(Graph graph, Analysis analysis) {
        this.graph = graph;
        this.analysis = analysis;
        ContextNodePair entryNode = new ContextNodePairImpl(graph.getEntryNode());
        inLatticeMap.put(entryNode, analysis.getStartLattice());
        createWorklist(entryNode);
        iterateWorklist();
        calculateResult();

    }

    private void createWorklist(ContextNodePair entryNode) {


        for(ContextNodePair successorPair : graph.getFlow(entryNode)) {
            worklist.addAll(graph.getFlow(successorPair).stream().map(m -> new PairImpl<>(successorPair, m)).collect(Collectors.toList()));
        }
    }

    private void iterateWorklist() {
        PairImpl<ContextNodePair, ContextNodePair> flow;
        while((flow = worklist.poll()) != null) {
            AnalysisLatticeElement fl = analysis.analyse(flow.getLeft(), inLatticeMap.get(flow.getLeft()));
            AnalysisLatticeElement l = inLatticeMap.get(flow.getRight());
            if(!fl.containedIn(l)) {
                inLatticeMap.put(flow.getRight(), l.join(fl));
                final PairImpl<ContextNodePair, ContextNodePair> finalFlow = flow;
                worklist.addAll(graph.getFlow(flow.getRight()).stream().map(n -> new PairImpl<>(finalFlow.getRight(), n)).collect(Collectors.toList()));
            }
        }
    }

    private void calculateResult() {
        for(Node n : graph.getNodes()) {
            outLatticeMap.put(n, analysis.analyse(n, inLatticeMap.get(n)));
        }
    }

    @Override
    public AnalysisLatticeElement getEntryLattice() {
        return outLatticeMap.get(graph.getEntryNode());
    }

    @Override
    public AnalysisLatticeElement getExitLattice() {
        return outLatticeMap.get(graph.getExitNode());
    }

    @Override
    public AnalysisLatticeElement getLattice(Node n) {
        return outLatticeMap.get(n);
    }
}
