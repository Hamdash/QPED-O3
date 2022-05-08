package eu.qped.java.utils.compiler;


import eu.qped.java.utils.ExtractJavaFilesFromDirectory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Java compiler for source code as String.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compiler {

    private static final String DEFAULT_CLASS_PATH = "GrayCode.java";
    private static final String DEFAULT_DIR_PATH = "exam-results";

    private List<Diagnostic<? extends JavaFileObject>> collectedDiagnostics;
    private String fullSourceCode;
    private String targetProjectPath;

    /**
     * @param stringAnswer can be either FilePath or the code as a string
     * @return if the code is compilable
     */

    public boolean compile(String stringAnswer) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticsCollector, Locale.GERMANY, Charset.defaultCharset());
        List<File> files = new ArrayList<>();

        if (stringAnswer != null && !stringAnswer.equals("")) {
            createJavaClass(writeCodeAsClass(stringAnswer));
            files.add(new File(DEFAULT_CLASS_PATH));
        } else {
            ExtractJavaFilesFromDirectory.ExtractJavaFilesFromDirectoryBuilder extractJavaFilesFromDirectoryBuilder = ExtractJavaFilesFromDirectory.builder();
            ExtractJavaFilesFromDirectory extractJavaFilesFromDirectory;
            if (targetProjectPath == null || targetProjectPath.equals("")){
                targetProjectPath = DEFAULT_DIR_PATH;
            }
            extractJavaFilesFromDirectoryBuilder.dirPath(targetProjectPath);
            extractJavaFilesFromDirectory = extractJavaFilesFromDirectoryBuilder.build();
            files = extractJavaFilesFromDirectory.filesWithJavaExtension();
            if (files.size() == 0) {
                return false;
            }
        }
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(files);
        boolean result = compiler.getTask(null, fileManager, diagnosticsCollector, null, null, compilationUnits).call();
        this.setCollectedDiagnostics(diagnosticsCollector.getDiagnostics());
        return result;
    }

    private void writeJavaFileContent(String code) {
        try (OutputStream output = Files.newOutputStream(Paths.get(DEFAULT_CLASS_PATH))) {
            output.write(code.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LogManager.getLogger(getClass()).throwing(e);
        }
    }

    private void createJavaClass(String javaFileContent) {
        try {
            // create class for Answer
            writeJavaFileContent(javaFileContent);
        } catch (Exception e) {
            LogManager.getLogger(getClass()).throwing(e);
        }
    }


    /**
     * @param answer the code
     * @return If the code does not contain a class, a default class is created and return it
     */
    private String writeCodeAsClass(String answer) {
        StringBuilder javaFileContent = new StringBuilder();
        boolean isClassOrInterface = answer.contains("class") || answer.contains("interface");
        boolean isPublic = false;
        if (isClassOrInterface) {
            String classDeclaration = answer.substring(0, answer.indexOf("class"));
            isPublic = classDeclaration.contains("public");
        }
//        if (isPublic) {
//            answer = answer.substring(answer.indexOf("public") + "public".length());
//        }
        if (isClassOrInterface) {
            javaFileContent.append(answer);
        } else {
            javaFileContent.append("/**" +
                    "* Test class" +
                    "*/" +
                    "import java.util.*;" +
                    "class TestClass {").append(answer).append("}");
        }
        fullSourceCode = javaFileContent.toString();
        return javaFileContent.toString();
    }

}
