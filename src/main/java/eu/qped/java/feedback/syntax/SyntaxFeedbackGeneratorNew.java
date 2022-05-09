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
                            + "## Error" + String.format("%02d", syntaxFeedbackCounter) + ":"
//                    Feedback Content
                            + " \n\n "
                            + " * ** "
                            + syntaxFeedback.getFeedbackContent()
                            + "**"
//                    Code Where this error happens
                            + "\n\n"
                            + "* Where this error happens:"
                            + "\n\n"
                            + "```java"
                            + error.getErrorSourceCode()
                            + "```"
//                   Example to fix this error
                            + "\n\n"
                            + "* Where this error happens:"
                            + "\n\n"
                            + "```java"
                            + syntaxFeedback.getSolutionExample()
                            + "```"
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
                + "int b = 0   \n"
                + "for(int i  = 0  ; i< 10 ; i++) { int k = 0;}    \n"
                + "} \n"
                + "public static void test () { \n"
                + "int g = 0; \n"
                + "} \n";

//        String code = " class Test {\n" +
//                "    private int i ;\n" +
//                "\n" +
//                "    public static void main(String[] args) {\n" +
//                "        int i = 0;\n" +
//                "        for (int j = 0; j < ; 10 j++) {\n" +
//                "            \n" +
//                "        }\n" +
//                "    }\n" +
//                "}";


        SyntaxChecker syntaxChecker = SyntaxChecker.builder().stringAnswer(code).level(CheckLevel.ADVANCED).build();
//        SyntaxChecker syntaxChecker = SyntaxChecker.builder().level(CheckLevel.ADVANCED).build();
//        SyntaxCheckReport syntaxCheckReport = syntaxChecker.check();

//        if(syntaxCheckReport != null) {
//            for(SyntaxError syntaxError : syntaxCheckReport.getSyntaxErrors()) {
//                System.out.println(syntaxError);
//            }
//        }

//        SyntaxFeedbackGeneratorNew syntaxFeedbackNew = SyntaxFeedbackGeneratorNew.builder().build();
//        List<SyntaxFeedbackNew> syntaxFeedbacks = syntaxFeedbackNew.generateFeedbacks(syntaxCheckReport.getSyntaxErrors());
//        for (SyntaxFeedbackNew feedback : syntaxFeedbacks) {
//            System.out.println(feedback.getBody());
//        }


        Map<String, String> mainSettings = new HashMap<>();
        mainSettings.put("semanticNeeded", "false");
        mainSettings.put("syntaxLevel", "2");
        mainSettings.put("preferredLanguage", "en");
        mainSettings.put("styleNeeded", "false");
//
//
        MainSettings mainSettingsConfiguratorConf = new MainSettings(mainSettings);
//
        MassExecutor massE = new MassExecutor(null, null, syntaxChecker, mainSettingsConfiguratorConf);
        massE.execute();

        List<String> result = new ArrayList<>();
//        System.out.println(massE.getSyntaxFeedbacks());
        for (eu.qped.java.feedback.syntaxLagacy.SyntaxFeedback syntax : massE.getSyntaxFeedbacks()) {
            result.add(""
                            + syntax.getFeedbackContent()
//                    + NEW_LINE
//                    + syntax.getBody()
//                    + NEW_LINE
//                    + syntax.getSolutionExample()
//                    + NEW_LINE
                            + "--------------------------------------------------"
            );
        }

//        System.out.println(result);
//        SyntaxFeedbackGeneratorNew syntaxFeedbackGenerator = SyntaxFeedbackGeneratorNew.builder().build();
//        List<SyntaxError> syntaxErrors = syntaxChecker.getSyntaxErrors();
//        for (SyntaxError syntaxError : syntaxErrors) {
//            try {
//
//                CharSequence charSequence = syntaxError.getSource().getCharContent(false);
//                CharSequence contentInError = charSequence.subSequence((int) syntaxError.getStartPos(), (int) syntaxError.getEndPos());
//                CharSequence contentInError1 = charSequence.subSequence((int) syntaxError.getStartPos(), charSequence.length());
////                System.out.println(">>>>" + charSequence);
////                System.out.println(">>>>" + charSequence.subSequence((int)syntaxError.getStartPos(), charSequence.length()));
////                System.out.println(">>>>" + syntaxChecker.getSourceCode());
////                System.out.println(">>>>" + syntaxChecker.getSourceCode().substring((int)syntaxError.getStartPos()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        List<SyntaxFeedbackNew> newSyntaxFeedbacks = syntaxFeedbackGenerator.generateFeedbacks(syntaxChecker.getSyntaxErrors());
//        System.out.println(newSyntaxFeedbacks.get(0).getBody());
    }
}
