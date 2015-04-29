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

    private AnalysisLatticeElement inLattice(ContextNodePair pair){
        AnalysisLatticeElement element;

        if((element = inLatticeMap.get(pair)) == null){
            return analysis.getEmptyLattice();
        }
        return  element;
    }

    private void iterateWorklist() {
        PairImpl<ContextNodePair, ContextNodePair> flow;
        while((flow = worklist.poll()) != null) {
            AnalysisLatticeElement newLattice = analysis.analyse(flow.getLeft(), inLattice(flow.getLeft()));
            AnalysisLatticeElement oldLattice = inLattice(flow.getRight());
            if(!newLattice.containedIn(oldLattice)) {
                inLatticeMap.put(flow.getRight(), oldLattice.join(newLattice));
                final PairImpl<ContextNodePair, ContextNodePair> finalFlow = flow;
                worklist.addAll(graph.getFlow(flow.getRight()).stream().map(n -> new PairImpl<>(finalFlow.getRight(), n)).collect(Collectors.toList()));
            }
        }
    }

    private void calculateResult() {
        for(ContextNodePair pair : inLatticeMap.keySet()) {
            outLatticeMap.put(pair, analysis.analyse(pair, inLatticeMap.get(pair)));
        }
    }

    @Override
    public AnalysisLatticeElement getEntryLattice() {
        return outLatticeMap.get(new ContextNodePairImpl(graph.getEntryNode()));
    }

    @Override
    public AnalysisLatticeElement getExitLattice() {
        return outLatticeMap.get(new ContextNodePairImpl(graph.getExitNode()));
    }

    @Override
    public AnalysisLatticeElement getLattice(ContextNodePair pair) {
        return outLatticeMap.get(pair);
    }
}
