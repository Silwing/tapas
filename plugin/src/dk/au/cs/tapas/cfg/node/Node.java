package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;

import java.util.Set;

/**
 * Created by budde on 4/22/15.
 *
 */
public interface Node {

    Node[] getSuccessors();


    Set<String> toDot();

    Set<String> toDot(Set<Node> visited);

    String toDotString();


    String toString();

    PsiElement getElement();


}
