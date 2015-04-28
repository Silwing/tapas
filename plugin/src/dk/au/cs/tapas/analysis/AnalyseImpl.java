package dk.au.cs.tapas.analysis;

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
    private final Map<Node, AnalysisLatticeElement> inLatticeMap = new HashMap<>();
    private final Map<Node, AnalysisLatticeElement> outLatticeMap = new HashMap<>();
    private final Queue<Pair<Node, Node>> worklist = new LinkedList<>();

    private final Stack<Node> resultNodes = new Stack<>();

    private final Graph graph;
    private final Analysis analysis;

    public AnalyseImpl(Graph graph, Analysis analysis) {
        this.graph = graph;
        this.analysis = analysis;
        createWorklist();
        inLatticeMap.put(graph.getEntryNode(), analysis.getStartLattice());
        iterateWorklist();
        calculateResult();
    }

    private void createWorklist() {
        for(Node n : graph.getNodes()) {
            inLatticeMap.put(n, analysis.getEmptyLattice());
            worklist.addAll(graph.getFlow(n).stream().map(m -> new Pair<>(n, m)).collect(Collectors.toList()));
        }
    }

    private void iterateWorklist() {
        Pair<Node, Node> flow;
        while((flow = worklist.poll()) != null) {
            AnalysisLatticeElement fl = analysis.analyse(flow.getLeft(), inLatticeMap.get(flow.getLeft()));
            AnalysisLatticeElement l = inLatticeMap.get(flow.getRight());
            if(!fl.containedIn(l)) {
                inLatticeMap.put(flow.getRight(), l.join(fl));
                final Pair<Node, Node> finalFlow = flow;
                worklist.addAll(graph.getFlow(flow.getRight()).stream().map(n -> new Pair<>(finalFlow.getRight(), n)).collect(Collectors.toList()));
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
