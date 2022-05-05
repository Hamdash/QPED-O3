package eu.qped.java.feedback.syntax;

import eu.qped.framework.CheckLevel;
import eu.qped.framework.Feedback;
import eu.qped.java.checkers.syntax.SyntaxChecker;
import eu.qped.java.checkers.syntax.SyntaxError;
import eu.qped.java.feedback.FeedbackGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
        errors.forEach(syntaxError -> result.add(this.getFeedback(syntaxError)));
        return result;
    }

    public SyntaxFeedback getFeedback(SyntaxError syntaxError)  {
        SyntaxFeedback result = null;
        SyntaxFeedbackData syntaxFeedbackData = SyntaxFeedbackData.builder().build();

        // TODO select
        result = SyntaxFeedbackData.getSyntaxFeedbackByErrorCode().get(syntaxError.getErrorCode()).get(0);
        return result;
    }

    public static void main(String[] args) {
        String code = ""
                + " public static void main (String[] args)  "
                + "     int i = 0;    "
                + " }"
        ;
        SyntaxChecker syntaxChecker = SyntaxChecker.builder().answer(code).level(CheckLevel.ADVANCED).build();

        syntaxChecker.check();
        System.out.println(syntaxChecker.getFeedbacks().size());
        syntaxChecker.getFeedbacks().forEach(e -> {
            System.out.println(">>>>");
            System.out.println(e.getFeedbackContent());
        });
//        System.out.println(syntaxChecker.getFeedbacks().get(0));
//        System.out.println(syntaxChecker.getFeedbacks().get(0).getFeedbackContent());
//        System.out.println(syntaxChecker.getFeedbacks().get(0).getSolutionExample());
//        System.out.println(syntaxChecker.getFeedbacks().get(0).getErrorMessage());
//        System.out.println(syntaxChecker.getFeedbacks().get(0).getBody());
//        SyntaxFeedbackGenerator syntaxFeedbackGenerator = SyntaxFeedbackGenerator.builder().build();
//        System.out.println(syntaxFeedbackGenerator.generateFeedbacks(syntaxChecker.getSyntaxErrors()));

    }
}
