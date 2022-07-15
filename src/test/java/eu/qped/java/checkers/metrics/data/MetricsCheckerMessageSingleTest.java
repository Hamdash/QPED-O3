package eu.qped.java.checkers.metrics.data;

import eu.qped.java.checkers.metrics.data.report.MetricsCheckerMessage;
import eu.qped.java.checkers.metrics.data.report.MetricsCheckerMessageSingle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.Metric.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link MetricsCheckerMessageSingle}.
 *
 * @author Jannik Seus
 */
public class MetricsCheckerMessageSingleTest extends MetricsCheckerMessageTest {


    protected MetricsCheckerMessage metricsCheckerMessage1;
    protected MetricsCheckerMessage metricsCheckerMessage2;
    protected MetricsCheckerMessage metricsCheckerMessage3;
    protected MetricsCheckerMessage metricsCheckerMessage4;

    @Override
    @BeforeEach
    void setUp() {
        metricsCheckerMessage1 = new MetricsCheckerMessageSingle(AMC, 28.0);
        metricsCheckerMessage2 = new MetricsCheckerMessageSingle(CE, 3.0);
        metricsCheckerMessage3 = new MetricsCheckerMessageSingle(LCOM, 2.032);
        metricsCheckerMessage4 = new MetricsCheckerMessageSingle(WMC, 6.503);
        sortedMetricsCheckerMessages = new ArrayList<>(List.of(metricsCheckerMessage1, metricsCheckerMessage2, metricsCheckerMessage3, metricsCheckerMessage4));
        randomMetricsCheckerMessages = new ArrayList<>(List.of(metricsCheckerMessage3, metricsCheckerMessage1, metricsCheckerMessage4, metricsCheckerMessage2));

    }

    @Test
    void getMetricValueTest() {

        double metricValue1 = ((MetricsCheckerMessageSingle) metricsCheckerMessage1).getMetricValue();
        double metricValue2 = ((MetricsCheckerMessageSingle) metricsCheckerMessage2).getMetricValue();
        double metricValue3 = ((MetricsCheckerMessageSingle) metricsCheckerMessage3).getMetricValue();
        double metricValue4 = ((MetricsCheckerMessageSingle) metricsCheckerMessage4).getMetricValue();

        assertEquals( 28.0, metricValue1);
        assertEquals( 3.0, metricValue2);
        assertEquals( 2.032, metricValue3);
        assertEquals( 6.503, metricValue4);
    }

    @Override
    protected ArrayList<MetricsCheckerMessage> createRandomMetricsCheckerMessages() {
        return new ArrayList<>(List.of(metricsCheckerMessage3, metricsCheckerMessage1, metricsCheckerMessage4, metricsCheckerMessage2));
    }

    @Override
    protected ArrayList<MetricsCheckerMessage> createSortedMetricsCheckerMessages() {
        return new ArrayList<>(List.of(metricsCheckerMessage1, metricsCheckerMessage2, metricsCheckerMessage3, metricsCheckerMessage4));
    }
}
