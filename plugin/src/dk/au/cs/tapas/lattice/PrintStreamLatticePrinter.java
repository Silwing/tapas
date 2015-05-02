package dk.au.cs.tapas.lattice;

import java.io.PrintStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Randi on 02-05-2015.
 */
public class PrintStreamLatticePrinter implements LatticePrinter {
    PrintStream stream;

    public PrintStreamLatticePrinter(PrintStream stream) {
        this.stream = stream;
    }

    private int tabs = 0;

    @Override
    public void startSection() {
        tabs++;
    }

    @Override
    public void endSection() {
        tabs--;
    }

    @Override
    public void print(String s) {
        stream.print(s);
    }

    @Override
    public void linebreak() {
        stream.println();
        stream.print(Stream.generate(() -> "\t").limit(tabs).collect(Collectors.joining()));
    }
}
