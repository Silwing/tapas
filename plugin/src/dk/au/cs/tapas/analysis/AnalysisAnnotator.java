package dk.au.cs.tapas.analysis;

import dk.au.cs.tapas.cfg.node.Node;

/**
 * Created by budde on 5/12/15.
 */
public interface AnalysisAnnotator {

    void setNode(Node node);

    void warning(String message);

    void error(String message);

    void information(String message);

}
