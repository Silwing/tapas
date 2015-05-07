package dk.au.cs.tapas.lattice;

import com.jetbrains.php.lang.psi.elements.Variable;

/**
 * Created by Silwing on 28-04-2015.
 */
public class VariableNameImpl implements VariableName {
    private String name;

    public VariableNameImpl(String n) {
        name = n;
    }

    public VariableNameImpl(Variable variable) {
        this(variable.getName());
    }

    @Override
    public boolean equals(Object o) {
       return o == this || (o instanceof VariableName && ((VariableName) o).getName().equals(getName()));

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() { return getName(); }
}
