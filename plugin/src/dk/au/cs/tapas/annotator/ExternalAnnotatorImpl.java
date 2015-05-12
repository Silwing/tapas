package dk.au.cs.tapas.annotator;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.psi.PhpFile;
import dk.au.cs.tapas.analysis.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by budde on 5/12/15.
 */
public class ExternalAnnotatorImpl extends ExternalAnnotator<PhpFile, Collection<Annotation>> {

    Map<Pair<PsiFile, Long>, Collection<Annotation>> cache = new HashMap<>();


    @Nullable
    @Override
    public PhpFile collectInformation(@NotNull PsiFile file) {
        if(!(file instanceof PhpFile)){
            return null;
        }

        return (PhpFile) file;
    }

    @Nullable
    @Override
    public Collection<Annotation> doAnnotate(PhpFile collectedInfo) {
        Pair <PsiFile, Long> pair = new PairImpl<>(collectedInfo, collectedInfo.getModificationStamp());
        // Check if file has been modified
        if(cache.containsKey(pair)){
            return cache.get(pair);
        }

        final Analyse[] analyse = new Analyse[1];

        ApplicationManager.getApplication().runReadAction(() -> {
            analyse[0] = new AnalyseImpl(collectedInfo);
        });
        Collection<Annotation> annotations = analyse[0].getAnnotations();
        cache.put(pair, annotations);
        return annotations;
    }

    @Override
    public void apply(@NotNull PsiFile file, Collection<Annotation> annotationResult, @NotNull AnnotationHolder holder) {
        for (Annotation annotation: annotationResult){
            com.intellij.lang.annotation.Annotation a = holder.createAnnotation(annotation.getSeverity(), annotation.getTextRange(), annotation.getMessage());
            if(annotation.getSeverity().equals(HighlightSeverity.ERROR)){
                a.setHighlightType(ProblemHighlightType.ERROR);
            } else if (annotation.getSeverity().equals(HighlightSeverity.WARNING)){
                a.setHighlightType(ProblemHighlightType.LIKE_UNUSED_SYMBOL);
            } else if (annotation.getSeverity().equals(HighlightSeverity.INFORMATION)){
                a.setHighlightType(ProblemHighlightType.INFORMATION);
            }
        }

    }
}
