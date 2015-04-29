package dk.au.cs.tapas.analysis;

import com.intellij.refactoring.changeSignature.ParameterInfo;
import dk.au.cs.tapas.cfg.node.Node;
import dk.au.cs.tapas.lattice.Context;

/**
 * Created by budde on 4/29/15.
 */
public interface ContextNodePair extends Pair<Context, Node>{

    Node getNode();

    Context getContext();

}
