package dk.au.cs.tapas.cfg;

import dk.au.cs.tapas.lattice.HeapLocation;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by budde on 4/27/15.
 */
public class HeapLocationSetCallArgumentImpl extends CallArgumentImpl<Set<HeapLocation>> implements HeapLocationSetCallArgument{

    public HeapLocationSetCallArgumentImpl(){
        this(new HashSet<>());
    }

    public HeapLocationSetCallArgumentImpl(Set<HeapLocation> argument) {
        super(argument);
    }
}
