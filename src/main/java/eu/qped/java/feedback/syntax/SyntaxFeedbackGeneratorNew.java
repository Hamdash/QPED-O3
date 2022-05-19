package eu.qped.java.feedback.syntax;

import eu.qped.java.checkers.syntax.SyntaxError;
import eu.qped.java.feedback.AbstractFeedbackGenerator;
import eu.qped.java.utils.markdown.MarkdownFormatterUtility;
import lombok.Builder;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Builder
public class SyntaxFeedbackGeneratorNew extends AbstractFeedbackGenerator {

    private final AtomicInteger feedbackCounter = new AtomicInteger(0);

    @Override
    public String generateHeader() {
        return "Error " + String.format("%02d", feedbackCounter.incrementAndGet()) + ":";
    }

    @Override
    public String generateFeedbackMessage(SyntaxError error) {
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
    public String generateErrorLine(SyntaxError syntaxError){
        return String.valueOf(syntaxError.getLine());
    }
    @Override
    public String generateErrorSource(SyntaxError syntaxError) {
        return syntaxError.getErrorSourceCode();
    }

    @Override
    public String generateSolutionExample(SyntaxError error) {
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




    public String formatHeader(Integer feedbackNumber) {
        return ""
                + MarkdownFormatterUtility.asHeading2("Error " + String.format("%02d", feedbackNumber) + ":")
                ;
    }


    public String formatFeedbackMessage(String feedbackMessage) {
        return ""
                + Arrays.stream(feedbackMessage.split(MarkdownFormatterUtility.NEW_LINE))
                .map(line -> MarkdownFormatterUtility.asBold(line.trim()))
                .collect(Collectors.joining(""))
                ;
    }


    public String formatErrorSource(String errorSource) {
        return ""
                + MarkdownFormatterUtility.asBold("Where this error happens:")
                + MarkdownFormatterUtility.asJavaCodeBlock("")
                ;
    }


    private String buildFeedbackSolutionExample(SyntaxFeedbackNew syntaxFeedback, SyntaxError error) {
        return ""
                + MarkdownFormatterUtility.asBold("Example to fix this error:")
                + MarkdownFormatterUtility.asJavaCodeBlock("")
                ;
    }




//    public static void main(String[] args) {
//        String code = ""
//                + "public static void main (String[] args) { \n"
//                + " int i = 0; b = 2;  \n"
////                + "int i = 0;   \n"
////                + " if (i == 1) \"one\";  \n"
////                + "int b = 0;   \n"
////                + " int b  = 3 + (2 + 3);   \n"
////                + " b = b + 10;   \n"
//                + "for(int i = 0  ; i< 10 ; i++) { int k = 0;}    \n"
//                + "} \n"
//                + "public static void test () { \n"
//                + "int g = 0; \n"
//                + "} \n";
//
//
//        SyntaxChecker syntaxChecker = SyntaxChecker.builder().stringAnswer(code).level(CheckLevel.ADVANCED).build();
//        Map<String, String> mainSettings = new HashMap<>();
//        mainSettings.put("semanticNeeded", "false");
//        mainSettings.put("syntaxLevel", "2");
//        mainSettings.put("preferredLanguage", "en");
//        mainSettings.put("styleNeeded", "false");
//
//        MainSettings mainSettingsConfiguratorConf = new MainSettings(mainSettings);
//
//        MassExecutor massE = new MassExecutor(null, null, syntaxChecker, mainSettingsConfiguratorConf);
//        massE.execute();
//
//        List<String> result = new ArrayList<>();
//
//        for (SyntaxFeedback syntax : massE.getSyntaxFeedbacks()) {
//            String s = ""
//                    + syntax.getFeedbackContent()
//                    + NEW_LINE
//                    + "--------------------------------------------------";
//            System.out.println(s);
//
//        }
//        for (SyntaxFeedbackNew syntax : massE.getSyntaxFeedbackNews()) {
//            String s = ""
//                    + syntax.getBody()
//                    + NEW_LINE
//                    + "--------------------------------------------------";
//            System.out.println(s);
//
//        }
//    }


}
