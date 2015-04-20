package dk.au.cs.tapas.lattice;

/**
 * Created by budde on 4/19/15.
 *
 */
public interface NullLatticeElement extends LatticeElement<NullLatticeElement>{
    NullLatticeElement bottom = new BottomNullLatticeElementImpl();
    NullLatticeElement top= new TopNullLatticeElementImpl();
}
