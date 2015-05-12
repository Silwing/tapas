package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.cfg.CallArgument;

/**
 * Created by budde on 4/27/15.
 */
public class EntryNodeImpl extends NodeImpl implements EntryNode {


    private CallArgument[] arguments;

    public EntryNodeImpl(Node successor, CallArgument[] arguments, PsiElement psiElement) {
        super(successor, psiElement);
        this.arguments = arguments;
    }

    @Override
    public CallArgument[] getCallArguments() {
        return arguments;
    }
}
