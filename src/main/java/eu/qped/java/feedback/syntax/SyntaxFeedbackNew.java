package eu.qped.java.feedback.syntax;

import eu.qped.framework.Feedback;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
public class SyntaxFeedbackNew extends Feedback {
    private String header;
    private String feedbackMessage;
    private String errorSource;
    private String solutionExample;
    private String errorLine;
}
