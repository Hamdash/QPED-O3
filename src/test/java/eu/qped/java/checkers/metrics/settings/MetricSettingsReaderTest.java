package eu.qped.java.checkers.metrics.settings;

import eu.qped.java.checkers.metrics.utils.MetricsCheckerTestUtility;
import eu.qped.java.checkers.mass.QFMetricsSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link  MetricSettingsReader}.
 *
 * @author Jannik Seus
 */
class MetricSettingsReaderTest {

    private MetricSettingsReader metricSettingsReader;
    private QFMetricsSettings qfMetricsSettings;

    @BeforeEach
    void setUp() {
        qfMetricsSettings = MetricsCheckerTestUtility.generateQMetricsSettings();
        this.metricSettingsReader = MetricSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

    }

    @Test
    void readMetricsSettingsTest() {
        MetricSettings metricSettings = metricSettingsReader.readMetricsCheckerSettings(MetricSettings.builder().build());

        assertEquals(Double.parseDouble(qfMetricsSettings.getAmcMin()), metricSettings.getAmcConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCaMin()), metricSettings.getCaConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCamMin()), metricSettings.getCamConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCbmMin()), metricSettings.getCbmConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCboMin()), metricSettings.getCboConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCcMin()), metricSettings.getCcConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCeMin()), metricSettings.getCeConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDamMin()), metricSettings.getDamConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDitMin()), metricSettings.getDitConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getIcMin()), metricSettings.getIcConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcomMin()), metricSettings.getLcomConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcom3Min()), metricSettings.getLcom3Config().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLocMin()), metricSettings.getLocConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMfaMin()), metricSettings.getMfaConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMoaMin()), metricSettings.getMoaConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNocMin()), metricSettings.getNocConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNpmMin()), metricSettings.getNpmConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getRfcMin()), metricSettings.getRfcConfig().getMetricThreshold().getMin());
        assertEquals(Double.parseDouble(qfMetricsSettings.getWmcMin()), metricSettings.getWmcConfig().getMetricThreshold().getMin());

        assertEquals(Double.parseDouble(qfMetricsSettings.getAmcMax()), metricSettings.getAmcConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCaMax()), metricSettings.getCaConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCamMax()), metricSettings.getCamConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCbmMax()), metricSettings.getCbmConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCboMax()), metricSettings.getCboConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCcMax()), metricSettings.getCcConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCeMax()), metricSettings.getCeConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDamMax()), metricSettings.getDamConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDitMax()), metricSettings.getDitConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getIcMax()), metricSettings.getIcConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcomMax()), metricSettings.getLcomConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcom3Max()), metricSettings.getLcom3Config().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLocMax()), metricSettings.getLocConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMfaMax()), metricSettings.getMfaConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMoaMax()), metricSettings.getMoaConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNocMax()), metricSettings.getNocConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNpmMax()), metricSettings.getNpmConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getRfcMax()), metricSettings.getRfcConfig().getMetricThreshold().getMax());
        assertEquals(Double.parseDouble(qfMetricsSettings.getWmcMax()), metricSettings.getWmcConfig().getMetricThreshold().getMax());


    }

    @Test
    void retrieveMetricThresholdAllBoundsGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method retrieveMetricThresholdMethod = metricSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        MetricThreshold retrievedThreshold = (MetricThreshold) retrieveMetricThresholdMethod.invoke(metricSettingsReader, Metric.AMC);
        assertThat(retrievedThreshold.getMin(), allOf(greaterThan(.0), lessThan(.5)));
        assertThat(retrievedThreshold.getMax(), allOf(greaterThan(.5), lessThan(1.)));
        assertEquals(Metric.AMC, retrievedThreshold.getMetric());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricUpperBoundGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        QFMetricsSettings qfMetricsSettings = new QFMetricsSettings();
        qfMetricsSettings.setAmc("", "4.0");
        this.metricSettingsReader = MetricSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

        Method retrieveMetricThresholdMethod = metricSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        InvocationTargetException invocationTargetException = assertThrows(InvocationTargetException.class, () ->retrieveMetricThresholdMethod.invoke(metricSettingsReader, Metric.AMC));
        assertEquals(IllegalStateException.class.getName(), invocationTargetException.getTargetException().getClass().getName());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricLowerBoundGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        QFMetricsSettings qfMetricsSettings = new QFMetricsSettings();
        qfMetricsSettings.setAmc("2.0", "");
        this.metricSettingsReader = MetricSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

        Method retrieveMetricThresholdMethod = metricSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        InvocationTargetException invocationTargetException = assertThrows(InvocationTargetException.class, () -> retrieveMetricThresholdMethod.invoke(metricSettingsReader, Metric.AMC));
        assertEquals(IllegalStateException.class.getName(), invocationTargetException.getTargetException().getClass().getName());

        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricNoBoundGivenTest() throws NoSuchMethodException {
        QFMetricsSettings qfMetricsSettings = new QFMetricsSettings();
        qfMetricsSettings.setAmc("", "");
        this.metricSettingsReader = MetricSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

        Method retrieveMetricThresholdMethod = metricSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        InvocationTargetException invocationTargetException = assertThrows(InvocationTargetException.class, () ->retrieveMetricThresholdMethod.invoke(metricSettingsReader, Metric.AMC));
        assertEquals(IllegalStateException.class.getName(), invocationTargetException.getTargetException().getClass().getName());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricMaximumWrongFormatTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        qfMetricsSettings.setAmc("0,4r", "0.99ö");
        this.metricSettingsReader = MetricSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

        Method retrieveMetricMaximumMethod = metricSettingsReader.getClass().getDeclaredMethod("retrieveMetricMaximum", Metric.class);
        retrieveMetricMaximumMethod.setAccessible(true);

        assertEquals(-1d, retrieveMetricMaximumMethod.invoke(metricSettingsReader, Metric.AMC));
        qfMetricsSettings.setWmc(null, null);
        assertEquals(-1d, retrieveMetricMaximumMethod.invoke(metricSettingsReader, Metric.WMC));

        retrieveMetricMaximumMethod.setAccessible(false);
    }
    @Test
    void retrieveMetricMinimumWrongFormatTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        qfMetricsSettings.setAmc("0,4r", "0.99ö");
        this.metricSettingsReader = MetricSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

        Method retrieveMetricMinimumMethod = metricSettingsReader.getClass().getDeclaredMethod("retrieveMetricMinimum", Metric.class);
        retrieveMetricMinimumMethod.setAccessible(true);

        assertEquals(-1.0, retrieveMetricMinimumMethod.invoke(metricSettingsReader, Metric.AMC));
        qfMetricsSettings.setWmc(null, null);
        assertEquals(-1d, retrieveMetricMinimumMethod.invoke(metricSettingsReader, Metric.WMC));

        retrieveMetricMinimumMethod.setAccessible(false);
    }
}