package eu.qped.java.feedback.syntax;

import eu.qped.framework.CheckLevel;
import eu.qped.framework.Feedback;
import eu.qped.java.checkers.mass.MainSettings;
import eu.qped.java.checkers.mass.MassExecutor;
import eu.qped.java.checkers.mass.QFSemSettings;
import eu.qped.java.checkers.mass.QFStyleSettings;
import eu.qped.java.checkers.semantics.SemanticConfigurator;
import eu.qped.java.checkers.style.StyleChecker;
import eu.qped.java.checkers.style.StyleConfigurator;
import eu.qped.java.checkers.syntax.SyntaxChecker;
import eu.qped.java.checkers.syntax.SyntaxError;
import eu.qped.java.feedback.FeedbackGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tools.ant.taskdefs.Javadoc;

import javax.tools.JavaFileObject;
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
public class SyntaxFeedbackGenerator implements FeedbackGenerator<SyntaxError> {
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

    private List<SyntaxFeedback> syntaxFeedbacks;
    private CheckLevel level;
    private String sourceCode;
    private String example;

    @Override
    public List<Feedback> generateFeedbacks(List<SyntaxError> errors) {
        List<Feedback> result = new ArrayList<>();
        errors.forEach(syntaxError -> {
            // build Feedback body
            List<SyntaxFeedback> relatedSyntaxFeedbacks = this.getFeedback(syntaxError);
            relatedSyntaxFeedbacks = relatedSyntaxFeedbacks.stream().filter(relatedSyntaxFeedback -> {
               return relatedSyntaxFeedback.getErrorMessage().equals(syntaxError.getErrorMsg());
            }).collect(Collectors.toList());
            relatedSyntaxFeedbacks.forEach(relatedSyntaxFeedback -> {
                try {
                    CharSequence source = syntaxError.getSource().getCharContent(false);
                    relatedSyntaxFeedback.setBody(""
                            + "* Compiler error\n\n"
                            + "* About this Error: " + this.getFeedback(syntaxError).get(0).getFeedbackContent() + "\n\n"
                            + "* Cause of error:\n\n"
                                + "-------------------------------------------"
                                + source.subSequence(0 , (int) syntaxError.getStartPos())
                            + "^"
                            + source.subSequence((int) syntaxError.getStartPos(),source.length()) + ""
                            + "\n\n"
                            + "-------------------------------------------"
                            + "* Example to fix this error: "
                            + relatedSyntaxFeedback.getSolutionExample()
                            + ""
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            result.addAll(relatedSyntaxFeedbacks);
        });
        return result;
    }

    public List<SyntaxFeedback> getFeedback(SyntaxError syntaxError) {
        List<SyntaxFeedback> result = new ArrayList<>();
        SyntaxFeedbackData syntaxFeedbackData = SyntaxFeedbackData.builder().build();
        result = SyntaxFeedbackData.getSyntaxFeedbackByErrorCode().get(syntaxError.getErrorCode());
        result.stream().filter(syntaxFeedback -> {
            return syntaxFeedback.getErrorMessage().equals(syntaxError.getErrorMsg());
        });
        return result;
    }

    public static void main(String[] args) {
        String code = ""
                + "public static void main (String[] args){  "
                    + "int i = 0    "
                + "}"
                + "public static void test () { "
                    + "int g = 0;"
                + "}"
                ;

        SyntaxChecker syntaxChecker = SyntaxChecker.builder().answer(code).level(CheckLevel.ADVANCED).build();
//



        Map<String, String> mainSettings = new HashMap<>();
        mainSettings.put("semanticNeeded", "false");
        mainSettings.put("syntaxLevel", "2");
        mainSettings.put("preferredLanguage", "en");
        mainSettings.put("styleNeeded", "false");


        MainSettings mainSettingsConfiguratorConf = new MainSettings(mainSettings);

        MassExecutor massE = new MassExecutor(null, null, syntaxChecker, mainSettingsConfiguratorConf);
        massE.execute();
        List<String> result = new ArrayList<>();
//        System.out.println(massE.getSyntaxFeedbacks());
        for (eu.qped.java.checkers.syntax.SyntaxFeedback syntax : massE.getSyntaxFeedbacks()) {
            result.add(""
                    + syntax.getFeedbackContent()
                    + NEW_LINE
                    + syntax.getBody()
                    + NEW_LINE
                    + syntax.getSolutionExample()
                    + NEW_LINE
                    + "--------------------------------------------------"
            );
        }
        System.out.println(result);
        SyntaxFeedbackGenerator syntaxFeedbackGenerator = SyntaxFeedbackGenerator.builder().build();
        List<SyntaxError> syntaxErrors = syntaxChecker.getSyntaxErrors();
        for (SyntaxError syntaxError : syntaxErrors) {
            try {

                CharSequence charSequence = syntaxError.getSource().getCharContent(false);
                CharSequence contentInError = charSequence.subSequence((int) syntaxError.getStartPos(), (int) syntaxError.getEndPos());
                CharSequence contentInError1 = charSequence.subSequence((int) syntaxError.getStartPos(), charSequence.length());
//                System.out.println(">>>>" + charSequence);
//                System.out.println(">>>>" + charSequence.subSequence((int)syntaxError.getStartPos(), charSequence.length()));
//                System.out.println(">>>>" + syntaxChecker.getSourceCode());
//                System.out.println(">>>>" + syntaxChecker.getSourceCode().substring((int)syntaxError.getStartPos()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Feedback> newSyntaxFeedbacks = syntaxFeedbackGenerator.generateFeedbacks(syntaxChecker.getSyntaxErrors());
        System.out.println(newSyntaxFeedbacks.get(0).getBody());
    }
}
