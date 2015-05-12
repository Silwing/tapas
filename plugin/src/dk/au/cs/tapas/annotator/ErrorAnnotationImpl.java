package dk.au.cs.tapas.annotator;

import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;

/**
 * Created by budde on 5/12/15.
 */
public class ErrorAnnotationImpl extends AnnotationImpl {

    public ErrorAnnotationImpl(PsiElement element, String message) {
        super(element, HighlightSeverity.ERROR, message);
    }
}
