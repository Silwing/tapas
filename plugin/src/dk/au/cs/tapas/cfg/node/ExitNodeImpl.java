package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.cfg.CallArgument;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by budde on 4/27/15.
 */
public class ExitNodeImpl extends NodeImpl implements ExitNode{
    private List<CallArgument> targetName;

    public ExitNodeImpl(PsiElement psiElement){
        this(new CallArgument[0], psiElement);
    }

    public ExitNodeImpl(CallArgument[] targetName, PsiElement psiElement) {
        super(new Node[0], psiElement);
        this.targetName = new LinkedList<>(Arrays.asList(targetName));
    }


    @Override
    public CallArgument[] getCallArguments() {
        return targetName.toArray(new CallArgument[targetName.size()]);
    }

    public void addCallArgument(CallArgument argument){
        this.targetName.add(argument);

    }


    @Override
    public String toString() {
        return "exit(" + targetName + ')';
    }
}
