package eu.qped.java.checkers.syntax;

import eu.qped.framework.CheckLevel;
import eu.qped.java.utils.compiler.Compiler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.*;

/**
 * checker for the syntax problems in java code.
 *
 * @author Mayar Hamdhash hamdash@students.uni-marburg.de
 * @version 2.0
 * @since 17.04.2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyntaxChecker {

    private String stringAnswer;
    private String targetProject;

    @Deprecated(forRemoval = true, since = "version 2.0" )
    private CheckLevel level;

    public SyntaxCheckReport check() {
        SyntaxCheckReport.SyntaxCheckReportBuilder resultBuilder = SyntaxCheckReport.builder();
        Compiler compiler = Compiler.builder().build();
        boolean compileResult;

        if (stringAnswer != null && !stringAnswer.equals("")) {
            compileResult = compiler.compile(stringAnswer);
            resultBuilder.compiledSource(CompiledSource.STRING);
        }
        else {
            compiler.setTargetProjectPath(targetProject);
            compileResult = compiler.compile(stringAnswer);
            resultBuilder.compiledSource(CompiledSource.PROJECT);
        }
        resultBuilder.isCompilable(compileResult);

        List<Diagnostic<? extends JavaFileObject>> diagnostics = compiler.getCollectedDiagnostics();
        List<SyntaxError> collectedErrors = new ArrayList<>();
        if (!diagnostics.isEmpty()) {
            collectedErrors = analyseDiagnostics(diagnostics);
        }
        resultBuilder.syntaxErrors(collectedErrors);
        resultBuilder.path(compiler.getTargetProjectPath());
        return resultBuilder.build();
    }

    private List<SyntaxError> analyseDiagnostics(List<Diagnostic<? extends JavaFileObject>> diagnostics) {
        List<SyntaxError> syntaxErrors = new ArrayList<>();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
            String errorSource = "";
            try {
               errorSource  = diagnostic.getSource().getCharContent(false).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                errorSource = errorSource.substring((int) diagnostic.getStartPosition());
            } catch (StringIndexOutOfBoundsException e) {
                errorSource = errorSource.substring((int) diagnostic.getStartPosition() + 1);
            }
            String[] splitSource = errorSource.split(";");
            Map<String, String> addProp = new HashMap<>();

            if (diagnostic.getCode().equals("compiler.err.expected")) {
                String forExpected = errorSource.split("[{]")[0];
                addProp.put("forSemExpected", forExpected);
            }
            String errorTrigger = splitSource[0];

            syntaxErrors.add(
                    SyntaxError.builder()
                            .errorCode(diagnostic.getCode())
                            .fileName(diagnostic.getSource().getName())
                            .errorMessage(diagnostic.getMessage(Locale.GERMAN))
                            .startPos(diagnostic.getStartPosition())
                            .endPos(diagnostic.getEndPosition())
                            .line(diagnostic.getLineNumber())
                            .errorSourceCode(errorSource)
                            .additionalProperties(addProp)
                            .errorTrigger(errorTrigger)
                            .columnNumber(diagnostic.getColumnNumber())
                            .build()
            );

        }
        return syntaxErrors;
    }

}
