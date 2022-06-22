package eu.qped.java.checkers.design;

import eu.qped.java.checkers.design.data.DesignCheckEntry;
import eu.qped.java.checkers.design.data.DesignCheckMessage;
import eu.qped.java.checkers.design.data.DesignCheckReport;
import eu.qped.java.checkers.design.utils.TestUtility;
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
import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;

/**
 * Test class for {@link DesignCheckReport}
 *
 * @author Jannik Seus
 */
@ExtendWith(MockitoExtension.class)
class DesignCheckReportTest {

    private DesignCheckReport designCheckReport1;
    private DesignCheckReport designCheckReport2;
    private final Field[] fields = DesignCheckReport.class.getDeclaredFields();;
    private List<DesignCheckEntry> sampleMapMetrics;
    private Map<Metric, Double> sampleMapThresholds;


    @BeforeEach
    void setUp() {
        sampleMapMetrics = List.of(new DesignCheckEntry("Class A", List.of(new DesignCheckMessage(Metric.AMC, 1.0))));
        sampleMapThresholds = Map.of(Metric.LCOM, 1.0);
        designCheckReport1 = new DesignCheckReport();
        designCheckReport2 = new DesignCheckReport(
                mock(List.class),
                sampleMapMetrics);


    }

    @Test
    void setMetricsMap() throws IllegalAccessException {
        Field metricsMapField = TestUtility.getFieldByName("metricsMap", fields);
        if (metricsMapField != null) {
            metricsMapField.setAccessible(true);
            sampleMapMetrics = List.of(new DesignCheckEntry("Class A", List.of(new DesignCheckMessage(Metric.AMC, 1.0))));


            assertNull(metricsMapField.get(designCheckReport1));
            assertEquals(sampleMapMetrics, metricsMapField.get(designCheckReport2));
        }
    }

    @Test
    void getMetricsMap() {
        Field metricsMapField = TestUtility.getFieldByName("metricsMap", fields);
        if (metricsMapField != null) {
            metricsMapField.setAccessible(true);
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


}