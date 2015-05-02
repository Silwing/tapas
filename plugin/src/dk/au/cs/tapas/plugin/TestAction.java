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
 *
 */
public class TestAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        PhpFile psi = (PhpFile) e.getData((DataKey) LangDataKeys.PSI_FILE);
        if(psi == null){
            return;
        }

        PsiParser parser = new PsiParserImpl();
        Graph g = parser.parseFile(psi);
        String dotString = g.getEntryNode().toDotString();
        Analyse analyse = new AnalyseImpl(g, new TypeAnalysisImpl());
        AnalysisLatticeElement l = analyse.getExitLattice();
        try {
            l.print(new PrintStreamLatticePrinter(new PrintStream(Files.newOutputStream(Paths.get("D:\\Randi\\Programmering\\Speciale\\tapas-survey\\plugin\\lattice.log"), StandardOpenOption.CREATE), false, "UTF-8")));
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println(g);
    }


}
