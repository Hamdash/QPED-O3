package eu.qped.java.utils.compiler;

import eu.qped.java.utils.ExtractJavaFilesFromDirectory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.plugins.processor.PluginProcessor;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
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

    private static final String DEFAULT_CLASS_PATH = "TestClass.java";
    private static final String DEFAULT_CLASS_NAME = "TestClass";

    private static final String DEFAULT_DIR_PATH = "exam-results";

    private List<Diagnostic<? extends JavaFileObject>> collectedDiagnostics;

    private String targetProjectOrClassPath;
    private String fileName;

    private String fullSourceCode;

    private List<String> options;

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
            files.add(new File(targetProjectOrClassPath));

        } else {
            ExtractJavaFilesFromDirectory.ExtractJavaFilesFromDirectoryBuilder extractJavaFilesFromDirectoryBuilder = ExtractJavaFilesFromDirectory.builder();
            ExtractJavaFilesFromDirectory extractJavaFilesFromDirectory;
            if (targetProjectOrClassPath == null || targetProjectOrClassPath.equals("")) {
                targetProjectOrClassPath = DEFAULT_DIR_PATH;
            }
            extractJavaFilesFromDirectoryBuilder.dirPath(targetProjectOrClassPath);
            extractJavaFilesFromDirectory = extractJavaFilesFromDirectoryBuilder.build();
            files = extractJavaFilesFromDirectory.filesWithJavaExtension();
            if (files.size() == 0) {
                return false;
            }
        }
        StringWriter stringWriter = new StringWriter();
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(files);

        if (options == null) {
            setDefaultOptions();
        }

        JavaCompiler.CompilationTask task = compiler.getTask(stringWriter, fileManager, diagnosticsCollector, options, null, compilationUnits);
        boolean result = task.call();

        this.setCollectedDiagnostics(diagnosticsCollector.getDiagnostics());
        return result;
    }

    public void addExternalJarsToClassPath(List<String> paths) {
        if (this.options == null){
            setDefaultOptions();
        }
        this.options.addAll(paths);
    }

    private void setDefaultOptions() {
        this.options = new ArrayList<>();
        options.add("-verbose");
        options.add("-Xlint");
        options.add("-g");
    }


    private void writeJavaFileContent(String code) {
        try (OutputStream output = Files.newOutputStream(Paths.get(targetProjectOrClassPath))) {
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

        if (isClassOrInterface) {
            String classDeclaration = answer.substring(answer.indexOf("class"), answer.indexOf("{"));

            String[] declarationArray = classDeclaration.split(" ");

            if (declarationArray.length < 2) {
                fileName = DEFAULT_CLASS_NAME;
                targetProjectOrClassPath = DEFAULT_CLASS_PATH;
            } else {
                fileName = declarationArray[1].trim(); // class name by student
                targetProjectOrClassPath = fileName + ".java";
            }
        } else {
            fileName = DEFAULT_CLASS_NAME;
            targetProjectOrClassPath = DEFAULT_CLASS_PATH;
        }
        if (isClassOrInterface) {
            javaFileContent.append(answer);
        } else {
            javaFileContent.append("/**" + "public")
                    .append(fileName)
                    .append("*/")
                    .append("\n")
                    .append("import java.util.*;")
                    .append("\n")
                    .append("public class")
                    .append(" ")
                    .append(fileName)
                    .append(" {")
                    .append("\n")
                    .append(answer)
                    .append("\n")
                    .append("}");
        }
        fullSourceCode = javaFileContent.toString();
        return javaFileContent.toString();
    }
}
