package eu.qped.java.feedback.syntax;

import eu.qped.framework.CheckLevel;
import eu.qped.java.checkers.mass.MainSettings;
import eu.qped.java.checkers.mass.MassExecutor;
import eu.qped.java.checkers.syntax.SyntaxCheckReport;
import eu.qped.java.checkers.syntax.SyntaxChecker;
import eu.qped.java.checkers.syntax.SyntaxError;
import eu.qped.java.feedback.FeedbackGenerator;
import eu.qped.java.feedback.syntaxLagacy.SyntaxFeedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SyntaxFeedbackGeneratorNew implements FeedbackGenerator<SyntaxFeedbackNew, SyntaxError> {
    private final static String ERROR_TRIGGER_CONS = " Error code: ";
    private final static String LINE_NUMBER_CONS = " Line: ";
    private final static String ERROR_MSG_CONS = " Error type: ";
    private final static String FEEDBACK_CONS = " Feedback: ";
    private final static String NEW_LINE = "\n\n";
    private final static ArrayList<String> TYPES = new ArrayList<>();

    static {
        TYPES.add("for");
        TYPES.add("switch");
        TYPES.add("while");
        TYPES.add("if");
        TYPES.add("else");
        TYPES.add("System");
        TYPES.add("break");
        TYPES.add("continue");
        TYPES.add("case");
    }

    private List<SyntaxFeedbackNew> syntaxFeedbacks;
    private int syntaxFeedbackCounter;

    public List<SyntaxFeedbackNew> buildSyntaxFeedbackBody(List<SyntaxFeedbackNew> syntaxFeedbacks, SyntaxError error) {
        for (SyntaxFeedbackNew syntaxFeedback : syntaxFeedbacks) {
            syntaxFeedbackCounter = syntaxFeedbackCounter + 1;
            syntaxFeedback.setBody(""
//                    Error Header
                            + " \n\n "
                            + "## Error " + String.format("%02d", syntaxFeedbackCounter) + ":"
//                    Feedback Content
                            + " \n\n "
                            + syntaxFeedback.getFeedbackContent()
//                    Code Where this error happens
                            + "\n\n "
                            + "**Where this error happens:**"
                            + "\n\n "
                            + "```java\n"
                            + error.getErrorSourceCode()
                            + "\n```"
//                   Example to fix this error
                            + "\n\n "
                            + "**Example to fix this error:**"
                            + "\n\n "
                            + "```java\n"
                            + syntaxFeedback.getSolutionExample()
                            + "\n```"
            );
        }
        return null;
    }

    @Override
    public List<SyntaxFeedbackNew> generateFeedbacks(List<SyntaxError> errors) {
        List<SyntaxFeedbackNew> result = new ArrayList<>();
        for (SyntaxError error : errors) {
            List<SyntaxFeedbackNew> relatedSyntaxFeedbacks = this.getFeedback(error);
            relatedSyntaxFeedbacks = relatedSyntaxFeedbacks.stream().filter(relatedSyntaxFeedback -> {
                return relatedSyntaxFeedback.getErrorMessage().equals(error.getErrorMessage());
            }).collect(Collectors.toList());
            buildSyntaxFeedbackBody(relatedSyntaxFeedbacks, error);
            result.addAll(relatedSyntaxFeedbacks);
        }

        return result;
    }

    public List<SyntaxFeedbackNew> getFeedback(SyntaxError syntaxError) {
        List<SyntaxFeedbackNew> result = new ArrayList<>();
        SyntaxFeedbackDataNew syntaxFeedbackDataNew = SyntaxFeedbackDataNew.builder().build();
        result = SyntaxFeedbackDataNew.getSyntaxFeedbackByErrorCode().get(syntaxError.getErrorCode());
        result.stream().filter(syntaxFeedback -> {
            return syntaxFeedback.getErrorMessage().equals(syntaxError.getErrorMessage());
        });
        return result;
    }

    public static void main(String[] args) {
        String code = ""
                + "public static void main (String[] args) { \n"
                + "int b = 0;   \n"
                + "int b = 0;   \n"
                + "for(int i  = 0  ; i< 10 ; i++) { int k = 0;}    \n"
                + "} \n"
                + "public static void test () { \n"
                + "int g = 0; \n"
                + "} \n";



        SyntaxChecker syntaxChecker = SyntaxChecker.builder().stringAnswer(code).level(CheckLevel.ADVANCED).build();
        Map<String, String> mainSettings = new HashMap<>();
        mainSettings.put("semanticNeeded", "false");
        mainSettings.put("syntaxLevel", "2");
        mainSettings.put("preferredLanguage", "en");
        mainSettings.put("styleNeeded", "false");

        MainSettings mainSettingsConfiguratorConf = new MainSettings(mainSettings);

        MassExecutor massE = new MassExecutor(null, null, syntaxChecker, mainSettingsConfiguratorConf);
        massE.execute();

        List<String> result = new ArrayList<>();

        for (SyntaxFeedback syntax : massE.getSyntaxFeedbacks()) {
            String s = ""
                    + syntax.getBody()
                    + NEW_LINE
                    + "--------------------------------------------------"
                    ;
            System.out.println(s);

        }
        for (SyntaxFeedbackNew syntax : massE.getSyntaxFeedbackNews()) {
            String s = ""
                    + syntax.getBody()
                    + NEW_LINE
                    + "--------------------------------------------------"
                    ;
            System.out.println(s);

        }
    }
}
