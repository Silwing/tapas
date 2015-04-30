package dk.au.cs.tapas.lattice;

/**
 * Created by Silwing on 28-04-2015.
 */
public class VariableNameImpl implements VariableName {
    private String name;

    public VariableNameImpl(String n) {
        name = n;
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
}
