package eu.qped.java.checkers.design;

import eu.qped.java.checkers.design.configuration.DesignSettings;
import eu.qped.java.checkers.design.configuration.MetricThreshold;
import eu.qped.java.checkers.design.data.DesignCheckEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
                .metric(Metric.AMC)
                .value(0d)
                .body(Metric.AMC.getDescription())
                .lowerThresholdReached(false)
                .upperThresholdReached(false)
                .suggestion("Change something!").build();
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
        designFeedback1.setLowerThresholdReached(designFeedback1.isLowerThresholdReached());
        designFeedback1.setUpperThresholdReached(designFeedback1.isUpperThresholdReached());
        designFeedback1.setSuggestion(designFeedback1.getSuggestion());

        assertEquals
                ("You are within the " + Metric.AMC + "'s threshold.",
                        DesignFeedback.generateSuggestion(
                                Metric.AMC,
                                designFeedback1.isLowerThresholdReached(),
                                designFeedback1.isUpperThresholdReached()));

        designFeedback1.setLowerThresholdReached(true);

        assertEquals
                ("The " + Metric.AMC + "'s value is too low.",
                        DesignFeedback.generateSuggestion(
                                Metric.AMC,
                                designFeedback1.isLowerThresholdReached(),
                                designFeedback1.isUpperThresholdReached()));

        designFeedback1.setLowerThresholdReached(false);
        designFeedback1.setUpperThresholdReached(true);

        assertEquals
                ("The " + Metric.AMC + "'s value is too high.",
                        DesignFeedback.generateSuggestion(
                                Metric.AMC,
                                designFeedback1.isLowerThresholdReached(),
                                designFeedback1.isUpperThresholdReached()));

        designFeedback1.setLowerThresholdReached(true);
        designFeedback1.setUpperThresholdReached(true);

        assertThrows(IllegalArgumentException.class,
                () -> DesignFeedback.generateSuggestion(
                        Metric.AMC,
                        true,
                        true));

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "TestClass", "Testclass.java", "2143fwsqacy"})
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

        assertEquals(DesignFeedback.generateDesignFeedbacks(designCheckEntries, designSettings), List.of());
    }

    @ParameterizedTest
    @EnumSource(Metric.class)
    void isThresholdReachedTest(Metric metric) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DesignSettings designSettings = setMetric(metric);
        Method isThresholdReachedMethod = DesignFeedback.class.getDeclaredMethod("isThresholdReached", Metric.class, DesignSettings.class, double.class, boolean.class);
        isThresholdReachedMethod.setAccessible(true);
        assertEquals(true, isThresholdReachedMethod.invoke(DesignFeedback.class, metric, designSettings, 2.0, false));
        assertEquals(true, isThresholdReachedMethod.invoke(DesignFeedback.class, metric, designSettings, -1.0, true));
        assertEquals(false, isThresholdReachedMethod.invoke(DesignFeedback.class, metric, designSettings, 0.5, true));
        assertEquals(false, isThresholdReachedMethod.invoke(DesignFeedback.class, metric, designSettings, 0.5, false));
        isThresholdReachedMethod.setAccessible(false);
    }

    @Test
    void testToString() {
        assertEquals(
                "threshold of Metric 'AMC' in class 'TestClass' not exceeded\tValue=0.0,\t suggestion: Average Method Complexity",
                designFeedback1.toString());
        designFeedback1.setLowerThresholdReached(true);
        assertEquals(
                "Lower threshold of metric 'AMC' in class 'TestClass' exceeded.\tThresholds: (0.0, 1.0)Value=0.0,\t suggestion: Average Method Complexity",
                designFeedback1.toString());
        designFeedback1.setLowerThresholdReached(false);
        designFeedback1.setUpperThresholdReached(true);

        assertEquals(
                "Upper threshold of metric 'AMC' in class 'TestClass' exceeded.\tThresholds: (0.0, 1.0)Value=0.0,\t suggestion: Average Method Complexity",
                designFeedback1.toString());

        designFeedback1 = DesignFeedback.builder().build();
        assertNotNull(designFeedback1);
        assertThrows(NullPointerException.class, () -> designFeedback1.toString());
    }

    private static DesignSettings setMetric(Metric metric) {
        DesignSettings designSettings = new DesignSettings();

        switch (metric) {
            case AMC:
                designSettings.setAmc(new MetricThreshold(metric, 0, 1));
                break;
            case CAM:
                designSettings.setCam(new MetricThreshold(metric, 0, 1));
                break;
            case CA:
                designSettings.setCa(new MetricThreshold(metric, 0, 1));
                break;
            case CBM:
                designSettings.setCbm(new MetricThreshold(metric, 0, 1));
                break;
            case CBO:
                designSettings.setCbo(new MetricThreshold(metric, 0, 1));
                break;
            case CC:
                designSettings.setCc(new MetricThreshold(metric, 0, 1));
                break;
            case CE:
                designSettings.setCe(new MetricThreshold(metric, 0, 1));
                break;
            case CIS:
                designSettings.setCis(new MetricThreshold(metric, 0, 1));
                break;
            case DAM:
                designSettings.setDam(new MetricThreshold(metric, 0, 1));
                break;
            case DIT:
                designSettings.setDit(new MetricThreshold(metric, 0, 1));
                break;
            case IC:
                designSettings.setIc(new MetricThreshold(metric, 0, 1));
                break;
            case LCOM:
                designSettings.setLcom(new MetricThreshold(metric, 0, 1));
                break;
            case LCOM3:
                designSettings.setLcom3(new MetricThreshold(metric, 0, 1));
                break;
            case LOC:
                designSettings.setLoc(new MetricThreshold(metric, 0, 1));
                break;
            case MOA:
                designSettings.setMoa(new MetricThreshold(metric, 0, 1));
                break;
            case MFA:
                designSettings.setMfa(new MetricThreshold(metric, 0, 1));
                break;
            case NOC:
                designSettings.setNoc(new MetricThreshold(metric, 0, 1));
                break;
            case NPM:
                designSettings.setNpm(new MetricThreshold(metric, 0, 1));
                break;
            case RFC:
                designSettings.setRfc(new MetricThreshold(metric, 0, 1));
                break;
            case WMC:
                designSettings.setWmc(new MetricThreshold(metric, 0, 1));
                break;
        }
        return designSettings;
    }
}