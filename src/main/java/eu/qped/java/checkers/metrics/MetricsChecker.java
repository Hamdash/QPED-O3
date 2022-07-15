package eu.qped.java.checkers.metrics;

import eu.qped.java.checkers.metrics.ckjm.QPEDMetricsFilter;
import eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler;
import eu.qped.java.checkers.metrics.data.feedback.MetricsCheckerFeedback;
import eu.qped.java.checkers.metrics.data.feedback.MetricsCheckerFeedbackGenerator;
import eu.qped.java.checkers.metrics.settings.MetricsCheckerSettings;
import eu.qped.java.checkers.metrics.settings.MetricsCheckerSettingsReader;
import eu.qped.java.checkers.metrics.data.report.MetricsCheckerReport;
import eu.qped.java.checkers.mass.QFMetricsSettings;
import eu.qped.java.utils.ExtractJavaFilesFromDirectory;
import gr.spinellis.ckjm.utils.CmdLineParser;
import lombok.*;

import java.io.File;
import java.util.*;


/**
 * Class represents a checker for object-oriented metrics.
 *
 * @author Jannik Seus
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricsChecker {

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private List<MetricsCheckerFeedback> metricsCheckerFeedbacks;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private QFMetricsSettings qfMetricsSettings;

    private final static String CLASS_FILES_PATH = "src/main/java/eu/qped/java/utils/compiler/compiledFiles";

    /**
     * Method is able to check one or multiple .class files
     * for defined metrics ({@link MetricCheckerEntryHandler.Metric}).
     *
     * @return the built {@link MetricsCheckerReport}
     */
    public MetricsCheckerReport check() {

        MetricsCheckerReport metricsCheckerReport = MetricsCheckerReport.builder().build();
        MetricsCheckerSettingsReader metricsCheckerSettingsReader = MetricsCheckerSettingsReader.builder().qfMetricsSettings(this.qfMetricsSettings).build();
        MetricsCheckerSettings metricsCheckerSettings = metricsCheckerSettingsReader.readMetricsCheckerSettings(MetricsCheckerSettings.builder().build());

        List<File> classFiles
                = ExtractJavaFilesFromDirectory.builder().dirPath(CLASS_FILES_PATH).build().filesWithExtension("class");
        String[] pathsToClassFiles = classFiles.stream().map(File::getPath).toArray(String[]::new);

        runCkjmExtended(metricsCheckerReport, pathsToClassFiles, metricsCheckerSettings.areCallsToToJdkIncluded(), metricsCheckerSettings.areOnlyPublicClassesIncluded());
        metricsCheckerReport.setPathsToClassFiles(List.of(pathsToClassFiles));
        this.metricsCheckerFeedbacks = MetricsCheckerFeedbackGenerator.generateMetricsCheckerFeedbacks(metricsCheckerReport.getMetricsMap(), metricsCheckerSettings);

        return metricsCheckerReport;
    }

    /**
     * Dispatching method for program code to run CKJM-extended. Improves readability.
     *
     * @param metricsCheckerReport        the final report of the design checker
     * @param classFileNames           the .class files' names (including relative path from src root)
     * @param includeCallsToJdk        determines whether to include calls to JDK when running the checker
     * @param includeOnlyPublicClasses determines whether to only include public classes when running the checker
     */
    private void runCkjmExtended(MetricsCheckerReport metricsCheckerReport, String[] classFileNames, boolean includeCallsToJdk, boolean includeOnlyPublicClasses) {
        QPEDMetricsFilter qmf = new QPEDMetricsFilter(includeCallsToJdk, includeOnlyPublicClasses);
        CmdLineParser cmdParser = new CmdLineParser();
        MetricCheckerEntryHandler handler = new MetricCheckerEntryHandler();
        cmdParser.parse(classFileNames);
        qmf.runMetricsInternal(cmdParser.getClassNames(), handler);
        metricsCheckerReport.setMetricsMap(handler.getOutputMetrics());
    }

    @Override
    public String toString() {
        return "MetricsChecker{" +
                "feedbacks=" + metricsCheckerFeedbacks +
                ", qfMetricsSettings=" + qfMetricsSettings +
                '}';
    }
}
