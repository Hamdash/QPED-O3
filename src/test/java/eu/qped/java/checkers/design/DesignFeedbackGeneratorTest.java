package eu.qped.java.checkers.design;

import eu.qped.java.checkers.design.DesignFeedbackGenerator.DefaultDesignSuggestion;
import eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.Metric;
import eu.qped.java.checkers.design.configuration.DesignSettings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link DesignFeedbackGenerator}.
 *
 * @author Jannik Seus
 */
class DesignFeedbackGeneratorTest {

    @ParameterizedTest
    @EnumSource(Metric.class)
    void generateSuggestion(Metric metric) {
        assertEquals("You are within the " + metric.toString() + "'s threshold.", DesignFeedbackGenerator.generateDefaultSuggestions(metric, false, false));
        assertEquals("The " + metric + "'s value is too low: " + DesignFeedbackGenerator.generateMetricSpecificSuggestion(metric, true), DesignFeedbackGenerator.generateDefaultSuggestions(metric, true, false));
        assertEquals("The " + metric + "'s value is too high: " + DesignFeedbackGenerator.generateMetricSpecificSuggestion(metric, false), DesignFeedbackGenerator.generateDefaultSuggestions(metric, false, true));
        assertThrows(IllegalArgumentException.class, () -> DesignFeedbackGenerator.generateDefaultSuggestions(metric, true, true));
    }

    @ParameterizedTest
    @EnumSource(Metric.class)
    void generateMetricSpecificSuggestion(Metric metric) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method generateMetricSpecificSuggestionLowerMethod = DesignFeedbackGenerator.class.getDeclaredMethod("generateMetricSpecificSuggestionLower", Metric.class);
        generateMetricSpecificSuggestionLowerMethod.setAccessible(true);
        assertEquals(DesignFeedbackGenerator.generateMetricSpecificSuggestion(metric, true), generateMetricSpecificSuggestionLowerMethod.invoke(null, metric));
        generateMetricSpecificSuggestionLowerMethod.setAccessible(false);

        Method generateMetricSpecificSuggestionUpperMethod = DesignFeedbackGenerator.class.getDeclaredMethod("generateMetricSpecificSuggestionUpper", Metric.class);
        generateMetricSpecificSuggestionUpperMethod.setAccessible(true);
        assertEquals(DesignFeedbackGenerator.generateMetricSpecificSuggestion(metric, false), generateMetricSpecificSuggestionUpperMethod.invoke(null, metric));
        generateMetricSpecificSuggestionUpperMethod.setAccessible(false);
    }

    @ParameterizedTest
    @EnumSource(Metric.class)
    void generateMetricSpecificSuggestionLower(Metric metric) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method generateMetricSpecificSuggestionLowerMethod = DesignFeedbackGenerator.class.getDeclaredMethod("generateMetricSpecificSuggestionLower", Metric.class);
        generateMetricSpecificSuggestionLowerMethod.setAccessible(true);
        DefaultDesignSuggestion defaultDesignSuggestionByMetric = getSuggestionByMetric(metric);
        assert defaultDesignSuggestionByMetric != null;
        assertEquals(defaultDesignSuggestionByMetric.getLowerBoundReachedSuggestion(), generateMetricSpecificSuggestionLowerMethod.invoke(null, metric));
        InvocationTargetException illegalArgument = assertThrows(InvocationTargetException.class, () -> generateMetricSpecificSuggestionLowerMethod.invoke(null, new Object[]{null}));
        assertEquals(IllegalArgumentException.class.getName(), illegalArgument.getTargetException().getClass().getName());
        generateMetricSpecificSuggestionLowerMethod.setAccessible(false);

    }

    @ParameterizedTest
    @EnumSource(Metric.class)
    void generateMetricSpecificSuggestionUpper(Metric metric) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method generateMetricSpecificSuggestionUpperMethod = DesignFeedbackGenerator.class.getDeclaredMethod("generateMetricSpecificSuggestionUpper", Metric.class);
        generateMetricSpecificSuggestionUpperMethod.setAccessible(true);
        DefaultDesignSuggestion defaultDesignSuggestionByMetric = getSuggestionByMetric(metric);
        assert defaultDesignSuggestionByMetric != null;
        assertEquals(defaultDesignSuggestionByMetric.getUpperBoundReachedSuggestion(), generateMetricSpecificSuggestionUpperMethod.invoke(null, metric));
        InvocationTargetException illegalArgument = assertThrows(InvocationTargetException.class, () -> generateMetricSpecificSuggestionUpperMethod.invoke(null, new Object[]{null}));
        assertEquals(IllegalArgumentException.class.getName(), illegalArgument.getTargetException().getClass().getName());
        generateMetricSpecificSuggestionUpperMethod.setAccessible(false);
    }

    // Method generateDesignFeedbacks() already covered in DesignCheckerTest.java

    private static DefaultDesignSuggestion getSuggestionByMetric(Metric metric) {
        switch (metric) {
            case AMC:
                return DefaultDesignSuggestion.AMC;
            case CA:
                return DefaultDesignSuggestion.CA;
            case CAM:
                return DefaultDesignSuggestion.CAM;
            case CBM:
                return DefaultDesignSuggestion.CBM;
            case CBO:
                return DefaultDesignSuggestion.CBO;
            case CC:
                return DefaultDesignSuggestion.CC;
            case CE:
                return DefaultDesignSuggestion.CE;
            case DAM:
                return DefaultDesignSuggestion.DAM;
            case DIT:
                return DefaultDesignSuggestion.DIT;
            case IC:
                return DefaultDesignSuggestion.IC;
            case LCOM:
                return DefaultDesignSuggestion.LCOM;
            case LCOM3:
                return DefaultDesignSuggestion.LCOM3;
            case LOC:
                return DefaultDesignSuggestion.LOC;
            case MFA:
                return DefaultDesignSuggestion.MFA;
            case MOA:
                return DefaultDesignSuggestion.MOA;
            case NOC:
                return DefaultDesignSuggestion.NOC;
            case NPM:
                return DefaultDesignSuggestion.NPM;
            case RFC:
                return DefaultDesignSuggestion.RFC;
            case WMC:
                return DefaultDesignSuggestion.WMC;
        }
        return null;
    }

    @Test
    void isThresholdReachedMetricNull() throws NoSuchMethodException {
        Method isThresholdReachedMethod = DesignFeedbackGenerator.class.getDeclaredMethod("isThresholdReached", Metric.class, DesignSettings.class, double.class, boolean.class);
        isThresholdReachedMethod.setAccessible(true);
        InvocationTargetException illegalArgument = assertThrows(InvocationTargetException.class, () -> isThresholdReachedMethod.invoke(null, null, null, 0d, false));
        assertEquals(IllegalArgumentException.class.getName(), illegalArgument.getTargetException().getClass().getName());

        isThresholdReachedMethod.setAccessible(false);
    }
}