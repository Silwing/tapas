package dk.au.cs.tapas.analysis;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.PhpFile;
import dk.au.cs.tapas.annotator.Annotation;
import dk.au.cs.tapas.annotator.ErrorAnnotationImpl;
import dk.au.cs.tapas.annotator.InformationAnnotationImpl;
import dk.au.cs.tapas.annotator.WarningAnnotationImpl;
import dk.au.cs.tapas.cfg.PsiParserImpl;
import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
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
    private final List<Annotation> annotations = new LinkedList<>();

    public AnalyseImpl(Graph graph, Function<AnalysisAnnotator, Analysis> analysisFunction) {
        this.graph = graph;
        this.analysis = analysisFunction.apply(new AnalysisAnnotatorImpl(annotations));
        AnalysisTarget entryNode = new AnalysisTargetImpl(graph.getEntryNode());
        inLatticeMap.put(entryNode, analysis.getStartLattice());
        worklist.addAll(graph.getFlow(entryNode).stream().map(successorPair -> new PairImpl<>(entryNode, successorPair)).collect(Collectors.toList()));
        iterateWorklist();
        annotations.addAll(graph.getNodes().stream().filter(node -> !inLatticeMap.containsKey(new AnalysisTargetImpl(node)) && node.getElement() != null).map(node -> new WarningAnnotationImpl(node.getElement(), "Unreachable node")).collect(Collectors.toList()));
    }

    public AnalyseImpl(Graph graph) {
        this(graph, TypeAnalysisImpl::new);
    }

    public AnalyseImpl(PhpFile collectedInfo) {
        this(new PsiParserImpl().parseFile(collectedInfo));
    }

    @NotNull
    private AnalysisLatticeElement inLatticeElement(AnalysisTarget pair) {
        AnalysisLatticeElement element;

        if ((element = inLatticeMap.get(pair)) == null) {
            return analysis.getEmptyLattice();
        }

        if (inLatticeMap.keySet().stream().anyMatch(p1 -> p1 == pair)) {
            return element;
        }


        inLatticeMap.remove(pair);
        inLatticeMap.put(pair, element);
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

            left = flow.getLeft();
            right = flow.getRight();
            newRightLattice = analysis.analyse(left, inLatticeElement(left));
            oldRightLattice = inLatticeElement(right);
            if (!hasContextNodePair(right) || !newRightLattice.containedIn(oldRightLattice)) {
                AnalysisLatticeElement joinedAnalysis = oldRightLattice.join(newRightLattice);
                inLatticeMap.put(flow.getRight(), joinedAnalysis);
                final PairImpl<AnalysisTarget, AnalysisTarget> finalFlow = flow;
                worklist.addAll(graph.getFlow(joinedAnalysis, flow.getRight()).stream().map(n -> new PairImpl<>(finalFlow.getRight(), n)).collect(Collectors.toList()));
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
        outLatticeMap.put(pair, analysis.analyse(pair, inLatticeElement(pair)));
        return getLattice(pair);
    }

    @Override
    @NotNull
    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    private class AnalysisAnnotatorImpl implements AnalysisAnnotator {
        private final List<Annotation> annotations;
        public Node node;

        public AnalysisAnnotatorImpl(List<Annotation> annotations) {
            this.annotations = annotations;
        }

        @Override
        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void warning(String message) {
            add(e -> new WarningAnnotationImpl(e, message));
        }

        @Override
        public void error(String message) {
            add(e -> new ErrorAnnotationImpl(e, message));

        }

        @Override
        public void information(String message) {
            add(e -> new InformationAnnotationImpl(e, message));

        }

        private void add(Function<PsiElement, Annotation> annotation) {
            if (node == null || node.getElement() == null) {
                return;
            }
            annotations.add(annotation.apply(node.getElement()));
        }

    }
}
