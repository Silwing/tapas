package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;

/**
 * Created by budde on 4/22/15.
 *
 */
public class StartNodeImpl extends NodeImpl implements StartNode {

    public StartNodeImpl(Node successor, PsiElement element) {
        super(successor, element);
    }

    public StartNodeImpl(Node[] successors, PsiElement psiElement) {
        super(successors, psiElement);
    }

    @Override
    public String toString() {
        return "start";
    }
}
