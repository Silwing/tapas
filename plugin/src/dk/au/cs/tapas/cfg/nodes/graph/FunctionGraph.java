package dk.au.cs.tapas.cfg.nodes.graph;

/**
 * Created by budde on 4/27/15.
 */
public interface FunctionGraph extends Graph{


    /**
     *
     * @return An array where entries are true if alias else false
     */
    boolean[] getArguments();
}
