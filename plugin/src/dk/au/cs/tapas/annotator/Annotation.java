package dk.au.cs.tapas.annotator;

import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;

/**
 * Created by budde on 5/12/15.
 */
public interface Annotation {

    HighlightSeverity getSeverity();

    TextRange getTextRange();

    String getMessage();
}
