package dk.au.cs.tapas.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.jetbrains.php.lang.psi.PhpFile;
import dk.au.cs.tapas.analysis.Analyse;
import dk.au.cs.tapas.analysis.AnalyseImpl;
import dk.au.cs.tapas.analysis.TypeAnalysisImpl;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.PsiParserImpl;
import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.lattice.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.PrintStreamLatticePrinter;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by budde on 2/4/15.
 */
public class TestAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        PhpFile psi = (PhpFile) e.getData((DataKey) LangDataKeys.PSI_FILE);
        if (psi == null) {
            return;
        }

        PsiParser parser = new PsiParserImpl();
        Graph g = parser.parseFile(psi);
        String dotString = g.getEntryNode().toDotString();
        dotString = parser.getFunctions().values().stream().reduce(dotString, (s,fg) -> s+fg.getEntryNode().toDotString(), (s1,s2) -> s1+s2);
        Analyse analyse = new AnalyseImpl(g, new TypeAnalysisImpl());
        AnalysisLatticeElement l = analyse.getExitLattice();

        l.print(new PrintStreamLatticePrinter(System.out));

    }


}
