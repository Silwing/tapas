package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;

/**
 * Created by budde on 4/22/15.
 *
 */
public class SkipNodeImpl extends NodeImpl implements SkipNode {

    public SkipNodeImpl(Node successor, PsiElement element) {
        super(successor, element);
    }

    public SkipNodeImpl(Node[] successors, PsiElement element) {
        super(successors, element);
    }

    public String toString(){
        return "[nop]";
    }

}
