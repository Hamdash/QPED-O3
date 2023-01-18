package eu.qped.java.checkers.metrics;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.qped.framework.QpedQfFilesUtility;
import eu.qped.java.checkers.mass.QfMetricsSettings;
import eu.qped.java.checkers.metrics.data.feedback.MetricsFeedback;
import eu.qped.java.checkers.metrics.data.feedback.MetricsFeedbackGenerator;
import eu.qped.java.checkers.metrics.data.report.ClassMetricsEntry;
import eu.qped.java.checkers.metrics.data.report.ClassMetricsMessage;
import eu.qped.java.checkers.metrics.data.report.MetricsCheckerReport;
import eu.qped.java.checkers.metrics.settings.MetricSettings;
import eu.qped.java.checkers.metrics.settings.MetricSettingsReader;
import eu.qped.java.checkers.metrics.utils.MetricsCheckerTestUtility;

/**
 * Test class for {@link MetricsChecker}
 *
 * @author Jannik Seus
 */
class MetricsCheckerTest {

    private MetricsChecker metricsCheckerFilled;

    private final Field[] fields = MetricsChecker.class.getDeclaredFields();


    @BeforeEach
    void setUp() throws IOException {
        MetricsCheckerTestUtility.generateTestClass();

        metricsCheckerFilled = MetricsChecker.builder()
                .metricsFeedbacks(List.of())
                .qfMetricsSettings(mock(QfMetricsSettings.class))
                .solutionRoot(MetricsCheckerTestUtility.getSolutionRoot())
                .build();
    }

//    @Test
//    void testClassFilesPath() throws IllegalAccessException {
//        Field classFilesPathField = MetricsCheckerTestUtility.getFieldByName("CLASS_FILES_PATH", fields);
//        assert classFilesPathField != null;
//        classFilesPathField.setAccessible(true);
//        String classFilesPath = (String) classFilesPathField.get(MetricsChecker.class);
//        classFilesPathField.setAccessible(false);
//        assertEquals("src/main/java/eu/qped/java/utils/compiler/compiledFiles", classFilesPath);
//    }




    @Test
    void testCheck() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
        MetricsChecker metricsCheckerCustom = MetricsChecker.builder().qfMetricsSettings(MetricsCheckerTestUtility.generateSampleQFMetricsSettings()).solutionRoot(MetricsCheckerTestUtility.getSolutionRoot()).build();

        MetricsCheckerReport metricsCheckerReport = MetricsCheckerReport.builder().build();
        MetricSettingsReader metricSettingsReader = MetricSettingsReader.builder().qfMetricsSettings(MetricsCheckerTestUtility.generateSampleQFMetricsSettings()).build();
        MetricSettings metricSettings = metricSettingsReader.readMetricsCheckerSettings(MetricSettings.builder().build());

        List<File> classFiles
                = QpedQfFilesUtility.filesWithExtension(MetricsCheckerTestUtility.getSolutionRoot(), "class");
        String[] pathsToClassFiles = classFiles.stream().map(File::getPath).toArray(String[]::new);

        Method runCkjmExtendedMethod = metricsCheckerCustom.getClass().getDeclaredMethod("runCkjmExtended", MetricsCheckerReport.class, String[].class, boolean.class, boolean.class);
        runCkjmExtendedMethod.setAccessible(true);
        runCkjmExtendedMethod.invoke(metricsCheckerCustom, metricsCheckerReport, pathsToClassFiles, false, true);
        runCkjmExtendedMethod.setAccessible(false);
        metricsCheckerReport.setPathsToClassFiles(List.of(pathsToClassFiles));
        List<MetricsFeedback> metricsFeedbacks = MetricsFeedbackGenerator.generateMetricsCheckerFeedbacks(metricsCheckerReport.getMetricsMap(), metricSettings);
        metricsCheckerCustom.setMetricsFeedbacks(metricsFeedbacks);

        assertArrayEquals(metricsCheckerReport.getPathsToClassFiles().toArray(), metricsCheckerCustom.check().getPathsToClassFiles().toArray());

        List<ClassMetricsEntry> metricsMapExpected = metricsCheckerReport.getMetricsMap();
        List<ClassMetricsEntry> metricsMapActual = metricsCheckerCustom.check().getMetricsMap();

        for (int i = 0; i < metricsMapExpected.size(); i++) {
            ClassMetricsEntry classMetricsEntryExpected = metricsMapExpected.get(i);
            ClassMetricsEntry classMetricsEntryActual = metricsMapActual.get(i);
            assertEquals(classMetricsEntryExpected.getClassName(), classMetricsEntryActual.getClassName());

            List<ClassMetricsMessage> metricsForClassExpected = classMetricsEntryExpected.getMetricsForClass();
            List<ClassMetricsMessage> metricsForClassActual = classMetricsEntryActual.getMetricsForClass();
            for (int j = 0; j < metricsForClassExpected.size(); j++) {
                ClassMetricsMessage classMetricsMessageExpected = metricsForClassExpected.get(j);
                ClassMetricsMessage classMetricsMessageActual = metricsForClassActual.get(j);
                assertEquals(classMetricsMessageExpected.getMetric(), classMetricsMessageActual.getMetric());
            }
        }
        assertEquals(metricsFeedbacks, metricsCheckerCustom.getMetricsFeedbacks());
    }
    @Test
    void testToString() throws IllegalAccessException {
        Field qfMetricsSettings = MetricsCheckerTestUtility.getFieldByName("qfMetricsSettings", fields);
        assert qfMetricsSettings != null;
        qfMetricsSettings.setAccessible(true);

        assertEquals(
                metricsCheckerFilled.toString(),
                "MetricsChecker{" +
                "feedbacks=" + metricsCheckerFilled.getMetricsFeedbacks() +
                ", qfMetricsSettings=" + qfMetricsSettings.get(metricsCheckerFilled) +
                '}');
        qfMetricsSettings.setAccessible(false);
    }
}