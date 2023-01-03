package eu.qped.java.checkers.syntax.feedback;

import eu.qped.framework.CheckLevel;
import eu.qped.framework.feedback.Feedback;
import eu.qped.framework.feedback.FeedbackManager;
import eu.qped.framework.feedback.RelatedLocation;
import eu.qped.framework.feedback.Type;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedback;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedbacksStore;
import eu.qped.framework.feedback.fromatter.MarkdownFeedbackFormatter;
import eu.qped.framework.feedback.template.TemplateBuilder;
import eu.qped.java.checkers.syntax.SyntaxChecker;
import eu.qped.java.checkers.syntax.SyntaxSetting;
import eu.qped.java.checkers.syntax.analyser.SyntaxError;
import eu.qped.java.utils.FileExtensions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static eu.qped.framework.feedback.defaultfeedback.DefaultFeedbackDirectoryProvider.provideDefaultFeedbackDirectory;

@Data
@AllArgsConstructor
@Builder
public class SyntaxFeedbackGenerator {

    private DefaultFeedbacksStore defaultFeedbacksStore;
    private FeedbackManager feedbackManager;

    public List<String> generateFeedbacks(List<SyntaxError> errors, SyntaxSetting syntaxSetting) {
        List<Feedback> nakedFeedbacks = generateNakedFeedbacks(errors, syntaxSetting);
        nakedFeedbacks = adaptFeedbackByCheckLevel(syntaxSetting, nakedFeedbacks);
        if(feedbackManager == null)  feedbackManager = FeedbackManager.builder().build();
        feedbackManager.setFeedbacks(nakedFeedbacks);
        return  feedbackManager.buildFeedbackInTemplate(syntaxSetting.getLanguage());
    }

    private List<Feedback> adaptFeedbackByCheckLevel(SyntaxSetting syntaxSetting, List<Feedback> feedbacks) {
        if (syntaxSetting.getCheckLevel().equals(CheckLevel.BEGINNER)) {
            return feedbacks;
        } else {
            return feedbacks.stream().peek(feedback -> feedback.setHints(Collections.emptyList())).collect(Collectors.toList());
        }
    }

    private List<Feedback> generateNakedFeedbacks(List<SyntaxError> errors, SyntaxSetting syntaxSetting) {
        List<Feedback> result = new ArrayList<>();
        if (defaultFeedbacksStore == null) {
            defaultFeedbacksStore = new DefaultFeedbacksStore(
                    provideDefaultFeedbackDirectory(SyntaxChecker.class)
                    , syntaxSetting.getLanguage() + FileExtensions.JSON
            );
        }

        for (SyntaxError error : errors) {
            var feedback = Feedback.builder().build();
            feedback.setType(Type.CORRECTION);
            feedback.setCheckerName(SyntaxChecker.class.getSimpleName());
            if (defaultFeedbacksStore.getRelatedDefaultFeedbackByTechnicalCause(error.getErrorMessage()) != null ) {
                feedback.updateFeedback(defaultFeedbacksStore.getRelatedDefaultFeedbackByTechnicalCause(error.getErrorMessage()));
            } else if ( defaultFeedbacksStore.getRelatedDefaultFeedbackByTechnicalCause(error.getErrorCode()) != null ) {
                feedback.updateFeedback(defaultFeedbacksStore.getRelatedDefaultFeedbackByTechnicalCause(error.getErrorCode()));
            } else  {
                feedback.setTechnicalCause(error.getErrorMessage());
                feedback.setReadableCause(error.getErrorMessage());
            }
            feedback.setRelatedLocation(RelatedLocation.builder()
                    .startLine((int) error.getLine())
                    .methodName("")
                    .fileName(
                            error.getFileName() != null && error.getFileName().equals("TestClass.java") ? "" : error.getFileName()
                    )
                    .build()
            );
            result.add(feedback);
        }
        return result;
    }



}
