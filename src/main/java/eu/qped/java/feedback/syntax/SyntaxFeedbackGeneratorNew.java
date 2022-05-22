package eu.qped.java.feedback.syntax;

import eu.qped.java.checkers.syntax.SyntaxError;
import lombok.Builder;

import java.util.concurrent.atomic.AtomicInteger;

@Builder
public class SyntaxFeedbackGeneratorNew extends AbstractSyntaxFeedbackGenerator {

    private final AtomicInteger feedbackCounter = new AtomicInteger(0);

    @Override
    protected String generateHeader() {
        return "Error " + String.format("%02d", feedbackCounter.incrementAndGet()) + ":";
    }

    @Override
    protected String generateFeedbackMessage(SyntaxError error) {
        String result = "";
        SyntaxFeedbackMessages syntaxFeedbackMessages = SyntaxFeedbackMessages.builder().build();
        if (syntaxFeedbackMessages.getFeedbackMessagesByErrorCode().containsKey(error.getErrorCode())) {
            result += syntaxFeedbackMessages.getFeedbackMessagesByErrorCode().get(error.getErrorCode());
        }
        if (syntaxFeedbackMessages.getFeedbackMessagesByErrorMessage().containsKey(error.getErrorMessage())) {
            result += syntaxFeedbackMessages.getFeedbackMessagesByErrorMessage().get(error.getErrorMessage());
        }
        if (result.equals("")) {
            result = error.getErrorMessage();
        }
        return result;
    }

    @Override
    protected String generateErrorLine(SyntaxError syntaxError) {
        return "At line :" + String.format("%d", syntaxError.getLine());
    }

    @Override
    protected String generateErrorSource(SyntaxError syntaxError) {
        return syntaxError.getErrorSourceCode();
    }

    @Override
    protected String generateSolutionExample(SyntaxError error) {
        String result = "";
        SyntaxFeedbackSolutionExamples syntaxFeedbackSolutionExamples = SyntaxFeedbackSolutionExamples.builder().build();
        if (syntaxFeedbackSolutionExamples.getSolutionExamplesByErrorCode().containsKey(error.getErrorCode())) {
            result += syntaxFeedbackSolutionExamples.getSolutionExamplesByErrorCode().get(error.getErrorCode());
        }
        if (syntaxFeedbackSolutionExamples.getSolutionExamplesByErrorMessage().containsKey(error.getErrorMessage())) {
            result += syntaxFeedbackSolutionExamples.getSolutionExamplesByErrorMessage().get(error.getErrorMessage());
        }
        return result;
    }

}
