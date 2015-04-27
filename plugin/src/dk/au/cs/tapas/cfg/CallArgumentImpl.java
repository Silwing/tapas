package dk.au.cs.tapas.cfg;

/**
 * Created by budde on 4/27/15.
 */
public abstract  class CallArgumentImpl<T> implements CallArgument<T>{

    T argument;

    public CallArgumentImpl(T argument) {
        this.argument = argument;
    }

    @Override
    public T getArgument() {
        return argument;
    }


    @Override
    public String toString() {
        return  argument.toString();
    }
}
