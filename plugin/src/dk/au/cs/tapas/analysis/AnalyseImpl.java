package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Silwing on 28-04-2015.
 */
public class AnalyseImpl implements Analyse {
    private final Map<AnalysisTarget, AnalysisLatticeElement> inLatticeMap = new HashMap<>();
    private final Map<AnalysisTarget, AnalysisLatticeElement> outLatticeMap = new HashMap<>();
    private final Queue<PairImpl<AnalysisTarget, AnalysisTarget>> worklist = new LinkedList<>();

    private final Graph graph;
    private final Analysis analysis;

    public AnalyseImpl(Graph graph, Analysis analysis) {
        this.graph = graph;
        this.analysis = analysis;
        AnalysisTarget entryNode = new AnalysisTargetImpl(graph.getEntryNode());
        inLatticeMap.put(entryNode, analysis.getStartLattice());
        worklist.addAll(graph.getFlow(entryNode).stream().map(successorPair -> new PairImpl<>(entryNode, successorPair)).collect(Collectors.toList()));
        iterateWorklist();
    }

    @NotNull
    private AnalysisLatticeElement inLatticeElement(AnalysisTarget pair) {
        AnalysisLatticeElement element;

        if ((element = inLatticeMap.get(pair)) == null) {
            return analysis.getEmptyLattice();
        }
        return element;
    }

    private boolean hasContextNodePair(AnalysisTarget pair) {
        return inLatticeMap.containsKey(pair);
    }


    private void iterateWorklist() {
        PairImpl<AnalysisTarget, AnalysisTarget> flow;
        while ((flow = worklist.poll()) != null) {
            AnalysisTarget left, right;
            AnalysisLatticeElement newRightLattice, oldRightLattice;
            try {

                left = flow.getLeft();
                right = flow.getRight();
                newRightLattice = analysis.analyse(left, inLatticeElement(left));
                oldRightLattice = inLatticeElement(right);
            } catch (StackOverflowError e) {
                e = e;
                throw e;
            }
                try {

            if (!hasContextNodePair(right) || !newRightLattice.containedIn(oldRightLattice)) {
                AnalysisLatticeElement joinedAnalysis = oldRightLattice.join(newRightLattice);
                inLatticeMap.put(flow.getRight(), joinedAnalysis);
                final PairImpl<AnalysisTarget, AnalysisTarget> finalFlow = flow;
                worklist.addAll(graph.getFlow(joinedAnalysis, flow.getRight()).stream().map(n -> new PairImpl<>(finalFlow.getRight(), n)).collect(Collectors.toList()));
            }
            } catch (StackOverflowError e) {
                e = e;
                throw e;
            }

        }
    }


    @Override
    public AnalysisLatticeElement getEntryLattice() {
        AnalysisTarget startPair = new AnalysisTargetImpl(graph.getEntryNode());
        return getLattice(startPair);
    }

    @Override
    public AnalysisLatticeElement getExitLattice() {
        AnalysisTarget endPair = new AnalysisTargetImpl(graph.getExitNode());
        return getLattice(endPair);
    }

    @Override
    public AnalysisLatticeElement getLattice(AnalysisTarget pair) {
        if (outLatticeMap.containsKey(pair)) {
            return outLatticeMap.get(pair);
        }
        //TODO this is not right for return nodes. Need to set call lattice first

        outLatticeMap.put(pair, analysis.analyse(pair, inLatticeElement(pair)));
        return getLattice(pair);
    }
}
