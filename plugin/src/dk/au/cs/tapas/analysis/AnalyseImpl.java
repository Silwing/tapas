package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.cfg.node.CallNode;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.AnalysisLatticeElementImpl;

import java.util.*;

/**
 * Created by Silwing on 28-04-2015.
 */
public class AnalyseImpl implements Analyse {
    private Graph graph;
    private Map<Node, AnalysisLatticeElementImpl> inLatticeMap;
    private Map<Node, AnalysisLatticeElementImpl> outLatticeMap;
    private Analysis analysis;
    private Queue<Pair<Node, Node>> worklist;

    public AnalyseImpl(Graph g, Analysis a) {
        graph = g;
        analysis = a;
        inLatticeMap = new HashMap<>();
        outLatticeMap = new HashMap<>();
        worklist = new LinkedList<>();
        inLatticeMap.put(g.getEntryNode(), a.getStartLattice());
        createWorklist(g.getEntryNode());
        iterateWorklist();
        calculateResult(g.getEntryNode());
    }

    private void createWorklist(Node entry) {
        if(entry instanceof CallNode) {

        }
        for(Node n : entry.getSuccessors()) {
            worklist.offer(new Pair(entry, n));
            inLatticeMap.put(n, analysis.getEmptyLattice());
            createWorklist(n);
        }
    }

    private void iterateWorklist() {
        Pair<Node, Node> flow;
        while((flow = worklist.poll()) != null) {
            AnalysisLatticeElementImpl fl = analysis.analyse(flow.getLeft(), inLatticeMap.get(flow.getLeft()));
            AnalysisLatticeElementImpl l = inLatticeMap.get(flow.getRight());
            if(!fl.containedIn(l)) {
                inLatticeMap.put(flow.getRight(), l.join(fl));
                for(Node n : flow.getRight().getSuccessors()) {
                    worklist.offer(new Pair(flow.getRight(), n));
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
    public AnalysisLatticeElementImpl getEntryLattice() {
        return outLatticeMap.get(graph.getEntryNode());
    }

    @Override
    public AnalysisLatticeElementImpl getExitLattice() {
        return outLatticeMap.get(graph.getExitNode());
    }

    @Override
    public AnalysisLatticeElementImpl getLattice(Node n) {
        return outLatticeMap.get(n);
    }
}
