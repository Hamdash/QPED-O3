package eu.qped.java.checkers.design;

import eu.qped.java.checkers.design.ckjm.SaveMapResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Represents a test class for {@link DesignFeedback}.
 *
 * @author Jannik Seus
 */
class DesignFeedbackTest {

    private DesignFeedback designFeedback1;

    @BeforeEach
    void setUp() {
        designFeedback1 = DesignFeedback.builder()
                .className("TestClass")
                .metric(SaveMapResults.Metric.AMC)
                .value(0d)
                .body(SaveMapResults.Metric.AMC.getDescription())
                .lowerThresholdReached(false)
                .upperThresholdReached(false)
                .suggestion("Change something!").build();
    }

    @ParameterizedTest
    @EnumSource(SaveMapResults.Metric.class)
    void generateSuggestionTestMetric(SaveMapResults.Metric metric) {
        designFeedback1.setMetric(metric);
        designFeedback1.setBody(metric.getDescription());

        assertEquals(metric, designFeedback1.getMetric());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1d, 0d, 0.5d, 1.0d, 3.3d})
    void generateSuggestionTestValue(double value) {
        designFeedback1.setValue(value);
        designFeedback1.setLowerThresholdReached(designFeedback1.isLowerThresholdReached());
        designFeedback1.setUpperThresholdReached(designFeedback1.isUpperThresholdReached());
        designFeedback1.setSuggestion(designFeedback1.getSuggestion());

        assertEquals
                ("You are within the " + SaveMapResults.Metric.AMC + "'s threshold.",
                DesignFeedback.generateSuggestion(
                        SaveMapResults.Metric.AMC,
                        designFeedback1.isLowerThresholdReached(),
                        designFeedback1.isUpperThresholdReached()));

        designFeedback1.setLowerThresholdReached(true);

        assertEquals
                ("The " + SaveMapResults.Metric.AMC + "'s value is too low.",
                        DesignFeedback.generateSuggestion(
                                SaveMapResults.Metric.AMC,
                                designFeedback1.isLowerThresholdReached(),
                                designFeedback1.isUpperThresholdReached()));

        designFeedback1.setLowerThresholdReached(false);
        designFeedback1.setUpperThresholdReached(true);

        assertEquals
                ("The " + SaveMapResults.Metric.AMC + "'s value is too high.",
                        DesignFeedback.generateSuggestion(
                                SaveMapResults.Metric.AMC,
                                designFeedback1.isLowerThresholdReached(),
                                designFeedback1.isUpperThresholdReached()));

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "TestClass", "Testclass.java", "2143fwsqacy"})
    void generateSuggestionTest(String className) {
        designFeedback1.setClassName(className);
        assertEquals(className, designFeedback1.getClassName());

    }
}