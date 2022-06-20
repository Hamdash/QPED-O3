package eu.qped.java.checkers.design;

import eu.qped.java.checkers.design.ckjm.QPEDMetricsFilter;
import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import eu.qped.java.checkers.design.configuration.DesignSettings;
import eu.qped.java.checkers.design.configuration.DesignSettingsReader;
import eu.qped.java.checkers.design.data.DesignCheckReport;
import eu.qped.java.checkers.mass.QFDesignSettings;
import eu.qped.java.utils.ExtractJavaFilesFromDirectory;
import gr.spinellis.ckjm.utils.CmdLineParser;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.*;


/**
 * Class represents a checker for class design.
 *
 * @author Jannik Seus
 */
@Data
@Builder
public class DesignChecker {

    private List<DesignFeedback> feedbacks;

    private DesignSettingsReader designSettingsReader;

    private QFDesignSettings qfDesignSettings;
    private DesignSettings designSettings;

    private final static String PATH_TO_CLASSFILES = "src/main/java/eu/qped/java/utils/compiler/compiledFiles";

    /**
     * is able to check one or multiple .class files
     * for defined metrics ({@link SaveMapResults.Metric}).
     *
     * @return the built {@link DesignCheckReport}
     */
    public DesignCheckReport check() {

        DesignCheckReport designCheckReport = DesignCheckReport.builder().build();
        DesignSettingsReader designSettingsReader = DesignSettingsReader.builder().qfDesignSettings(this.qfDesignSettings).build();
        this.designSettings = designSettingsReader.readDesignSettings();

        List<File> classFiles
                = ExtractJavaFilesFromDirectory.builder().dirPath(PATH_TO_CLASSFILES).build().filesWithExtension("class");
        String[] classFileNames = classFiles.stream().map(File::getPath).toArray(String[]::new);

        runCkjmExtended(designCheckReport, classFileNames);
        designCheckReport.setFiles(List.of(classFileNames));

        this.feedbacks = DesignFeedback.generateDesignFeedback(designCheckReport.getMetricsMap(), this.designSettings);

        return designCheckReport;
    }

    /**
     * Dispatching method for program code considering CKJM-extended. Improves readability.
     *
     * @param designCheckReport the final report of the design checker
     * @param classFileNames    the .class files' names (including relative path from src root)
     */
    private void runCkjmExtended(DesignCheckReport designCheckReport, String[] classFileNames) {
        QPEDMetricsFilter qmf = new QPEDMetricsFilter();
        CmdLineParser cmdParser = new CmdLineParser();
        SaveMapResults handler = new SaveMapResults();

        cmdParser.parse(classFileNames);
        qmf.runMetricsInternal(cmdParser.getClassNames(), handler);
        designCheckReport.setMetricsMap(handler.getOutputMetrics());
    }

    @Override
    public String toString() {
        return "DesignChecker{" +
                "feedbacks=" + feedbacks +
                ", designSettingsReader=" + designSettingsReader +
                ", qfDesignSettings=" + qfDesignSettings +
                ", designSettings=" + designSettings +
                '}';
    }
}
