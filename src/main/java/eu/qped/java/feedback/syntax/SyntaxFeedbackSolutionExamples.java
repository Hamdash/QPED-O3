package eu.qped.java.feedback.syntax;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
@AllArgsConstructor
@Builder
public class SyntaxFeedbackSolutionExamples {
    private static final String NEW_LINE = "\n\n";

    public Map<String, String> getSolutionExamplesByErrorCode() {
        return new HashMap<>() {{
            put("compiler.err.already.defined"
                    , ""
                            + "// old code\n"
                            + "int oddNumber = 7;\n"
                            + "int oddNumber = 7;\n"
                            + "// new code\n"
                            + "int oddNumber = 7;\n"
                            + "int NewOddNumber = 7;\n"
            );
        }};
    }

    public Map<String, String> getSolutionExamplesByErrorMessage() {
        return new HashMap<>() {{
            put("';' expected"
                    , ""
                            + "// old code\n"
                            + "int oddNumber = 7\n"
                            + "// new code\n"
                            + "int oddNumber = 7;\n"
            );
        }};
    }
}
