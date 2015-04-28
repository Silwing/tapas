package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.cfg.node.Node;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by budde on 4/28/15.
 */
public class FinalGraphImpl implements Graph {
    private final Node exitNode, entryNode;

    public FinalGraphImpl(Graph graph) {

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
}