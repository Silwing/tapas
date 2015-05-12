package dk.au.cs.tapas.annotator;

import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by budde on 5/12/15.
 */
public class AnnotationImpl implements Annotation{


    private final HighlightSeverity severity;
    private final String message;
    private final TextRange element;

    public AnnotationImpl(@NotNull PsiElement element, @NotNull HighlightSeverity severity, @NotNull String message) {
        this.element = element.getTextRange();
        this.severity = severity;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public TextRange getTextRange() {
        return element;
    }

    @Override
    public HighlightSeverity getSeverity() {
        return severity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AnnotationImpl that = (AnnotationImpl) object;

        if (!severity.equals(that.severity)) return false;
        if (!message.equals(that.message)) return false;
        return element.equals(that.element);

    }

    @Override
    public int hashCode() {
        int result = severity.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + element.hashCode();
        return result;
    }
}
