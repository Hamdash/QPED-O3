package eu.qped.java.checkers.design;

import eu.qped.framework.Checker;
import eu.qped.framework.qf.QfObject;
import eu.qped.java.checkers.design.ckjm.QPEDMetricsFilter;
import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.PrintPlainResults;
import gr.spinellis.ckjm.ant.PrintXmlResults;
import gr.spinellis.ckjm.utils.CmdLineParser;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * design checker
 */
public class DesignChecker implements Checker {

    /**
     * the map where the calculated metrics are stored in.
     *  (k1: classname, v1: (k2: metric, v2: value) )
     */
    private Map<String, Map<Metric, Double>> metricsMap;

    /**
     * designsSettings for the design checker, previously as args in main, or standard input.
     * -s (include jdk), -p (only public classes), -x (XML console), -m (Save to map)
     * //TODO refactor
     */
    private String[] designSettings;

    public DesignChecker(String[] designSettings) {
        this.metricsMap = new HashMap<>();
        this.designSettings = designSettings;
    }

    /**
     * is able to check one or multiple .class files
     * for defined metrics ({@link Metric}).
     * The output is printed on the console (plain or xml) or saved in {@link #metricsMap).
     */
    private void check() {
        QPEDMetricsFilter qmf = new QPEDMetricsFilter();
        CmdLineParser cmdParser = new CmdLineParser();

        cmdParser.parse(designSettings);

        if (cmdParser.isArgSet("s")) {
            qmf.setIncludeJdk(true);
        }
        if (cmdParser.isArgSet("p")) {
            qmf.setOnlyPublic(true);
        }

        CkjmOutputHandler handler;
        if (cmdParser.isArgSet("x")) {
            handler = new PrintXmlResults(new PrintStream(System.out));
        } else if (cmdParser.isArgSet("m")) {
            handler = new SaveMapResults(this.metricsMap);
        } else {
            handler = new PrintPlainResults(System.out);
        }

        String[] tmp = new String[1];
        qmf.runMetricsInternal(cmdParser.getClassNames().toArray(tmp), handler);
    }

    //TODO implement actual check method to be used by QuarterFall
    @Override
    public void check(QfObject qfObject) throws Exception {
    }



    // TODO example
    //  for removal
    public static void main(String[] args) {
        String pathToClass1 = "/Users/jannik/ProgrammingProjects/IdeaProjects/uni/ba/fork/QPED-O3/GrayCode.class";
        String pathToClass2 = "/Users/jannik/ProgrammingProjects/IdeaProjects/uni/ba/fork/QPED-O3/Mmm.class";
        String[] designCheckerSettings = new String[]{pathToClass1, pathToClass2, "-m"};
        DesignChecker b = new DesignChecker(designCheckerSettings);
        b.check();

        // visualizing results saved in map on console (to be deleted)
        b.metricsMap.forEach((k,v) -> System.out.println(k + " : " + v));
    }
}
