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

    class test {
        public void print() {
            int x = 3;
            x = 2;
            System.out.println();
        }
    }


    public static void main(String[] args) throws IOException {
        String code = "import java.util.ArrayList;\n" +
                "import java.util.Arrays;\n" +
                "import java.util.List;\n" +
                "\n" +
                "public class GrayCode {\n" +
                "\n" +
                "    /*Die main Methode gibt einen GrayCode der laenge 2 aus\n" +
                "     /\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(grayCodeStrings(2));\n" +
                "    }\n" +
                "    /Die Methode grayCode(int n erzeugt einen GrayCode in beliebiger laenge\n" +
                "     *@param n ist die gewuenschte laenge\n" +
                "     * @return gibt den GrayCode als eine Integer Liste aus\n" +
                "     /\n" +
                "    public static List<Integer> grayCode(int n) {\n" +
                "        if (n == 0) return Arrays.asList(0);\n" +
                "        List<String> grayCodes = grayCodeStrings(n);\n" +
                "        List<Integer> solution = new ArrayList<>();\n" +
                "        for (String s : grayCodes) {\n" +
                "            solution.add(Integer.parseInt(s, 2));\n" +
                "        }\n" +
                "        return solution;\n" +
                "    }\n" +
                "    /Die Methode grayCodeStrings(int n) wandelt die Integer Liste aus der Methode grayCode in\n" +
                "     *eine String Liste um\n" +
                "     * @param n die Laenge des GrayCodes\n" +
                "     * @return gibt den GrayCode als eine String Liste aus\n" +
                "     */\n" +
                "    public static List<String> grayCodeStrings(int n) {\n" +
                "        List<String> list = new ArrayList<>();\n" +
                "        if (n == 0) {\n" +
                "            list.add(\"\");\n" +
                "            return list;\n" +
                "        } else if (n == 1) {\n" +
                "            list.add(\"0\");\n" +
                "            list.add(\"1\");\n" +
                "            return list;\n" +
                "        } else {\n" +
                "            List<String> prev = grayCodeStrings(n - 1);\n" +
                "            list.addAll(prev);\n" +
                "            for (int i = prev.size() - 1; i >= 0 i--) {\n" +
                "                String bits = list.get(i);\n" +
                "                list.set(i, \"0\" + bits);\n" +
                "                list.add(\"1\" + bits);\n" +
                "            }\n" +
                "            return list;\n" +
                "        }\n" +
                "    }\n" +
                "}";

        SyntaxChecker syntaxChecker = SyntaxChecker.builder().stringAnswer(code).build();
        System.out.println(syntaxChecker.check());
//        System.out.println(syntaxChecker.check().getSyntaxErrors().get(0).getErrorSourceCode());
    }
}
