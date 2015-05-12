package dk.au.cs.tapas.annotator;

import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;

/**
 * Created by budde on 5/12/15.
 */
public class WarningAnnotationImpl extends AnnotationImpl{
    public WarningAnnotationImpl(PsiElement element, String message) {
        super(element, HighlightSeverity.WARNING, message);
    }
}
