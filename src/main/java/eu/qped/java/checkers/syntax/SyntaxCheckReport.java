package eu.qped.java.checkers.syntax;

import lombok.*;

import java.util.List;


/**
 * bildet ein Report für das Checken von Syntax ab.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyntaxCheckReport {

    private List<SyntaxError> syntaxErrors;
    private boolean isCompilable;
    private String path;
    private CompiledSource compiledSource;

    @Override
    public String toString() {
        return
                "Compilable: " + isCompilable + "\n" +
                        "path: " + path + "\n" +
                        "Compilation unit type: " + compiledSource + "\n" +
                        "errors count: " + syntaxErrors.size();
    }

}
