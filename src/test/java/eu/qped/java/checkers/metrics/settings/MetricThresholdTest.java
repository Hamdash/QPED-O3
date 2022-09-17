package eu.qped.java.checkers.metrics.settings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.Metric.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link MetricThreshold}.
 *
 * @author Jannik Seus
 */
class MetricThresholdTest {

    private MetricThreshold metricThreshold1;
    private MetricThreshold metricThreshold2;
    private MetricThreshold metricThreshold3;
    private MetricThreshold metricThreshold4;
    private MetricThreshold metricThreshold5;

    @BeforeEach
    void setUp() {
        metricThreshold1 = new MetricThreshold(CC, 1, 10, false);
        metricThreshold2 = new MetricThreshold(LCOM3, 0.2, 2, false);
        metricThreshold4 = new MetricThreshold(AMC, 1.0, 5.4, false);
        metricThreshold5 = new MetricThreshold(RFC, 5.3, 1.73, false);
    }

    @Test
    void testInvalidInitialization() {
        assertThrows(IllegalStateException.class, () -> new MetricThreshold(LCOM, 4.7, -1, true));
    }

    @Test
    void compareTo() {
        ArrayList<MetricThreshold> thresholds = new ArrayList<>(List.of(metricThreshold1, metricThreshold2, metricThreshold4, metricThreshold5));
        thresholds.sort(Comparator.naturalOrder());
        ArrayList<MetricThreshold> sortedThresholds = new ArrayList<>(List.of(metricThreshold4, metricThreshold1, metricThreshold2, metricThreshold5));
        assertArrayEquals(sortedThresholds.toArray(), thresholds.toArray());
        assertThrows(IllegalStateException.class, () -> new MetricThreshold(null, 0d, 1d, false));
        assertThrows(IllegalStateException.class, () -> new MetricThreshold(null, 0d, 1d, true));
    }

    @Test
    void getLowerBound() throws NoSuchFieldException, IllegalAccessException {
        Field lowerBoundField = metricThreshold4.getClass().getDeclaredField("lowerBound");
        lowerBoundField.setAccessible(true);
        double lowerBound = (double) lowerBoundField.get(metricThreshold4);
        assertEquals(1.0, lowerBound);
        assertEquals(1.0, metricThreshold4.getLowerBound());
        lowerBoundField.setAccessible(false);
    }

    @Test
    void getUpperBound() throws NoSuchFieldException, IllegalAccessException {
        Field upperBoundField = metricThreshold4.getClass().getDeclaredField("upperBound");
        upperBoundField.setAccessible(true);
        double upperBound = (double) upperBoundField.get(metricThreshold4);
        assertEquals(5.4, upperBound);
        assertEquals(5.4, metricThreshold4.getUpperBound());
        upperBoundField.setAccessible(false);
    }

    @Test
    void setLowerBound() throws NoSuchFieldException, IllegalAccessException {
        Field lowerBoundField = metricThreshold4.getClass().getDeclaredField("lowerBound");
        lowerBoundField.setAccessible(true);
        lowerBoundField.set(metricThreshold4, 1.56d);
        assertEquals(1.56d, lowerBoundField.get(metricThreshold4));
        metricThreshold4.setLowerBound(1.21d);
        assertEquals(1.21d, metricThreshold4.getLowerBound());
        lowerBoundField.setAccessible(false);
    }

    @Test
    void setUpperBound() throws NoSuchFieldException, IllegalAccessException {
        Field upperBoundField = metricThreshold4.getClass().getDeclaredField("upperBound");
        upperBoundField.setAccessible(true);
        upperBoundField.set(metricThreshold4, 7.23d);
        assertEquals(7.23d, upperBoundField.get(metricThreshold4));
        metricThreshold4.setUpperBound(7.97d);
        assertEquals(7.97d, metricThreshold4.getUpperBound());
        upperBoundField.setAccessible(false);
    }
}