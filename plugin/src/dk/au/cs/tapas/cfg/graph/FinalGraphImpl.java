package dk.au.cs.tapas.cfg.graph;

import com.sun.org.apache.xpath.internal.functions.FuncGenerateId;
import dk.au.cs.tapas.cfg.node.Node;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by budde on 4/28/15.
 */
public class FinalGraphImpl implements Graph{
    private final Node exitNode, entryNode;
    private final Map<String, FunctionGraph> functionGraphMap;
    private Set<Node> nodes;

    public FinalGraphImpl(Graph graph, Map<String, FunctionGraph> functionGraphMap) {

        this.functionGraphMap = functionGraphMap;
        entryNode = graph.getEntryNode();
        exitNode = findExitNode(entryNode);
        assert exitNode != null;

    }

    private Node findExitNode(Node entryNode) {
        return findExitNode(entryNode, new HashSet<>());
    }

    private Node findExitNode(Node entryNode, HashSet<Node> objects) {
        if (objects.contains(entryNode)) {
            return null;
        }

        if(entryNode.getSuccessors().length == 0){
            return entryNode;
        }

        for (Node child : entryNode.getSuccessors()) {
            Node prospect = findExitNode(child);
            if(prospect != null){
                return prospect;
            }
        }

        return null;
    }

    @NotNull
    @Override
    public Node getExitNode() {
        return exitNode;
    }

    @NotNull
    @Override
    public Node getEntryNode() {
        return entryNode;
    }

    @Override
    public Set<Node> getFlow(Node n) {
        return null;
    }

    @Override
    public Set<Node> getNodes() {
        if(nodes != null){
            return new HashSet<>(nodes);
        }

        nodes = new HashSet<>();
        addNodes(entryNode, nodes);
        functionGraphMap.values().stream().forEach((FunctionGraph graph) -> nodes.addAll(graph.getNodes()));
        return getNodes();
    }

    private void addNodes(Node node, Set<Node> nodes) {
        if(nodes.add(node)){
            for(Node successor: node.getSuccessors()){
                addNodes(successor, nodes);
            }
        }
    }
}
