package eu.qped.java.checkers.metrics.data;

import eu.qped.java.checkers.metrics.data.report.MetricCheckerEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link MetricCheckerEntry}.
 *
 * @author Jannik Seus
 */
class MetricCheckerEntryTest {

    private MetricCheckerEntry metricCheckerEntry1;
    private MetricCheckerEntry metricCheckerEntry2;
    private MetricCheckerEntry metricCheckerEntry3;

    @BeforeEach
    void setUp() {
        metricCheckerEntry1 = new MetricCheckerEntry("className", List.of());
        metricCheckerEntry2 = new MetricCheckerEntry("", List.of());
        metricCheckerEntry3 = new MetricCheckerEntry("anotherClass", List.of());
    }

    @Test
    void compareTo() {
        ArrayList<MetricCheckerEntry> entries = new ArrayList<>(List.of(metricCheckerEntry1, metricCheckerEntry2, metricCheckerEntry3));
        entries.sort(Comparator.naturalOrder());
        List<MetricCheckerEntry> sortedEntries = List.of(metricCheckerEntry2, metricCheckerEntry3, metricCheckerEntry1);
        assertArrayEquals(sortedEntries.toArray(), entries.toArray());
    }

    @Test
    void getClassName() throws NoSuchFieldException, IllegalAccessException {
        Field classNameField = metricCheckerEntry1.getClass().getDeclaredField("className");
        classNameField.setAccessible(true);
        assertEquals("className", classNameField.get(metricCheckerEntry1));
        assertEquals("className", metricCheckerEntry1.getClassName());
        classNameField.setAccessible(false);

    }

    @Test
    void getMetricsForClass() throws NoSuchFieldException, IllegalAccessException {
        Field metricsForClassField = metricCheckerEntry1.getClass().getDeclaredField("metricsForClass");
        metricsForClassField.setAccessible(true);
        Object[] expected = List.of().toArray();
        assertArrayEquals(expected, ((List<?>) metricsForClassField.get(metricCheckerEntry1)).toArray());
        assertArrayEquals(expected, metricCheckerEntry1.getMetricsForClass().toArray());
        metricsForClassField.setAccessible(false);
    }

}