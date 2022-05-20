package eu.qped.java.feedback.syntax;

import eu.qped.java.checkers.syntax.SyntaxError;
import eu.qped.java.utils.markdown.MarkdownFormatterUtility;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class SyntaxFeedbackFormatter {

    public static SyntaxFeedbackNew formatFeedback(SyntaxFeedbackNew syntaxFeedback) {
        syntaxFeedback.setHeader(formatHeader(syntaxFeedback.getHeader()));
        syntaxFeedback.setFeedbackMessage(formatFeedbackMessage(syntaxFeedback.getFeedbackMessage()));
        syntaxFeedback.setErrorSource(formatErrorSource(syntaxFeedback.getErrorSource()));
        syntaxFeedback.setSolutionExample(buildFeedbackSolutionExample(syntaxFeedback.getSolutionExample()));
        return syntaxFeedback;
    }


    private static String formatHeader(String feedbackHeader) {
        return ""
                + MarkdownFormatterUtility.asHeading2(feedbackHeader)
                ;
    }


    private static String formatFeedbackMessage(String feedbackMessage) {
        return ""
                + Arrays.stream(feedbackMessage.split(MarkdownFormatterUtility.NEW_Double_LINE))
                .map(line -> MarkdownFormatterUtility.asBold(line.trim()))
                .collect(Collectors.joining(""))
                ;
    }


    private static String formatErrorSource(String errorSource) {
        return ""
                + MarkdownFormatterUtility.asBold("Where this error happens:")
                + MarkdownFormatterUtility.asJavaCodeBlock(errorSource)
                ;
    }


    private static String buildFeedbackSolutionExample(String solutionExample) {
        return ""
                + MarkdownFormatterUtility.asBold("Example to fix this error:")
                + MarkdownFormatterUtility.asJavaCodeBlock(solutionExample)
                ;
    }
}
