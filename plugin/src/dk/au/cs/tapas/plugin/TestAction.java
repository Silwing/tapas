package dk.au.cs.tapas.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.PhpFile;
import dk.au.cs.tapas.cfg.PsiParser;
import dk.au.cs.tapas.cfg.PsiParserImpl;

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
        parser.parseFile(psi);
    }


}
