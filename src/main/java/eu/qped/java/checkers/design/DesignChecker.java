package eu.qped.java.checkers.design;

import eu.qped.framework.qf.QfObject;
import eu.qped.java.checkers.design.ckjm.QPEDMetricsFilter;
import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import eu.qped.java.utils.ExtractJavaFilesFromDirectory;
import eu.qped.java.utils.compiler.Compiler;
import gr.spinellis.ckjm.utils.CmdLineParser;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class represents a checker for class design.
 *
 * @author Jannik Seus
 */
@Data
@Builder
public class DesignChecker {

    /**
     * answer as String
     */
    private String answer;

    /**
     * answer as a multi-class project (given as path to package)
     */
    private String targetProject;
    private Compiler compiler;

    private ArrayList<DesignFeedback> feedbacks;

    private DesignConfigurator designConfigurator;

    private final static String PATH_TO_CLASSFILES = "src/main/java/eu/qped/java/utils/compiler/compiledFiles";

    /**
     * is able to check one or multiple .class files
     * for defined metrics ({@link SaveMapResults.Metric}).
     * The output is saved in {@link DesignCheckReport#getMetricsMap()}).
     *
     * @return the built {@link DesignCheckReport}
     */
    public DesignCheckReport check() {

        DesignCheckReport.DesignCheckReportBuilder resultBuilder = DesignCheckReport.builder();
        Map<String, Map<SaveMapResults.Metric, Double>> metricsMap = new HashMap<>();
        QPEDMetricsFilter qmf = new QPEDMetricsFilter();
        CmdLineParser cmdParser = new CmdLineParser();
        SaveMapResults handler = new SaveMapResults(metricsMap);

        if (compiler == null) {
            compiler = Compiler.builder().build();
        }

        boolean compileResult;
        if (answer != null && !answer.equals("")) {
            compileResult = compiler.compileFromString(answer);
            resultBuilder.codeAsString(compiler.getFullSourceCode());
            resultBuilder.designConfigurator(this.designConfigurator);
            //TODO resultBuilder.path()
        } else {
            compileResult = compiler.compileFromProject(targetProject);
        }
        List<File> classFiles
                = ExtractJavaFilesFromDirectory.builder().dirPath(PATH_TO_CLASSFILES).build().filesWithExtension("class");
        String[] classFileNames = new String[classFiles.size()];
        for (int i = 0; i < classFiles.size(); i++) {
            classFileNames[i] = classFiles.get(i).getPath();
        }
        cmdParser.parse(classFileNames);
        qmf.runMetricsInternal(cmdParser.getClassNames(), handler);
        resultBuilder.metricsMap(handler.getOutputMetrics());

        DesignCheckReport builtReport = resultBuilder.build();
        generateBaseFeedback(builtReport.getMetricsMap());

        return builtReport;
    }

    public void generateBaseFeedback(Map<String, Map<SaveMapResults.Metric, Double>> metricsMap) {
        this.feedbacks = new ArrayList<>();
        this.feedbacks.add(new DesignFeedback(SaveMapResults.Metric.WMC.toString(),"th?","error","errorLine", "ex"));
        this.feedbacks.add(new DesignFeedback(SaveMapResults.Metric.AMC.toString(),"th?","error","errorLine", "ex"));
        this.feedbacks.add(new DesignFeedback(SaveMapResults.Metric.LCOM.toString(),"th?","error","errorLine", "ex"));
        this.feedbacks.add(new DesignFeedback(SaveMapResults.Metric.LCOM3.toString(),"th?","error","errorLine", "ex"));

    }
    // for removal

    public static void main(String[] args) {

        String answer = "import java.util.List;\n" +
                "    import java.util.ArrayList;\n" +
                "    public class Mmm{\n" +
                "        List<String> xx(){\n" +
                "            List list = new ArrayList();\n" +
                "            list.add(\"8888\");\n" +
                "            return list;\n" +
                "        }\n" +
                "    }";

        Compiler c = Compiler.builder().build();
        c.compileFromString(answer);

        String pathToClass = "/Users/jannik/ProgrammingProjects/IdeaProjects/uni/bachelorarbeit/fork/Mmm.class";

        DesignChecker b = DesignChecker.builder()
                .answer(new QfObject().getAnswer())
                .feedbacks(new ArrayList<>())
                .build();
        b.setTargetProject(pathToClass);
        b.setAnswer(answer);
        b.check().getMetricsMap().forEach((k, v) -> System.out.println(k + " : " + v));
    }
}
