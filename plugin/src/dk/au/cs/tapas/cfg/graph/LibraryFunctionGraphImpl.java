package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.cfg.node.EndNodeImpl;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.cfg.node.StartNodeImpl;
import org.jetbrains.annotations.Nullable;

/**
 * Created by budde on 4/27/15.
 */
public class LibraryFunctionGraphImpl implements LibraryFunctionGraph {
    private Node entryNode;
    private Node exitNode;
    private boolean[] arguments;

    public LibraryFunctionGraphImpl(boolean[] arguments) {
        exitNode = new EndNodeImpl();
        entryNode = new StartNodeImpl(exitNode);
        this.arguments = arguments;

    }

    @Override
    public boolean[] getArguments() {
        return arguments;
    }

    @Nullable
    @Override
    public Node getExitNode() {
        return exitNode;
    }

    @Nullable
    @Override
    public Node getEntryNode() {
        return entryNode;
    }
}
