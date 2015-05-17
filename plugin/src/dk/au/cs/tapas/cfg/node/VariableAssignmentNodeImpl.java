package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.TemporaryVariableName;
import dk.au.cs.tapas.lattice.VariableName;

/**
 * Created by budde on 5/15/15.
 */
public class VariableAssignmentNodeImpl extends NodeImpl implements VariableAssignmentNode {
    private final VariableName variableName;
    private final TemporaryVariableName valueName;
    private final TemporaryVariableName targetName;

    public VariableAssignmentNodeImpl(Node successor, VariableName variableName, TemporaryVariableName valueName, TemporaryVariableName targetName, PsiElement element) {
        super(successor, element);
        this.variableName = variableName;
        this.valueName = valueName;
        this.targetName = targetName;
    }

    @Override
    public VariableName getVariableName() {
        return variableName;
    }

    @Override
    public TemporaryVariableName getValueName() {
        return valueName;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }


    @Override
    public String toString() {
        return "var_write(" + variableName + ", " + valueName + ", " + targetName + ')';
    }


}
