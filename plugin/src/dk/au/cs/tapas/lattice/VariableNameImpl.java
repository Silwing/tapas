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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariableNameImpl that = (VariableNameImpl) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
