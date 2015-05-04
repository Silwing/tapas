package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.cfg.node.CallNode;
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
    }

    private void createWorklist(ContextNodePair entryNode) {


        for(ContextNodePair successorPair : graph.getFlow(entryNode)) {
            worklist.addAll(graph.getFlow(successorPair).stream().map(m -> new PairImpl<>(successorPair, m)).collect(Collectors.toList()));
        }
    }

    private AnalysisLatticeElement inLatticeElement(ContextNodePair pair){
        AnalysisLatticeElement element;

        if((element = inLatticeMap.get(pair)) == null){
            return analysis.getEmptyLattice();
        }
        return  element;
    }

    private boolean hasContextNodePair(ContextNodePair pair){
        return inLatticeMap.containsKey(pair);
    }



    private void iterateWorklist() {
        PairImpl<ContextNodePair, ContextNodePair> flow;
        while((flow = worklist.poll()) != null) {
            ContextNodePair left = flow.getLeft(), right = flow.getRight();
            if(left.getNode() instanceof CallNode){
                // Adding lattice to return node in order to restore scope
                ((CallNode) left.getNode()).getResultNode().addCallLattice(left.getContext(), inLatticeElement(left));
            }

            AnalysisLatticeElement newRightLattice = analysis.analyse(left, inLatticeElement(left));
            AnalysisLatticeElement oldRightLattice = inLatticeElement(right);
            if(!hasContextNodePair(right) || !newRightLattice.containedIn(oldRightLattice)) {
                assert oldRightLattice != null;
                inLatticeMap.put(flow.getRight(), oldRightLattice.join(newRightLattice));
                final PairImpl<ContextNodePair, ContextNodePair> finalFlow = flow;
                worklist.addAll(graph.getFlow(flow.getRight()).stream().map(n -> new PairImpl<>(finalFlow.getRight(), n)).collect(Collectors.toList()));
            }
        }
    }


    @Override
    public AnalysisLatticeElement getEntryLattice() {
        ContextNodePair startPair = new ContextNodePairImpl(graph.getEntryNode());
        return getLattice(startPair);
    }

    @Override
    public AnalysisLatticeElement getExitLattice() {
        ContextNodePair endPair = new ContextNodePairImpl(graph.getExitNode());
        return getLattice(endPair);
    }

    @Override
    public AnalysisLatticeElement getLattice(ContextNodePair pair) {
        if(outLatticeMap.containsKey(pair)){
            return outLatticeMap.get(pair);
        }
        //TODO this is not right for return nodes. Need to set call lattice first

        outLatticeMap.put(pair,analysis.analyse(pair, inLatticeElement(pair)));
        return getLattice(pair);
    }
}
