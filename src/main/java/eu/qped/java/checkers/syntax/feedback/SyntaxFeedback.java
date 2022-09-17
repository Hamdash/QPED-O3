package eu.qped.java.checkers.syntax.feedback;

import eu.qped.framework.Feedback;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@Deprecated(forRemoval = true)
public class SyntaxFeedback extends Feedback {
    private String header;
    private String feedbackMessage;
    private String errorSource;
    private String solutionExample;
    private String errorLine;

    @Override
    public String toString() {
        return header + feedbackMessage + errorLine + errorSource + solutionExample;
    }
}
