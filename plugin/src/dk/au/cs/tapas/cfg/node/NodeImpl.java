package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import com.sun.net.httpserver.Authenticator;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by budde on 4/22/15.
 *
 */
public abstract class NodeImpl implements Node {


    protected final Node[] successors;
    private final PsiElement element;

    public NodeImpl(Node successor, PsiElement element) {
        this(new Node[]{successor}, element);
    }


    public NodeImpl(Node[] successors, PsiElement element) {
        this.successors = successors;
        this.element = element;
    }

    @Override
    public Node[] getSuccessors() {
        return successors.clone();
    }


    @Override
    public Set<String> toDot() {
        return toDot(new HashSet<>());

    }

    @Override
    public String toDotString() {
        return toDot().stream().reduce("", (s1, s2) -> s1 +s2);
    }

    @Override
    public Set<String> toDot(Set<Node> visited) {
        Set<String> returnSet = new HashSet<>();
        if (visited.contains(this)) {
            return returnSet;
        }
        visited.add(this);
        returnSet.add(hashCode()+"[label=\""+toString().replace("\"", "\\\"")+"\"]\n");
        for (Node child : getSuccessors()) {
            returnSet.add(hashCode() + "->" + child.hashCode() + "\n");
            returnSet.addAll(child.toDot(new HashSet<>(visited)));
        }

        return returnSet;
    }

    @Override
    public PsiElement getElement() {
        return element;
    }
}
