package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;

/**
 * Created by budde on 4/22/15.
 *
 */
public class EndNodeImpl extends NodeImpl {
    public EndNodeImpl(PsiElement element) {
        super(new Node[0], element);
    }

    @Override
    public String toString() {
        return "end";
    }

}
