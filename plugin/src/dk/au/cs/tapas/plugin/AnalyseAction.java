package dk.au.cs.tapas.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.php.lang.psi.PhpFile;
import dk.au.cs.tapas.analysis.Analyse;
import dk.au.cs.tapas.analysis.AnalyseImpl;
import dk.au.cs.tapas.analysis.TypeAnalysisImpl;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.PsiParserImpl;
import dk.au.cs.tapas.cfg.graph.Graph;
import dk.au.cs.tapas.lattice.element.AnalysisLatticeElement;
import dk.au.cs.tapas.lattice.PrintStreamLatticePrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by budde on 2/4/15.
 *
 */
public class AnalyseAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        PhpFile psi = (PhpFile) e.getData(LangDataKeys.PSI_FILE);
        if (psi == null) {
            Messages.showErrorDialog(e.getProject(), "Could not find a file to analyse. Please focus a PHP file to perform the analysis on.", "No File in Focus");
            return;
        }

        BalloonBuilder success = JBPopupFactory.getInstance().createHtmlTextBalloonBuilder("Analysis results can be found in the results folder in the root of the project.", MessageType.INFO, null)
                .setFadeoutTime(4000);
        VirtualFile results = e.getProject().getBaseDir().findChild("results");
        if(results == null)
        {
            final Runnable directoryCreatorAnalyser = () -> {
                try {
                    e.getProject().getBaseDir().createChildDirectory(this, "results");
                    analyse(e.getProject().getBaseDir().findChild("results"), psi, e.getProject());

                        success.createBalloon()
                        .show(JBPopupFactory.getInstance().guessBestPopupLocation(e.getDataContext()), Balloon.Position.atRight);
                } catch (IOException ex) {
                    Messages.showErrorDialog(e.getProject(), "Could not create result directory.", "Directory Creation Error");
                }

            };
            ApplicationManager.getApplication().invokeLater(() ->
                            CommandProcessor.getInstance().executeCommand(e.getProject(), () -> ApplicationManager.getApplication().runWriteAction(directoryCreatorAnalyser), "DirectoryCreate", null)
            );
        } else {
            final Runnable analyser = () -> {
                analyse(results, psi, e.getProject());
                success.createBalloon().show(JBPopupFactory.getInstance().guessBestPopupLocation(e.getDataContext()), Balloon.Position.atRight);
            };
            ApplicationManager.getApplication().invokeLater(() ->
                            CommandProcessor.getInstance().executeCommand(e.getProject(), () -> ApplicationManager.getApplication().runWriteAction(analyser), "DirectoryCreate", null)
            );
        }

    }

    private void analyse(VirtualFile resultDir, PhpFile psi, Project p) {
        PsiParser parser = new PsiParserImpl();
        Graph g = parser.parseFile(psi);
        String graph = "digraph g{\n";
        String dotString = graph + g.getEntryNode().toDotString();
        dotString = parser.getFunctions().values().stream().reduce(dotString, (s,fg) -> s+fg.getEntryNode().toDotString(), (s1,s2) -> s1+s2);
        dotString += "\n}";

        Analyse analyse = new AnalyseImpl(g);

        AnalysisLatticeElement l = analyse.getExitLattice();

        String fileName = psi.getVirtualFile().getName();
        String dotFile = fileName + ".dot";
        String latticeFile = fileName + "-lattice.txt";
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(resultDir.getPath(), dotFile), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            writer.write(dotString);
            writer.close();
            l.print(new PrintStreamLatticePrinter(new PrintStream(Files.newOutputStream(Paths.get(resultDir.getPath(), latticeFile), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE))));
        } catch(IOException ex) {
            Messages.showErrorDialog(p, "An error occurred while trying to save the files.", "File Error");
        }
    }


}
