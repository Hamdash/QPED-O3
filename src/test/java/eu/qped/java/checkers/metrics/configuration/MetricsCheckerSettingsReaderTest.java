package eu.qped.java.checkers.metrics.configuration;

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
 * Test class for {@link  MetricsCheckerSettingsReader}.
 *
 * @author Jannik Seus
 */
class MetricsCheckerSettingsReaderTest {

    private MetricsCheckerSettingsReader metricsCheckerSettingsReader;
    private QFMetricsSettings qfMetricsSettings;

    @BeforeEach
    void setUp() {
        qfMetricsSettings = MetricsCheckerTestUtility.generateQMetricsSettings();
        this.metricsCheckerSettingsReader = MetricsCheckerSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

    }

    @Test
    void readMetricsSettingsTest() {
        MetricsCheckerSettings metricsCheckerSettings = metricsCheckerSettingsReader.readMetricsCheckerSettings(MetricsCheckerSettings.builder().build());

        assertEquals(Double.parseDouble(qfMetricsSettings.getAmcMin()), metricsCheckerSettings.getAmcThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCaMin()), metricsCheckerSettings.getCaThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCamMin()), metricsCheckerSettings.getCamThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCbmMin()), metricsCheckerSettings.getCbmThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCboMin()), metricsCheckerSettings.getCboThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCcMin()), metricsCheckerSettings.getCcThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCeMin()), metricsCheckerSettings.getCeThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDamMin()), metricsCheckerSettings.getDamThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDitMin()), metricsCheckerSettings.getDitThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getIcMin()), metricsCheckerSettings.getIcThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcomMin()), metricsCheckerSettings.getLcomThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcom3Min()), metricsCheckerSettings.getLcom3Threshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLocMin()), metricsCheckerSettings.getLocThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMfaMin()), metricsCheckerSettings.getMfaThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMoaMin()), metricsCheckerSettings.getMoaThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNocMin()), metricsCheckerSettings.getNocThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNpmMin()), metricsCheckerSettings.getNpmThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getRfcMin()), metricsCheckerSettings.getRfcThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getWmcMin()), metricsCheckerSettings.getWmcThreshold().getLowerBound());

        assertEquals(Double.parseDouble(qfMetricsSettings.getAmcMax()), metricsCheckerSettings.getAmcThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCaMax()), metricsCheckerSettings.getCaThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCamMax()), metricsCheckerSettings.getCamThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCbmMax()), metricsCheckerSettings.getCbmThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCboMax()), metricsCheckerSettings.getCboThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCcMax()), metricsCheckerSettings.getCcThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCeMax()), metricsCheckerSettings.getCeThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDamMax()), metricsCheckerSettings.getDamThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDitMax()), metricsCheckerSettings.getDitThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getIcMax()), metricsCheckerSettings.getIcThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcomMax()), metricsCheckerSettings.getLcomThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcom3Max()), metricsCheckerSettings.getLcom3Threshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLocMax()), metricsCheckerSettings.getLocThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMfaMax()), metricsCheckerSettings.getMfaThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMoaMax()), metricsCheckerSettings.getMoaThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNocMax()), metricsCheckerSettings.getNocThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNpmMax()), metricsCheckerSettings.getNpmThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getRfcMax()), metricsCheckerSettings.getRfcThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getWmcMax()), metricsCheckerSettings.getWmcThreshold().getUpperBound());


    }

    @Test
    void retrieveMetricThresholdAllBoundsGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method retrieveMetricThresholdMethod = metricsCheckerSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        MetricThreshold retrievedThreshold = (MetricThreshold) retrieveMetricThresholdMethod.invoke(metricsCheckerSettingsReader, Metric.AMC);
        assertThat(retrievedThreshold.getLowerBound(), allOf(greaterThan(.0), lessThan(.5)));
        assertThat(retrievedThreshold.getUpperBound(), allOf(greaterThan(.5), lessThan(1.)));
        assertEquals(Metric.AMC, retrievedThreshold.getMetric());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricUpperBoundGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        QFMetricsSettings qfMetricsSettings = new QFMetricsSettings();
        qfMetricsSettings.setAmc("", "4.0");
        this.metricsCheckerSettingsReader = MetricsCheckerSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

        Method retrieveMetricThresholdMethod = metricsCheckerSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        MetricThreshold retrievedThreshold = (MetricThreshold) retrieveMetricThresholdMethod.invoke(metricsCheckerSettingsReader, Metric.AMC);
        assertEquals(0, retrievedThreshold.getLowerBound());
        assertEquals(4.0, retrievedThreshold.getUpperBound());
        assertEquals(Metric.AMC, retrievedThreshold.getMetric());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricLowerBoundGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        QFMetricsSettings qfMetricsSettings = new QFMetricsSettings();
        qfMetricsSettings.setAmc("2.0", "");
        this.metricsCheckerSettingsReader = MetricsCheckerSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

        Method retrieveMetricThresholdMethod = metricsCheckerSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        MetricThreshold retrievedThreshold = (MetricThreshold) retrieveMetricThresholdMethod.invoke(metricsCheckerSettingsReader, Metric.AMC);
        assertEquals(2.0, retrievedThreshold.getLowerBound());
        assertEquals(Double.MAX_VALUE, retrievedThreshold.getUpperBound());
        assertEquals(Metric.AMC, retrievedThreshold.getMetric());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricNoBoundGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        QFMetricsSettings qfMetricsSettings = new QFMetricsSettings();
        qfMetricsSettings.setAmc("", "");
        this.metricsCheckerSettingsReader = MetricsCheckerSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

        Method retrieveMetricThresholdMethod = metricsCheckerSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        MetricThreshold retrievedThreshold = (MetricThreshold) retrieveMetricThresholdMethod.invoke(metricsCheckerSettingsReader, Metric.AMC);
        assertEquals(0, retrievedThreshold.getLowerBound());
        assertEquals(Double.MAX_VALUE, retrievedThreshold.getUpperBound());
        assertEquals(Metric.AMC, retrievedThreshold.getMetric());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricMaximumWrongFormatTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        qfMetricsSettings.setAmc("0,4r", "0.99ö");
        this.metricsCheckerSettingsReader = MetricsCheckerSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

        Method retrieveMetricMaximumMethod = metricsCheckerSettingsReader.getClass().getDeclaredMethod("retrieveMetricMaximum", Metric.class);
        retrieveMetricMaximumMethod.setAccessible(true);

        assertEquals(-1, (Double) retrieveMetricMaximumMethod.invoke(metricsCheckerSettingsReader, Metric.AMC));
        qfMetricsSettings.setWmc(null, null);
        assertEquals(Double.MAX_VALUE, retrieveMetricMaximumMethod.invoke(metricsCheckerSettingsReader, Metric.WMC));

        retrieveMetricMaximumMethod.setAccessible(false);
    }
    @Test
    void retrieveMetricMinimumWrongFormatTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        qfMetricsSettings.setAmc("0,4r", "0.99ö");
        this.metricsCheckerSettingsReader = MetricsCheckerSettingsReader.builder().qfMetricsSettings(qfMetricsSettings).build();

        Method retrieveMetricMinimumMethod = metricsCheckerSettingsReader.getClass().getDeclaredMethod("retrieveMetricMinimum", Metric.class);
        retrieveMetricMinimumMethod.setAccessible(true);

        assertEquals(-1.0, retrieveMetricMinimumMethod.invoke(metricsCheckerSettingsReader, Metric.AMC));
        qfMetricsSettings.setWmc(null, null);
        assertEquals(0d, retrieveMetricMinimumMethod.invoke(metricsCheckerSettingsReader, Metric.WMC));

        retrieveMetricMinimumMethod.setAccessible(false);
    }
}