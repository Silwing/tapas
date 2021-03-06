package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.cfg.Constant;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

/**
 * Created by budde on 4/26/15.
 *
 */
public class ReadConstNodeImpl extends NodeImpl implements ReadConstNode{


    private Constant constant;
    private TemporaryVariableName targetName;

    public ReadConstNodeImpl(Node successor, Constant constant, TemporaryVariableName targetName, PsiElement psiElement) {
        super(successor, psiElement);
        this.constant = constant;
        this.targetName = targetName;
    }

    @Override
    public Constant getConstant() {
        return constant;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }

    @Override
    public String toString() {
        return "constant_read(" + constant + ", " + targetName + ')';
    }
}
