package dk.au.cs.tapas.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.jetbrains.php.lang.psi.PhpFile;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.PsiParserImpl;
import dk.au.cs.tapas.cfg.graph.Graph;

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
        dotString = parser.getFunctions().values().stream()
                .map((graph) -> graph.getEntryNode().toDotString())
                .reduce(dotString, (s1, s2) -> s1 + s2);
        System.out.println(g);
    }


}
