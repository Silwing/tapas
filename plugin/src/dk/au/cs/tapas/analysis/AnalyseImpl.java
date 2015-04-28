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
    private final Map<Node, AnalysisLatticeElement> inLatticeMap = new HashMap<>();
    private final Map<Node, AnalysisLatticeElement> outLatticeMap = new HashMap<>();
    private final Queue<Pair<Node, Node>> worklist = new LinkedList<>();
    private final Set<Pair<Node, Node>> worklistSet = new HashSet<>();
    private final Map<String, FunctionGraph> functions;

    private final Graph graph;
    private final Analysis analysis;

    public AnalyseImpl(Graph graph, Map<String, FunctionGraph> functions, Analysis analysis) {
        this.graph = graph;
        this.analysis = analysis;
        this.functions = functions;
        inLatticeMap.put(graph.getEntryNode(), analysis.getStartLattice());
        createWorklist(graph.getEntryNode());
        iterateWorklist();
        calculateResult(graph.getEntryNode());
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
        for(Node n : entry.getSuccessors()) {
            worklist.offer(new Pair<>(entry, n));
            inLatticeMap.put(n, analysis.getEmptyLattice());
            createWorklist(n);
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
