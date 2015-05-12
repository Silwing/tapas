package dk.au.cs.tapas.annotator;

import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;

/**
 * Created by budde on 5/12/15.
 */
public class AnnotationImpl implements Annotation{


    private final HighlightSeverity severity;
    private final String message;
    private final PsiElement element;

    public AnnotationImpl(PsiElement element, HighlightSeverity severity, String message) {
        this.element = element;
        this.severity = severity;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public PsiElement getPsiElement() {
        return element;
    }

    @Override
    public HighlightSeverity getSeverity() {
        return severity;
    }
}
