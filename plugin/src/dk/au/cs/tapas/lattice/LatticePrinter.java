package dk.au.cs.tapas.lattice;

/**
 * Created by Silwing on 30-04-2015.
 */
public interface LatticePrinter {
    void startSection();
    void endSection();
    void print(String s);
    void linebreak();
}
