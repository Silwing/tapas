package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 5/13/15.
 */
public class ArrayAppendAssignmentNodeImpl extends NodeImpl implements ArrayAppendAssignmentNode {
    private final TemporaryVariableName targetName;
    private final TemporaryVariableName valueName;
    private final Set<HeapLocation> variableLocations;

    public ArrayAppendAssignmentNodeImpl(Node successor, TemporaryVariableName targetName, TemporaryVariableName valueName, Set<HeapLocation> variableLocations, PsiElement element) {
        super(successor, element);

        this.targetName = targetName;
        this.valueName = valueName;
        this.variableLocations = variableLocations;
    }

    @Override
    public TemporaryVariableName getTargetName() {
        return targetName;
    }

    @Override
    public TemporaryVariableName getValueName() {
        return valueName;
    }

    @Override
    public Set<HeapLocation> getVariableLocationSet() {
        return variableLocations;
    }
}
