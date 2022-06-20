package eu.qped.java.checkers.design;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static eu.qped.java.checkers.design.ckjm.SaveMapResults.*;

/**
 * Test class for {@link DesignCheckReport}
 *
 * @author Jannik Seus
 */
@ExtendWith(MockitoExtension.class)
class DesignCheckReportTest {

    private DesignCheckReport designCheckReport1;
    private DesignCheckReport designCheckReport2;
    private static Field[] fields;
    private Map<String, Map<Metric, Double>> sampleMapMetrics;
    private Map<Metric, Double> sampleMapThresholds;

    @BeforeAll
    static void beforeAll() {
        fields = DesignCheckReport.class.getDeclaredFields();
    }

    @BeforeEach
    void setUp() {
        sampleMapMetrics = Map.of("Class A", Map.of(Metric.AMC, 1.0));
        sampleMapThresholds = Map.of(Metric.LCOM, 1.0);
        designCheckReport1 = new DesignCheckReport();
        designCheckReport2 = new DesignCheckReport(
                mock(List.class),
                mock(Map.class));


    }

    @Test
    void setMetricsMap() throws IllegalAccessException {
        Field metricsMapField = getFieldByName("metricsMap");
        if (metricsMapField != null) {
            metricsMapField.setAccessible(true);
            sampleMapMetrics = Map.of("Class A", Map.of(Metric.AMC, 1.0));
            designCheckReport1.setMetricsMap(sampleMapMetrics);
            designCheckReport2.setMetricsMap(sampleMapMetrics);

            assertEquals(sampleMapMetrics, metricsMapField.get(designCheckReport1));
            assertEquals(sampleMapMetrics, metricsMapField.get(designCheckReport2));
        }
    }

    @Test
    void getMetricsMap() {
        Field metricsMapField = getFieldByName("metricsMap");
        if (metricsMapField != null) {
            metricsMapField.setAccessible(true);
            designCheckReport1.setMetricsMap(sampleMapMetrics); //setter already tested
            designCheckReport2.setMetricsMap(sampleMapMetrics);
            Map<String, Map<Metric, Double>> retrievedMap1 = designCheckReport1.getMetricsMap();
            Map<String, Map<Metric, Double>> retrievedMap2 = designCheckReport2.getMetricsMap();

            assertEquals(sampleMapMetrics, retrievedMap1);
            assertEquals(sampleMapMetrics, retrievedMap2);
        }
    }

    @Test
    void builder() {
        DesignCheckReport builtDCR = DesignCheckReport.builder().build();
        DesignCheckReport mockedDCR = new DesignCheckReport();

        List<Field> declaredFieldsBuilt = Arrays.asList(builtDCR.getClass().getDeclaredFields());
        List<Field> declaredFieldsMocked = Arrays.asList(mockedDCR.getClass().getDeclaredFields());

        List<String> declaredFieldNamesBuilt = new ArrayList<>();
        List<String> declaredFieldNamesMocked = new ArrayList<>();

        assertEquals(declaredFieldsBuilt.size(), declaredFieldsMocked.size());
        for (int i = 0; i < declaredFieldsBuilt.size(); i++) {
            declaredFieldNamesBuilt.add(declaredFieldsBuilt.get(i).getName());
            declaredFieldNamesMocked.add(declaredFieldsMocked.get(i).getName());
        }
        assertTrue(declaredFieldNamesMocked.containsAll(declaredFieldNamesBuilt));
        assertArrayEquals(declaredFieldNamesBuilt.toArray(), declaredFieldNamesMocked.toArray());
        assertArrayEquals(declaredFieldsBuilt.toArray(), declaredFieldsMocked.toArray());
    }


    private static Field getFieldByName(String name) {
        for (Field f : fields) {
            if (f.getName().equals(name)) return f;
        }
        return null;
    }
}