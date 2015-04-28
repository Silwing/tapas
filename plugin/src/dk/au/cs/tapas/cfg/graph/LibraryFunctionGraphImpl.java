package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.cfg.node.*;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 4/27/15.
 */
public class LibraryFunctionGraphImpl implements LibraryFunctionGraph {
    private final boolean aliasReturn;
    private StartNode entryNode;
    private ExitNode exitNode;
    private boolean[] arguments;

    public LibraryFunctionGraphImpl(boolean[] arguments, boolean aliasReturn) {
        exitNode = new ExitNodeImpl();
        entryNode = new StartNodeImpl(exitNode);
        this.arguments = arguments;
        this.aliasReturn = aliasReturn;

    }

    @Override
    public boolean[] getArguments() {
        return arguments;
    }

    @Override
    public boolean isAliasReturn() {
        return aliasReturn;
    }

    @NotNull
    @Override
    public ExitNode getExitNode() {
        return exitNode;
    }

    @NotNull
    @Override
    public StartNode getEntryNode() {
        return entryNode;
    }
}
