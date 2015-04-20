package dk.au.cs.tapas.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by budde on 2/4/15.
 *
 */
public class TestAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        System.out.println(e);
    }


}
