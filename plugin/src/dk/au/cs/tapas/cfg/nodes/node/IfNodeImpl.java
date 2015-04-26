package dk.au.cs.tapas.cfg.nodes.node;

import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/22/15.
 *
 */
public class IfNodeImpl extends NodeImpl implements IfNode {
    private final TemporaryVariableName condition;

    public IfNodeImpl(TemporaryVariableName condition, Node successor1, Node successor2) {
        super(new Node[]{successor1, successor2});
        this.condition = condition;
    }


    public void setSuccessor1(Node successor){
        successors[0] = successor;

    }

    public void setSuccessor2(Node successor){
        successors[1] = successor;

    }

    @Override
    public TemporaryVariableName getConditionName() {
        return condition;
    }

    @Override
    public Node getSuccessor1(){
        return  successors[0];

    }

    @Override
    public Node getSuccessor2(){
        return successors[1];

    }




    @Override
    public String toString() {
        return "if("+condition+")";
    }
}
