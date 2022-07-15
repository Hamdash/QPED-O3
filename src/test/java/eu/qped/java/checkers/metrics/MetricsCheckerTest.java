package eu.qped.java.checkers.metrics;

import eu.qped.java.checkers.metrics.data.feedback.MetricsCheckerFeedback;
import eu.qped.java.checkers.metrics.data.feedback.MetricsCheckerFeedbackGenerator;
import eu.qped.java.checkers.metrics.data.report.MetricCheckerEntry;
import eu.qped.java.checkers.metrics.data.report.MetricsCheckerMessage;
import eu.qped.java.checkers.metrics.data.report.MetricsCheckerReport;
import eu.qped.java.checkers.metrics.settings.MetricsCheckerSettings;
import eu.qped.java.checkers.metrics.settings.MetricsCheckerSettingsReader;
import eu.qped.java.checkers.metrics.utils.MetricsCheckerTestUtility;
import eu.qped.java.checkers.mass.QFMetricsSettings;
import eu.qped.java.utils.ExtractJavaFilesFromDirectory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for {@link MetricsChecker}
 *
 * @author Jannik Seus
 */
class MetricsCheckerTest {

    private MetricsChecker metricsCheckerEmpty;
    private MetricsChecker metricsCheckerFilled;
    private MetricsChecker metricsCheckerNoArgs;

    private final Field[] fields = MetricsChecker.class.getDeclaredFields();


    @BeforeEach
    void setUp() {
        MetricsCheckerTestUtility.generateTestClass();

        metricsCheckerEmpty = MetricsChecker.builder().build();
        metricsCheckerFilled = MetricsChecker.builder()
                .metricsCheckerFeedbacks(List.of())
                .qfMetricsSettings(mock(QFMetricsSettings.class))
                .build();
        metricsCheckerNoArgs = new MetricsChecker();
    }

    @Test
    void testClassFilesPath() throws IllegalAccessException {
        Field classFilesPathField = MetricsCheckerTestUtility.getFieldByName("CLASS_FILES_PATH", fields);
        assert classFilesPathField != null;
        classFilesPathField.setAccessible(true);
        String classFilesPath = (String) classFilesPathField.get(MetricsChecker.class);
        classFilesPathField.setAccessible(false);
        assertEquals("src/main/java/eu/qped/java/utils/compiler/compiledFiles", classFilesPath);
    }

    @Test
    void testEmptyMetricsChecker() throws IllegalAccessException {
        assertNull(metricsCheckerEmpty.getMetricsCheckerFeedbacks());

        Field qfMetricsSettings = MetricsCheckerTestUtility.getFieldByName("qfMetricsSettings", fields);
        assert qfMetricsSettings != null;
        qfMetricsSettings.setAccessible(true);
        assertNull(qfMetricsSettings.get(metricsCheckerEmpty));
        qfMetricsSettings.setAccessible(false);
    }

    @Test
    void testFilledMetricsChecker() throws IllegalAccessException {
        assertNotNull(metricsCheckerFilled.getMetricsCheckerFeedbacks());

        Field qfMetricsSettings = MetricsCheckerTestUtility.getFieldByName("qfMetricsSettings", fields);
        assert qfMetricsSettings != null;
        qfMetricsSettings.setAccessible(true);
        assertNotNull(qfMetricsSettings.get(metricsCheckerFilled));
        qfMetricsSettings.setAccessible(false);
    }
    @Test
    void testNoArgsMetricsChecker() throws IllegalAccessException {
        assertNull(metricsCheckerNoArgs.getMetricsCheckerFeedbacks());

        Field qfMetricsSettingsField = MetricsCheckerTestUtility.getFieldByName("qfMetricsSettings", fields);
        assert qfMetricsSettingsField != null;
        qfMetricsSettingsField.setAccessible(true);
        assertNull(qfMetricsSettingsField.get(metricsCheckerNoArgs));
        qfMetricsSettingsField.setAccessible(false);
    }


    @Test
    void testCheck() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        MetricsChecker metricsCheckerCustom = MetricsChecker.builder().qfMetricsSettings(MetricsCheckerTestUtility.generateSampleQFMetricsSettings()).build();

        MetricsCheckerReport metricsCheckerReport = MetricsCheckerReport.builder().build();
        MetricsCheckerSettingsReader metricsCheckerSettingsReader = MetricsCheckerSettingsReader.builder().qfMetricsSettings(MetricsCheckerTestUtility.generateSampleQFMetricsSettings()).build();
        MetricsCheckerSettings metricsCheckerSettings = metricsCheckerSettingsReader.readMetricsCheckerSettings(MetricsCheckerSettings.builder().build());

        List<File> classFiles
                = ExtractJavaFilesFromDirectory.builder().dirPath("src/main/java/eu/qped/java/utils/compiler/compiledFiles").build().filesWithExtension("class");
        String[] pathsToClassFiles = classFiles.stream().map(File::getPath).toArray(String[]::new);

        Method runCkjmExtendedMethod = metricsCheckerCustom.getClass().getDeclaredMethod("runCkjmExtended", MetricsCheckerReport.class, String[].class, boolean.class, boolean.class);
        runCkjmExtendedMethod.setAccessible(true);
        runCkjmExtendedMethod.invoke(metricsCheckerCustom, metricsCheckerReport, pathsToClassFiles, false, true);
        runCkjmExtendedMethod.setAccessible(false);
        metricsCheckerReport.setPathsToClassFiles(List.of(pathsToClassFiles));
        List<MetricsCheckerFeedback> metricsCheckerFeedbacks = MetricsCheckerFeedbackGenerator.generateMetricsCheckerFeedbacks(metricsCheckerReport.getMetricsMap(), metricsCheckerSettings);
        metricsCheckerCustom.setMetricsCheckerFeedbacks(metricsCheckerFeedbacks);

        assertArrayEquals(metricsCheckerReport.getPathsToClassFiles().toArray(), metricsCheckerCustom.check().getPathsToClassFiles().toArray());

        List<MetricCheckerEntry> metricsMapExpected = metricsCheckerReport.getMetricsMap();
        List<MetricCheckerEntry> metricsMapActual = metricsCheckerCustom.check().getMetricsMap();

        for (int i = 0; i < metricsMapExpected.size(); i++) {
            MetricCheckerEntry metricCheckerEntryExpected = metricsMapExpected.get(i);
            MetricCheckerEntry metricCheckerEntryActual = metricsMapActual.get(i);
            assertEquals(metricCheckerEntryExpected.getClassName(), metricCheckerEntryActual.getClassName());

            List<MetricsCheckerMessage> metricsForClassExpected = metricCheckerEntryExpected.getMetricsForClass();
            List<MetricsCheckerMessage> metricsForClassActual = metricCheckerEntryActual.getMetricsForClass();
            for (int j = 0; j < metricsForClassExpected.size(); j++) {
                MetricsCheckerMessage metricsCheckerMessageExpected = metricsForClassExpected.get(j);
                MetricsCheckerMessage metricsCheckerMessageActual = metricsForClassActual.get(j);
                assertEquals(metricsCheckerMessageExpected.getMetric(), metricsCheckerMessageActual.getMetric());
            }
        }
        assertEquals(metricsCheckerFeedbacks, metricsCheckerCustom.getMetricsCheckerFeedbacks());
    }
    @Test
    void testToString() throws IllegalAccessException {
        Field qfMetricsSettings = MetricsCheckerTestUtility.getFieldByName("qfMetricsSettings", fields);
        assert qfMetricsSettings != null;
        qfMetricsSettings.setAccessible(true);

        assertEquals(
                metricsCheckerFilled.toString(),
                "MetricsChecker{" +
                "feedbacks=" + metricsCheckerFilled.getMetricsCheckerFeedbacks() +
                ", qfMetricsSettings=" + qfMetricsSettings.get(metricsCheckerFilled) +
                '}');
        qfMetricsSettings.setAccessible(false);
    }
}