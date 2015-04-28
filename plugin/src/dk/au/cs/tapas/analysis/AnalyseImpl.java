package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.cfg.node.CallNode;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.AnalysisLatticeElementImpl;

import java.util.*;

/**
 * Created by Silwing on 28-04-2015.
 */
public class AnalyseImpl implements Analyse {
    private final Map<Node, AnalysisLatticeElement> inLatticeMap = new HashMap<>();
    private final Map<Node, AnalysisLatticeElement> outLatticeMap = new HashMap<>();
    private final Queue<Pair<Node, Node>> worklist = new LinkedList<>();

    private final Graph graph;
    private final Analysis analysis;

    public AnalyseImpl(Graph graph, Analysis analysis) {
        this.graph = graph;
        this.analysis = analysis;
        inLatticeMap.put(graph.getEntryNode(), analysis.getStartLattice());
        createWorklist(graph.getEntryNode());
        iterateWorklist();
        calculateResult(graph.getEntryNode());
    }

    private void createWorklist(Node entry) {
        if(entry instanceof CallNode) {

        }
        for(Node n : entry.getSuccessors()) {
            worklist.offer(new Pair<>(entry, n));
            inLatticeMap.put(n, analysis.getEmptyLattice());
            createWorklist(n);
        }
    }

    private void iterateWorklist() {
        Pair<Node, Node> flow;
        while((flow = worklist.poll()) != null) {
            AnalysisLatticeElement newTargetLattice = analysis.analyse(flow.getLeft(), inLatticeMap.get(flow.getLeft()));
            AnalysisLatticeElement targetLattice = inLatticeMap.get(flow.getRight());
            if(!newTargetLattice.containedIn(targetLattice)) {
                inLatticeMap.put(flow.getRight(), targetLattice.join(newTargetLattice));
                for(Node successor : flow.getRight().getSuccessors()) {
                    worklist.offer(new Pair<>(flow.getRight(), successor));
                }
            }
        }
    }

    private void calculateResult(Node entry) {
        outLatticeMap.put(entry, analysis.analyse(entry, inLatticeMap.get(entry)));
        for(Node n : entry.getSuccessors()) {
            calculateResult(n);
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
