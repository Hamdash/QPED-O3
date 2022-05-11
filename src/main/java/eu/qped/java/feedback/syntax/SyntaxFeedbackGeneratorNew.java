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
//                    add compiler msg if no element in the list  --> may other template
//                    Error Header
                            + buildFeedbackHeader(syntaxFeedback, error)
//                    Feedback Content + and more Content + may use tokens
                            + buildFeedbackContent(syntaxFeedback, error)
//                    Code Where this error happens or line number
                            + buildFeedbackErrorSourceCode(syntaxFeedback, error)
//                    Example to fix this error
                            + buildFeedbackSolutionExample(syntaxFeedback, error)
                            + "\n\n"
                            + "<details> \n\n \n\n "
                            + "<summary>TITLE</summary>\n\n"
                            + "\n\n"
                            + "BODY CONTENT"
                            + "\n\n"
                            + "</details>"
            );
        }
        return null;
    }



    @Override
    public List<SyntaxFeedbackNew> generateFeedbacks(List<SyntaxError> errors) {
        List<SyntaxFeedbackNew> result = new ArrayList<>();
        for (SyntaxError error : errors) {
            List<SyntaxFeedbackNew> relatedSyntaxFeedbacks = this.getFeedback(error);
            // check for length bigger than 1 or 0
            // ignore some errors // End pos may can use
            relatedSyntaxFeedbacks = relatedSyntaxFeedbacks.stream().filter(relatedSyntaxFeedback -> {
                return relatedSyntaxFeedback.getErrorMessage() == null
                        || relatedSyntaxFeedback.getErrorMessage().equals("")
                        || relatedSyntaxFeedback.getErrorMessage().equals(error.getErrorMessage())
                        ;
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

    private String buildFeedbackHeader(SyntaxFeedbackNew syntaxFeedback, SyntaxError error) {
        return ""
                + " \n\n "
                + "## Error " + String.format("%02d", syntaxFeedbackCounter) + ":"
                ;
    }

    private String buildFeedbackSolutionExample(SyntaxFeedbackNew syntaxFeedback, SyntaxError error) {
        return ""
                + "\n\n "
                + "**Example to fix this error:**"
                + "\n\n "
                + "```java\n"
                + syntaxFeedback.getSolutionExample()
                + "\n```"
                ;
    }

    private String buildFeedbackErrorSourceCode(SyntaxFeedbackNew syntaxFeedback, SyntaxError error) {
        return ""
                + "\n\n "
                + "**Where this error happens:**"
                + "\n\n "
                + "```java\n"
                + error.getErrorSourceCode()
                + "\n```"
                ;
    }

    private String buildFeedbackContent(SyntaxFeedbackNew syntaxFeedback, SyntaxError error) {
        String result = "";
        if (syntaxFeedback.getErrorMessage() == null && syntaxFeedback.getErrorMessage().equals("")) {
            result += ""
                    + "\n\n"
                    + error.getErrorMessage()
            ;
        } else {
            result += ""
                    + "\n\n"
                    + syntaxFeedback.getFeedbackContent()
            ;
        }
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
                    + syntax.getFeedbackContent()
                    + NEW_LINE
                    + "--------------------------------------------------";
            System.out.println(s);

        }
        for (SyntaxFeedbackNew syntax : massE.getSyntaxFeedbackNews()) {
            String s = ""
                    + syntax.getBody()
                    + NEW_LINE
                    + "--------------------------------------------------";
            System.out.println(s);

        }
    }
}
