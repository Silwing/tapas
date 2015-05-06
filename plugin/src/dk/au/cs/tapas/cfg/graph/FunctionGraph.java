package dk.au.cs.tapas.cfg.graph;

import dk.au.cs.tapas.cfg.node.ExitNode;
import dk.au.cs.tapas.cfg.node.StartNode;
import dk.au.cs.tapas.lattice.VariableName;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 4/27/15.
 */
public interface FunctionGraph extends Graph{


    /**
     *
     * @return An array where entries are true if alias else false
     */
    boolean[] getArguments();

    boolean isAliasReturn();

    VariableName[] getArgumentNames();

    @NotNull
    @Override
    StartNode getEntryNode();

    @NotNull
    @Override
    ExitNode getExitNode();
}
