package eu.qped.java.checkers.metrics.data;

import eu.qped.java.checkers.metrics.data.report.MetricsCheckerMessage;
import eu.qped.java.checkers.metrics.data.report.MetricsCheckerMessageMulti;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.Metric.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link MetricsCheckerMessageMulti}.
 *
 * @author Jannik Seus
 */
public class MetricsCheckerMessageMultiTest extends MetricsCheckerMessageTest {


    protected MetricsCheckerMessage metricsCheckerMessage1;

    @Override
    @BeforeEach
    void setUp() {
        Map<String, Integer> metricValues = Map.of("void method2()", 2, "void method3()", 4);
        metricsCheckerMessage1 = new MetricsCheckerMessageMulti(CC, metricValues);
        randomMetricsCheckerMessages = new ArrayList<>(List.of(metricsCheckerMessage1));
        sortedMetricsCheckerMessages = new ArrayList<>(List.of(metricsCheckerMessage1));
    }

    // compareTo does not make sense here (yet) because CC is the only possible metric type of
    // DesignCheckMessageMulti implemented.


    @Test
    void getMetricValuesTest() {
        String key1 = "void method2()";
        String key2 = "void method3()";

        Map<String, Integer> metricValues = ((MetricsCheckerMessageMulti) metricsCheckerMessage1).getMetricValues();

        assertTrue(metricValues.containsKey(key1));
        assertTrue(metricValues.containsKey(key2));

        assertEquals(2, metricValues.get(key1));
        assertEquals(4, metricValues.get(key2));
    }

    @Override
    protected ArrayList<MetricsCheckerMessage> createRandomMetricsCheckerMessages() {
        return new ArrayList<>(List.of(metricsCheckerMessage1));
    }

    @Override
    protected ArrayList<MetricsCheckerMessage> createSortedMetricsCheckerMessages() {
        return new ArrayList<>(List.of(metricsCheckerMessage1));
    }
}
