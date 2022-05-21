package eu.qped.java.feedback.syntax;

import eu.qped.java.checkers.syntax.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class SyntaxFeedbackDataNew {
    private final static String NEWLINE = "\n";

    //FIXME no static
    public Map<String, List<SyntaxFeedbackNew>> getSyntaxFeedbackByErrorCode() {
        Map<String, List<SyntaxFeedbackNew>> feedbackBySyntaxErrorCode = new HashMap<>();
//        feedbackBySyntaxErrorCode.put(
//                "compiler.err.expected",
//                List.of(
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "**The compiler expects that you end statements with a \";\".**"
//                                        + "\n\n"
//                                        + "**This usually happens when you forget to write semicolon or closing parenthesis.**"
//                                )
//                                .solutionExample(""
//                                        + "// old code\n"
//                                        + "int oddNumber = 7\n"
//                                        + "// new code\n"
//                                        + "int oddNumber = 7;\n"
//                                )
//                                .errorMessage("';' expected")
//                                .build(),
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent("'(' expected")
//                                .solutionExample("")
//                                .errorMessage("'(' expected")
//                                .build(),
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent("= expected")
//                                .solutionExample("")
//                                .errorMessage("= expected")
//                                .build(),
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent("'.class' expected")
//                                .solutionExample("")
//                                .errorMessage("'.class' expected")
//                                .build(),
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent("'[' expected")
//                                .solutionExample("")
//                                .errorMessage("'[' expected")
//                                .build(),
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent("You wrote a block of code somewhere where Java does not expect it, for example: System.out.println (\"Hello\"); inside the class outside a method")
//                                .solutionExample("")
//                                .errorMessage("<identifier> expected")
//                                .build(),
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent("You have defined a data field without a name, you can define the data field")
//                                .solutionExample("like this: int variable = 5; define")
//                                .errorMessage("<identifier> expected")
//                                .build(),
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent("if you try to write a method outside of the class, you can clear this error if you write this method inside the class")
//                                .solutionExample("")
//                                .errorMessage("class, interface, or enum expected")
//                                .build(),
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent("additional curly bracket \"}\" Here the error can be corrected by simply removing the additional curly bracket \"}\" or by observing the indentation")
//                                .solutionExample("")
//                                .errorMessage("class, interface, or enum expected")
//                                .build()
//                )
//        );
//        feedbackBySyntaxErrorCode.put(
//                "compiler.err.illegal.start.of.expr",
//                List.of(
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent("Access Modifiers must not be used within the method with local variables, as their method area defines their accessibility")
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.expr+µ+variable ")
//                                                .build()
//                                )
//                                .build(),
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent("A method cannot have another method within its scope")
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.expr+µ+method")
//                                                .build()
//                                )
//                                .build(),
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "every block (method) or class definition must begin and end with curly braces. \n"
//                                        + "String Character Without Double Quotes E.g. \n"
//                                        + "A method cannot have another method within its scope \n"
//                                        + "Access Modifiers must not be used within the method with local variables, as their method area defines their accessibility."
//                                )
//                                .solutionExample("String = \"a\";")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.expr+µ+All")
//                                                .build()
//                                )
//                                .build()
//                )
//        );
//        feedbackBySyntaxErrorCode.put(
//                // TODO
//                "compiler.err.illegal.start.of.type",
//                List.of(
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "you have used the Type:"
//                                        + " for"
//                                        + " in a wrong place"
//                                )
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.type+µ+for")
//                                                .build()
//                                )
//                                .build()
//                        ,
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "you have used the Type:"
//                                        + " switch"
//                                        + " in a wrong place"
//                                )
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.type+µ+switch")
//                                                .build()
//                                )
//                                .build()
//                        ,
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "you have used the Type:"
//                                        + " while"
//                                        + " in a wrong place"
//                                )
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.type+µ+while")
//                                                .build()
//                                )
//                                .build()
//                        , SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "you have used the Type:"
//                                        + " if"
//                                        + " in a wrong place"
//                                )
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.type+µ+if")
//                                                .build()
//                                )
//                                .build()
//                        , SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "you have used the Type:"
//                                        + " else"
//                                        + " in a wrong place"
//                                )
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.type+µ+else")
//                                                .build()
//                                )
//                                .build()
//                        , SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "you have used the Type:"
//                                        + " System"
//                                        + " in a wrong place"
//                                )
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.type+µ+System")
//                                                .build()
//                                )
//                                .build()
//                        ,
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "you have used the Type:"
//                                        + " break"
//                                        + " in a wrong place"
//                                )
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.type+µ+break")
//                                                .build()
//                                )
//                                .build()
//                        ,
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "you have used the Type:"
//                                        + " continue"
//                                        + " in a wrong place"
//                                )
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.type+µ+continue")
//                                                .build()
//                                )
//                                .build()
//                        ,
//                        SyntaxFeedbackNew.builder()
//                                .feedbackContent(""
//                                        + "you have used the Type:"
//                                        + " case"
//                                        + " in a wrong place"
//                                )
//                                .solutionExample("")
//                                .errorMessage("")
//                                .errorInfo(
//                                        ErrorInfo.builder()
//                                                .errorKey("compiler.err.illegal.start.of.type+µ+case")
//                                                .build()
//                                )
//                                .build()
//                )
//
//        );
        return feedbackBySyntaxErrorCode;
    }

}
