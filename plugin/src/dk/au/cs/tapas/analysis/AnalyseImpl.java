package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.graph.FunctionGraph;
import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.cfg.node.CallNode;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.AnalysisLatticeElementImpl;

import java.util.*;

/**
 * Created by Silwing on 28-04-2015.
 *
 */
public class AnalyseImpl implements Analyse {
    private Graph graph;
    private Map<String, FunctionGraph> functions;
    private Map<Node, AnalysisLatticeElementImpl> inLatticeMap;
    private Map<Node, AnalysisLatticeElementImpl> outLatticeMap;
    private Analysis analysis;
    private Queue<Pair<Node, Node>> worklist;
    private Set<Pair<Node, Node>> worklistSet;

    public AnalyseImpl(Graph g, Map<String, FunctionGraph> f, Analysis a) {
        graph = g;
        functions = f;
        analysis = a;
        inLatticeMap = new HashMap<>();
        outLatticeMap = new HashMap<>();
        worklist = new LinkedList<>();
        worklistSet = new HashSet<>();
        inLatticeMap.put(g.getEntryNode(), a.getStartLattice());
        createWorklist(g.getEntryNode());
        iterateWorklist();
        calculateResult(g.getEntryNode());
    }

    private boolean addToWorklist(Pair<Node, Node> toAdd) {
        if(!worklistSet.contains(toAdd)) {
            worklistSet.add(toAdd);
            worklist.offer(toAdd);
            return true;
        }
        return false;
    }

    private void createWorklist(Node entry) {
        if(entry instanceof CallNode) {
            CallNode c = (CallNode)entry;
            Graph func = functions.get(c.getFunctionName());
            inLatticeMap.put(entry, analysis.getEmptyLattice());
            if(addToWorklist(new Pair<>(entry, func.getEntryNode())))
                createWorklist(func.getEntryNode());
            inLatticeMap.put(c.getResultNode(), analysis.getEmptyLattice());
            if(addToWorklist(new Pair<>(func.getExitNode(), c.getResultNode())))
                createWorklist(c.getResultNode());
        } else {
            for (Node n : entry.getSuccessors()) {
                inLatticeMap.put(n, analysis.getEmptyLattice());
                if(addToWorklist(new Pair<>(entry, n)))
                    createWorklist(n);
            }
        }
    }

    private void iterateWorklist() {
        Pair<Node, Node> flow;
        while((flow = worklist.poll()) != null) {
            worklistSet.remove(flow);
            AnalysisLatticeElementImpl fl = analysis.analyse(flow.getLeft(), inLatticeMap.get(flow.getLeft()));
            AnalysisLatticeElementImpl l = inLatticeMap.get(flow.getRight());
            if(!fl.containedIn(l)) {
                inLatticeMap.put(flow.getRight(), l.join(fl));
                for(Node n : flow.getRight().getSuccessors()) {
                    addToWorklist(new Pair<>(flow.getRight(), n));
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
