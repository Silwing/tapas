package dk.au.cs.tapas.cfg.node;

import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import dk.au.cs.tapas.lattice.HeapLocation;
import dk.au.cs.tapas.lattice.TemporaryVariableName;

import java.util.Set;

/**
 * Created by budde on 5/12/15.
 */
public class ArrayWriteAssignmentNodeImpl extends NodeImpl implements ArrayWriteAssignmentNode {
    private TemporaryVariableName targetName;
    private TemporaryVariableName valueName;
    private TemporaryVariableName indexName;
    private Set<HeapLocation> variableLocations;

    public ArrayWriteAssignmentNodeImpl(Node successor, TemporaryVariableName targetName, TemporaryVariableName valueName, TemporaryVariableName indexName, Set<HeapLocation> variableLocations, PhpPsiElement psiElement) {
        super(successor, psiElement);

        this.targetName = targetName;
        this.valueName = valueName;
        this.indexName = indexName;
        this.variableLocations = variableLocations;
    }

    @Override
    public TemporaryVariableName getIndexName() {
        return indexName;
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
    public Set<HeapLocation> getVariableLocationsSet() {
        return variableLocations;
    }


    public String toString(){
        return "array_write_assignment("+targetName+ ", " + variableLocations+ ", " + indexName+ ", " + valueName+ ")";
    }

}
