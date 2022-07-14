package eu.qped.java.checkers.design;

import eu.qped.java.checkers.design.configuration.DesignSettings;
import eu.qped.java.checkers.design.data.DesignCheckEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for {@link DesignFeedback}.
 *
 * @author Jannik Seus
 */
class DesignFeedbackTest {

    private DesignFeedback designFeedback1;

    @BeforeEach
    void setUp() {
        designFeedback1 = DesignFeedback.builder()
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
        designFeedback1.setMetric(metric);
        designFeedback1.setBody(metric.getDescription());

        assertEquals(metric, designFeedback1.getMetric());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1d, 0d, 0.5d, 1.0d, 3.3d})
    void generateSuggestionTestValue(double value) {
        designFeedback1.setValue(value);
        designFeedback1.setSuggestion(designFeedback1.getSuggestion());

        assertEquals
                ("You are within the " + Metric.AMC + "'s threshold.",
                        DesignFeedbackGenerator.generateDefaultSuggestions(
                                Metric.AMC, false, false));

        assertEquals
                ("The " + Metric.AMC + "'s value is too low: Increase your average method size, e.g. by joining multiple methods with mostly the same functionalities from over-engineering.",
                        DesignFeedbackGenerator.generateDefaultSuggestions(
                                Metric.AMC, true, false));

        assertEquals
                ("The " + Metric.AMC + "'s value is too high: Decrease your average method size, e.g. by delegating functionalities to other newly created methods.",
                        DesignFeedbackGenerator.generateDefaultSuggestions(
                                Metric.AMC, false, true));

        assertThrows(IllegalArgumentException.class,
                () -> DesignFeedbackGenerator.generateDefaultSuggestions(
                        Metric.AMC,
                        true,
                        true));
        designFeedback1.setValue(99d);
        assertEquals(99d, designFeedback1.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "TestClass", "AnotherTestClass.java", "RandomName"})
    void generateSuggestionTest(String className) {
        designFeedback1.setClassName(className);
        assertEquals(className, designFeedback1.getClassName());

    }

    @ParameterizedTest
    @EnumSource(Metric.class)
    void generateDesignFeedbackTest() {
        DesignSettings designSettings = DesignSettings.builder().build();
        List<DesignCheckEntry> designCheckEntries =
                List.of(mock(DesignCheckEntry.class), mock(DesignCheckEntry.class), mock(DesignCheckEntry.class),
                        mock(DesignCheckEntry.class), mock(DesignCheckEntry.class), mock(DesignCheckEntry.class));

        assertEquals(DesignFeedbackGenerator.generateDesignFeedbacks(designCheckEntries, designSettings), List.of());
    }

    @Test
    void testToString() {
        assertEquals(
                "In class 'TestClass.java'\n" +
                        "AMC (Average Method Complexity)\n" +
                        "Measured at: 99.0\n" +
                        "Change something!",
                designFeedback1.toString());
        assertEquals(
                "In class 'TestClass.java'\n" +
                        "AMC (Average Method Complexity)\n" +
                        "Measured at: 99.0\n" +
                        "Change something!",
                designFeedback1.toString());

        assertEquals(
                "In class 'TestClass.java'\n" +
                        "AMC (Average Method Complexity)\n" +
                        "Measured at: 99.0\n" +
                        "Change something!",
                designFeedback1.toString());

        designFeedback1 = DesignFeedback.builder().build();
        assertNotNull(designFeedback1);
    }
}