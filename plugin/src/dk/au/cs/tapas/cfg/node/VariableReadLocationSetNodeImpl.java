package dk.au.cs.tapas.cfg.node;

import com.intellij.psi.PsiElement;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.VariableName;

import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class VariableReadLocationSetNodeImpl extends NodeImpl implements VariableReadLocationSetNode {
    private Set<HeapLocation> locations;
    private VariableName variableName;

    public VariableReadLocationSetNodeImpl(Node successor, VariableName variableName, Set<HeapLocation> locations, PsiElement psiElement) {
        super(successor, psiElement);
        this.locations = locations;
        this.variableName = variableName;
    }

    @Override
    public Set<HeapLocation> getTargetLocationSet() {
        return locations;
    }


    @Override
    public String toString() {
        return "var_read(" + variableName + ", " + locations + ')';
    }

    @Override
    public VariableName getVariableName() {
        return variableName;
    }
}
