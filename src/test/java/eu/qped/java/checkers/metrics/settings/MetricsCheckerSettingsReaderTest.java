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

        assertEquals(Double.parseDouble(qfMetricsSettings.getAmcMin()), metricsCheckerSettings.getAmcConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCaMin()), metricsCheckerSettings.getCaConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCamMin()), metricsCheckerSettings.getCamConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCbmMin()), metricsCheckerSettings.getCbmConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCboMin()), metricsCheckerSettings.getCboConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCcMin()), metricsCheckerSettings.getCcConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCeMin()), metricsCheckerSettings.getCeConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDamMin()), metricsCheckerSettings.getDamConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDitMin()), metricsCheckerSettings.getDitConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getIcMin()), metricsCheckerSettings.getIcConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcomMin()), metricsCheckerSettings.getLcomConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcom3Min()), metricsCheckerSettings.getLcom3Config().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLocMin()), metricsCheckerSettings.getLocConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMfaMin()), metricsCheckerSettings.getMfaConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMoaMin()), metricsCheckerSettings.getMoaConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNocMin()), metricsCheckerSettings.getNocConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNpmMin()), metricsCheckerSettings.getNpmConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getRfcMin()), metricsCheckerSettings.getRfcConfig().getMetricThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getWmcMin()), metricsCheckerSettings.getWmcConfig().getMetricThreshold().getLowerBound());

        assertEquals(Double.parseDouble(qfMetricsSettings.getAmcMax()), metricsCheckerSettings.getAmcConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCaMax()), metricsCheckerSettings.getCaConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCamMax()), metricsCheckerSettings.getCamConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCbmMax()), metricsCheckerSettings.getCbmConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCboMax()), metricsCheckerSettings.getCboConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCcMax()), metricsCheckerSettings.getCcConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getCeMax()), metricsCheckerSettings.getCeConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDamMax()), metricsCheckerSettings.getDamConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getDitMax()), metricsCheckerSettings.getDitConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getIcMax()), metricsCheckerSettings.getIcConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcomMax()), metricsCheckerSettings.getLcomConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLcom3Max()), metricsCheckerSettings.getLcom3Config().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getLocMax()), metricsCheckerSettings.getLocConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMfaMax()), metricsCheckerSettings.getMfaConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getMoaMax()), metricsCheckerSettings.getMoaConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNocMax()), metricsCheckerSettings.getNocConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getNpmMax()), metricsCheckerSettings.getNpmConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getRfcMax()), metricsCheckerSettings.getRfcConfig().getMetricThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfMetricsSettings.getWmcMax()), metricsCheckerSettings.getWmcConfig().getMetricThreshold().getUpperBound());


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