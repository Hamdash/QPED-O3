package eu.qped.java.feedback.syntax;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

import static eu.qped.java.utils.markdown.MarkdownFormatterUtility.NEW_Double_LINE;

@Data
@AllArgsConstructor
@Builder
public class SyntaxFeedbackMessages {
    public Map<String, String> getFeedbackMessagesByErrorCode() {
        return new HashMap<>() {{
            put("compiler.err.already.defined"
                    , ""
                            + "The compiler expects that you don't define variables or methods twice, where they can be called (scope of that variable or method)."
            );
        }};
    }

    public Map<String, String> getFeedbackMessagesByErrorMessage() {
        return new HashMap<>() {{
            put("';' expected"
                    , ""
                            + "The compiler expects that you end statements with a \";\""
                            + NEW_Double_LINE
                            + "This usually happens when you forget to write semicolon or closing parenthesis."
            );
        }};
    }
}
