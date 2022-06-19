package eu.qped.java.checkers.design;

import eu.qped.framework.qf.QfObject;
import eu.qped.java.checkers.design.ckjm.QPEDMetricsFilter;
import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import eu.qped.java.checkers.mass.QFDesignSettings;
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

    private DesignSettingsReader designSettingsReader;

    private QFDesignSettings qfDesignSettings;
    private DesignSettings designSettings;

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
        DesignSettingsReader designSettingsReader = DesignSettingsReader.builder().qfDesignSettings(this.qfDesignSettings).build();
        this.designSettings = designSettingsReader.readDesignSettings();

        if (compiler == null) {
            compiler = Compiler.builder().build();
        }

        boolean compileResult;
        if (answer != null && !answer.equals("")) {
            compileResult = compiler.compileFromString(answer);
            resultBuilder.codeAsString(compiler.getFullSourceCode());
            resultBuilder.designSettingsReader(this.designSettingsReader);
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

        runCkjmExtended(resultBuilder, metricsMap, classFileNames);

        DesignCheckReport builtReport = resultBuilder.build();
        generateBaseFeedback(builtReport.getMetricsMap(), this.designSettings);

        return builtReport;
    }

    private void runCkjmExtended(DesignCheckReport.DesignCheckReportBuilder resultBuilder, Map<String, Map<SaveMapResults.Metric, Double>> metricsMap, String[] classFileNames) {
        QPEDMetricsFilter qmf = new QPEDMetricsFilter();
        CmdLineParser cmdParser = new CmdLineParser();
        SaveMapResults handler = new SaveMapResults(metricsMap);

        cmdParser.parse(classFileNames);
        qmf.runMetricsInternal(cmdParser.getClassNames(), handler);
        resultBuilder.metricsMap(handler.getOutputMetrics());
    }

    public void generateBaseFeedback(Map<String, Map<SaveMapResults.Metric, Double>> metricsMap, DesignSettings designSettings) {
        this.feedbacks = new ArrayList<>();

        for (Map.Entry entry : metricsMap.entrySet()) {

            String className = (String) entry.getKey();
            Map<SaveMapResults.Metric, Double> innerMap = (Map<SaveMapResults.Metric, Double>) entry.getValue();

            if (innerMap != null) {
                for (Map.Entry innerEntry : innerMap.entrySet()) {
                    SaveMapResults.Metric metric = (SaveMapResults.Metric) innerEntry.getKey();
                    Double metricValue = (Double) innerEntry.getValue();

                    boolean[] isThresholdReached = isThresholdReached(metric, metricValue);
                    boolean lowerThreshold = isThresholdReached[0];
                    boolean upperThreshold = isThresholdReached[1];
                    String suggestion = generateSuggestion(metric, lowerThreshold, upperThreshold);
                    this.feedbacks.add(new DesignFeedback(className, metric.getDescription(), metric, metricValue,lowerThreshold, upperThreshold, suggestion));
                }
            }
        }
    }

    private boolean[] isThresholdReached(SaveMapResults.Metric metric, double value) {
        boolean lower = false;
        boolean upper = false;

        switch (metric) {
            case AMC:
                if(value > this.designSettings.getAmcMax()) upper = true;
                if(value < this.designSettings.getAmcMin()) lower = true;
                break;
            case CAM:
                if(value > this.designSettings.getCamMax()) upper = true;
                if(value < this.designSettings.getCamMin()) lower = true;
                break;
            case CA:
                if(value > this.designSettings.getCaMax()) upper = true;
                if(value < this.designSettings.getCaMin()) lower = true;
                break;
            case CBM:
                if(value > this.designSettings.getCbmMax()) upper = true;
                if(value < this.designSettings.getCbmMin()) lower = true;
                break;
            case CBO:
                if(value > this.designSettings.getCboMax()) upper = true;
                if(value < this.designSettings.getCboMin()) lower = true;
                break;
            case CC:
                if(value > this.designSettings.getCcMax()) upper = true;
                if(value < this.designSettings.getCcMin()) lower = true;
                break;
            case CE:
                if(value > this.designSettings.getCeMax()) upper = true;
                if(value < this.designSettings.getCeMin()) lower = true;
                break;
            case CIS:
                if(value > this.designSettings.getCisMax()) upper = true;
                if(value < this.designSettings.getCisMin()) lower = true;
                break;
            case DAM:
                if(value > this.designSettings.getDamMax()) upper = true;
                if(value < this.designSettings.getDamMin()) lower = true;
                break;
            case DIT:
                if(value > this.designSettings.getDitMax()) upper = true;
                if(value < this.designSettings.getDitMin()) lower = true;
                break;
            case IC:
                if(value > this.designSettings.getIcMax()) upper = true;
                if(value < this.designSettings.getIcMin()) lower = true;
                break;
            case LCOM:
                if(value > this.designSettings.getLcomMax()) upper = true;
                if(value < this.designSettings.getLcomMin()) lower = true;
                break;
            case LCOM3:
                if(value > this.designSettings.getLcom3Max()) upper = true;
                if(value < this.designSettings.getLcom3Min()) lower = true;
                break;
            case LOC:
                if(value > this.designSettings.getLocMax()) upper = true;
                if(value < this.designSettings.getLocMin()) lower = true;
                break;
            case MOA:
                if(value > this.designSettings.getMoaMax()) upper = true;
                if(value < this.designSettings.getMoaMin()) lower = true;
                break;
            case MFA:
                if(value > this.designSettings.getMfaMax()) upper = true;
                if(value < this.designSettings.getMfaMin()) lower = true;
                break;
            case NOC:
                if(value > this.designSettings.getNocMax()) upper = true;
                if(value < this.designSettings.getNocMin()) lower = true;
                break;
            case NPM:
                if(value > this.designSettings.getNpmMax()) upper = true;
                if(value < this.designSettings.getNpmMin()) lower = true;
                break;
            case RFC:
                if(value > this.designSettings.getRfcMax()) upper = true;
                if(value < this.designSettings.getRfcMin()) lower = true;
                break;
            case WMC:
                if(value > this.designSettings.getWmcMax()) upper = true;
                if(value < this.designSettings.getWmcMin()) lower = true;
                break;
        }
        return new boolean[]{lower, upper};
    }

    private String generateSuggestion(SaveMapResults.Metric metric, boolean lowerThreshold, boolean upperThreshold) {
        if (!lowerThreshold && !upperThreshold) {
            return "You are within the " + metric.toString() + "'s threshold.";
        } else if (lowerThreshold) {
            return "The " + metric.toString() + "'s value is too low.";
        } else if (upperThreshold) {
            return "The " + metric.toString() + "'s value is too high.";
        } else {
            throw new IllegalArgumentException();
        }
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
