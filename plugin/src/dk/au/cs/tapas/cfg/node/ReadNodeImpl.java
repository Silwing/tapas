package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.VariableName;

/**
 * Created by budde on 4/26/15.
 */
public class ReadNodeImpl extends NodeImpl implements ReadNode {

    private final VariableName variable;
    private final TemporaryVariableName target;

    public ReadNodeImpl(VariableName variable, TemporaryVariableName target, Node successor, PsiElement psiElement) {
        super(successor, psiElement);
        this.variable = variable;
        this.target = target;
    }


    @Override
    public VariableName getVariableName() {
        return variable;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return target;
    }


    @Override
    public String toString() {
        return "read("+variable + ", " + target +")";
    }
}
