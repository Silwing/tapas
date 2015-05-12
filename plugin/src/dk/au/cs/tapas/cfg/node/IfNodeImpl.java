package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/22/15.
 *
 */
public class IfNodeImpl extends NodeImpl implements IfNode {
    private final TemporaryVariableName condition;

    public IfNodeImpl(TemporaryVariableName condition, Node trueSuccessor, Node falseSuccessor, PsiElement element) {
        super(new Node[]{trueSuccessor, falseSuccessor}, element);
        this.condition = condition;
    }


    public void setTrueSuccessor(Node successor){
        successors[0] = successor;

    }

    public void setFalseSuccessor(Node successor){
        successors[1] = successor;

    }

    @Override
    public TemporaryVariableName getConditionName() {
        return condition;
    }

    @Override
    public Node getTrueSuccessor(){
        return  successors[0];

    }

    @Override
    public Node getFalseSuccessor(){
        return successors[1];

    }




    @Override
    public String toString() {
        return "if("+condition+")";
    }
}
