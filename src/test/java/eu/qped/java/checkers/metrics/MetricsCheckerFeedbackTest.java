package eu.qped.java.checkers.metrics;

import eu.qped.java.checkers.metrics.data.feedback.MetricsCheckerFeedback;
import eu.qped.java.checkers.metrics.data.feedback.MetricsCheckerFeedbackGenerator;
import eu.qped.java.checkers.metrics.settings.MetricsCheckerSettings;
import eu.qped.java.checkers.metrics.data.report.MetricCheckerEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for {@link MetricsCheckerFeedback}.
 *
 * @author Jannik Seus
 */
class MetricsCheckerFeedbackTest {

    private MetricsCheckerFeedback metricsCheckerFeedback1;

    @BeforeEach
    void setUp() {
        metricsCheckerFeedback1 = MetricsCheckerFeedback.builder()
                .className("TestClass")
                .metric(Metric.AMC)
                .value(99d)
                .body(Metric.AMC.getDescription())
                .suggestion("Change something!")
                .build();
    }

    @ParameterizedTest
    @EnumSource(Metric.class)
    void generateSuggestionTestMetric(Metric metric) {
        metricsCheckerFeedback1.setMetric(metric);
        metricsCheckerFeedback1.setBody(metric.getDescription());

        assertEquals(metric, metricsCheckerFeedback1.getMetric());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1d, 0d, 0.5d, 1.0d, 3.3d})
    void generateSuggestionTestValue(double value) {
        metricsCheckerFeedback1.setValue(value);
        metricsCheckerFeedback1.setSuggestion(metricsCheckerFeedback1.getSuggestion());

        assertEquals
                ("You are within the " + Metric.AMC + "'s threshold.",
                        MetricsCheckerFeedbackGenerator.generateDefaultSuggestions(
                                Metric.AMC, false, false));

        assertEquals
                ("The " + Metric.AMC + "'s value is too low: Increase your average method size, e.g. by joining multiple methods with mostly the same functionalities from over-engineering.",
                        MetricsCheckerFeedbackGenerator.generateDefaultSuggestions(
                                Metric.AMC, true, false));

        assertEquals
                ("The " + Metric.AMC + "'s value is too high: Decrease your average method size, e.g. by delegating functionalities to other newly created methods.",
                        MetricsCheckerFeedbackGenerator.generateDefaultSuggestions(
                                Metric.AMC, false, true));

        assertThrows(IllegalArgumentException.class,
                () -> MetricsCheckerFeedbackGenerator.generateDefaultSuggestions(
                        Metric.AMC,
                        true,
                        true));
        metricsCheckerFeedback1.setValue(99d);
        assertEquals(99d, metricsCheckerFeedback1.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "TestClass", "AnotherTestClass.java", "RandomName"})
    void generateSuggestionTest(String className) {
        metricsCheckerFeedback1.setClassName(className);
        assertEquals(className, metricsCheckerFeedback1.getClassName());

    }

    @ParameterizedTest
    @EnumSource(Metric.class)
    void generateMetricsCheckerFeedbackTest() {
        MetricsCheckerSettings metricsCheckerSettings = MetricsCheckerSettings.builder().build();
        List<MetricCheckerEntry> designCheckEntries =
                List.of(mock(MetricCheckerEntry.class), mock(MetricCheckerEntry.class), mock(MetricCheckerEntry.class),
                        mock(MetricCheckerEntry.class), mock(MetricCheckerEntry.class), mock(MetricCheckerEntry.class));

        assertEquals(MetricsCheckerFeedbackGenerator.generateMetricsCheckerFeedbacks(designCheckEntries, metricsCheckerSettings), List.of());
    }

    @Test
    void testToString() {
        assertEquals(
                "In class 'TestClass.java'\n" +
                        "AMC (Average Method Complexity)\n" +
                        "Measured at: 99.0\n" +
                        "Change something!",
                metricsCheckerFeedback1.toString());
        assertEquals(
                "In class 'TestClass.java'\n" +
                        "AMC (Average Method Complexity)\n" +
                        "Measured at: 99.0\n" +
                        "Change something!",
                metricsCheckerFeedback1.toString());

        assertEquals(
                "In class 'TestClass.java'\n" +
                        "AMC (Average Method Complexity)\n" +
                        "Measured at: 99.0\n" +
                        "Change something!",
                metricsCheckerFeedback1.toString());

        metricsCheckerFeedback1 = MetricsCheckerFeedback.builder().build();
        assertNotNull(metricsCheckerFeedback1);
    }
}