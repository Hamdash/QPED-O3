package eu.qped.java.checkers.design;

import eu.qped.framework.Checker;
import eu.qped.framework.qf.QfObject;
import eu.qped.java.checkers.design.ckjm.QPEDMetricsFilter;
import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import eu.qped.java.checkers.syntax.SyntaxChecker;
import eu.qped.java.utils.compiler.Compiler;
import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.PrintPlainResults;
import gr.spinellis.ckjm.ant.PrintXmlResults;
import gr.spinellis.ckjm.utils.CmdLineParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Class represents a checker for class design.
 *
 * @author Jannik Seus
 */
@Data
@Builder
public class DesignChecker {


    private String answer;

    private String targetProject;

    /**
     * is able to check one or multiple .class files
     * for defined metrics ({@link Metric}).
     * The output is printed on the console (plain or xml) or saved in {@link DesignCheckReport#getMetricsMap()} ).
     */
    private DesignCheckReport check() {
        DesignCheckReport.DesignCheckReportBuilder resultBuilder = DesignCheckReport.builder();
        Map<String, Map<Metric, Double>> metricsMap = new HashMap<>();

        QPEDMetricsFilter qmf = new QPEDMetricsFilter();
        CmdLineParser cmdParser = new CmdLineParser();

        cmdParser.parse(new String[]{targetProject});

        CkjmOutputHandler handler;
        handler = new SaveMapResults(metricsMap);

        String[] tmp = new String[1];
        qmf.runMetricsInternal(cmdParser.getClassNames().toArray(tmp), handler);

        resultBuilder.metricsMap(((SaveMapResults) handler).getOutputMetrics());
        //resultBuilder.metricsThresholds();
        //resultBuilder.codeAsString();
        //resultBuilder.path();
        return resultBuilder.build();
    }

    // for removal
    public static void main(String[] args) {

        Compiler c = Compiler.builder().build();
        c.compile(
                "    import java.util.ArrayList;\n" +
                        "import java.util.List;\n" +
                        "    public class Mmm{\n" +
                        "        List<String> xx(){\n" +
                        "            List list = new ArrayList();\n" +
                        "            list.add(\"8888\");\n" +
                        "            return list;\n" +
                        "        }\n" +
                        "    }"
        );

        String pathToClass = "/Users/jannik/ProgrammingProjects/IdeaProjects/uni/ba/fork/QPED-O3/Mmm.class";

        DesignChecker b = DesignChecker.builder().answer(new QfObject().getAnswer()).build();
        b.setTargetProject(pathToClass);
        b.check().getMetricsMap().forEach((k,v) -> System.out.println(k + " : " + v));
    }
}
