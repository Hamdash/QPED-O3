package eu.qped.java.checkers.metrics.data;

import eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler;
import eu.qped.java.checkers.metrics.data.report.MetricsCheckerMessage;
import eu.qped.java.checkers.metrics.data.report.MetricsCheckerMessageMulti;
import eu.qped.java.checkers.metrics.data.report.MetricsCheckerMessageSingle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Test class for {@link MetricsCheckerMessage}.
 *
 * @author Jannik Seus
 */
abstract class MetricsCheckerMessageTest {

    ArrayList<MetricsCheckerMessage> randomMetricsCheckerMessages;
    ArrayList<MetricsCheckerMessage> sortedMetricsCheckerMessages;

    @BeforeEach
    void setUp() {
        randomMetricsCheckerMessages = createRandomMetricsCheckerMessages();
        sortedMetricsCheckerMessages = createSortedMetricsCheckerMessages();
    }

    protected abstract ArrayList<MetricsCheckerMessage> createRandomMetricsCheckerMessages();

    protected abstract ArrayList<MetricsCheckerMessage> createSortedMetricsCheckerMessages();

    @Test
    void compareTo() {
        randomMetricsCheckerMessages.sort(Comparator.naturalOrder());
        assertArrayEquals(sortedMetricsCheckerMessages.toArray(), randomMetricsCheckerMessages.toArray());
    }

    @Test
    void getMetric() {
        ArrayList<MetricsCheckerMessage> sortedMetricsCheckerMessages = createSortedMetricsCheckerMessages();
        if (sortedMetricsCheckerMessages.get(0) instanceof MetricsCheckerMessageSingle) {
            Assertions.assertEquals(MetricCheckerEntryHandler.Metric.AMC, sortedMetricsCheckerMessages.get(0).getMetric());
            Assertions.assertEquals(MetricCheckerEntryHandler.Metric.CE, sortedMetricsCheckerMessages.get(1).getMetric());
            Assertions.assertEquals(MetricCheckerEntryHandler.Metric.LCOM, sortedMetricsCheckerMessages.get(2).getMetric());
            Assertions.assertEquals(MetricCheckerEntryHandler.Metric.WMC, sortedMetricsCheckerMessages.get(3).getMetric());
        } else if (sortedMetricsCheckerMessages.get(0) instanceof MetricsCheckerMessageMulti) {
            Assertions.assertEquals(MetricCheckerEntryHandler.Metric.CC, sortedMetricsCheckerMessages.get(0).getMetric());
        }
    }
}