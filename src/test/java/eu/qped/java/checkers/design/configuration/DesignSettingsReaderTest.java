package eu.qped.java.checkers.design.configuration;

import eu.qped.java.checkers.design.utils.DesignTestUtility;
import eu.qped.java.checkers.mass.QFDesignSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static eu.qped.java.checkers.design.ckjm.DesignCheckEntryHandler.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link  DesignSettingsReader}.
 *
 * @author Jannik Seus
 */
class DesignSettingsReaderTest {

    private DesignSettingsReader designSettingsReader;
    private QFDesignSettings qfDesignSettings;

    @BeforeEach
    void setUp() {
        qfDesignSettings = DesignTestUtility.generateQfDesignSettings();
        this.designSettingsReader = DesignSettingsReader.builder().qfDesignSettings(qfDesignSettings).build();

    }

    @Test
    void readDesignSettingsTest() {
        DesignSettings designSettings = designSettingsReader.readDesignSettings(DesignSettings.builder().build());

        assertEquals(Double.parseDouble(qfDesignSettings.getAmcMin()), designSettings.getAmcThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCaMin()), designSettings.getCaThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCamMin()), designSettings.getCamThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCbmMin()), designSettings.getCbmThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCboMin()), designSettings.getCboThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCcMin()), designSettings.getCcThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCeMin()), designSettings.getCeThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getDamMin()), designSettings.getDamThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getDitMin()), designSettings.getDitThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getIcMin()), designSettings.getIcThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getLcomMin()), designSettings.getLcomThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getLcom3Min()), designSettings.getLcom3Threshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getLocMin()), designSettings.getLocThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getMfaMin()), designSettings.getMfaThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getMoaMin()), designSettings.getMoaThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getNocMin()), designSettings.getNocThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getNpmMin()), designSettings.getNpmThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getRfcMin()), designSettings.getRfcThreshold().getLowerBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getWmcMin()), designSettings.getWmcThreshold().getLowerBound());

        assertEquals(Double.parseDouble(qfDesignSettings.getAmcMax()), designSettings.getAmcThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCaMax()), designSettings.getCaThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCamMax()), designSettings.getCamThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCbmMax()), designSettings.getCbmThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCboMax()), designSettings.getCboThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCcMax()), designSettings.getCcThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getCeMax()), designSettings.getCeThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getDamMax()), designSettings.getDamThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getDitMax()), designSettings.getDitThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getIcMax()), designSettings.getIcThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getLcomMax()), designSettings.getLcomThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getLcom3Max()), designSettings.getLcom3Threshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getLocMax()), designSettings.getLocThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getMfaMax()), designSettings.getMfaThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getMoaMax()), designSettings.getMoaThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getNocMax()), designSettings.getNocThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getNpmMax()), designSettings.getNpmThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getRfcMax()), designSettings.getRfcThreshold().getUpperBound());
        assertEquals(Double.parseDouble(qfDesignSettings.getWmcMax()), designSettings.getWmcThreshold().getUpperBound());


    }

    @Test
    void retrieveMetricThresholdAllBoundsGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method retrieveMetricThresholdMethod = designSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        MetricThreshold retrievedThreshold = (MetricThreshold) retrieveMetricThresholdMethod.invoke(designSettingsReader, Metric.AMC);
        assertThat(retrievedThreshold.getLowerBound(), allOf(greaterThan(.0), lessThan(.5)));
        assertThat(retrievedThreshold.getUpperBound(), allOf(greaterThan(.5), lessThan(1.)));
        assertEquals(Metric.AMC, retrievedThreshold.getMetric());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricUpperBoundGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        QFDesignSettings qfDesignSettings = new QFDesignSettings();
        qfDesignSettings.setAmc("", "4.0");
        this.designSettingsReader = DesignSettingsReader.builder().qfDesignSettings(qfDesignSettings).build();

        Method retrieveMetricThresholdMethod = designSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        MetricThreshold retrievedThreshold = (MetricThreshold) retrieveMetricThresholdMethod.invoke(designSettingsReader, Metric.AMC);
        assertEquals(0, retrievedThreshold.getLowerBound());
        assertEquals(4.0, retrievedThreshold.getUpperBound());
        assertEquals(Metric.AMC, retrievedThreshold.getMetric());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricLowerBoundGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        QFDesignSettings qfDesignSettings = new QFDesignSettings();
        qfDesignSettings.setAmc("2.0", "");
        this.designSettingsReader = DesignSettingsReader.builder().qfDesignSettings(qfDesignSettings).build();

        Method retrieveMetricThresholdMethod = designSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        MetricThreshold retrievedThreshold = (MetricThreshold) retrieveMetricThresholdMethod.invoke(designSettingsReader, Metric.AMC);
        assertEquals(2.0, retrievedThreshold.getLowerBound());
        assertEquals(Double.MAX_VALUE, retrievedThreshold.getUpperBound());
        assertEquals(Metric.AMC, retrievedThreshold.getMetric());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricNoBoundGivenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        QFDesignSettings qfDesignSettings = new QFDesignSettings();
        qfDesignSettings.setAmc("", "");
        this.designSettingsReader = DesignSettingsReader.builder().qfDesignSettings(qfDesignSettings).build();

        Method retrieveMetricThresholdMethod = designSettingsReader.getClass().getDeclaredMethod("retrieveMetricThreshold", Metric.class);
        retrieveMetricThresholdMethod.setAccessible(true);
        MetricThreshold retrievedThreshold = (MetricThreshold) retrieveMetricThresholdMethod.invoke(designSettingsReader, Metric.AMC);
        assertEquals(0, retrievedThreshold.getLowerBound());
        assertEquals(Double.MAX_VALUE, retrievedThreshold.getUpperBound());
        assertEquals(Metric.AMC, retrievedThreshold.getMetric());
        retrieveMetricThresholdMethod.setAccessible(false);
    }

    @Test
    void retrieveMetricMaximumWrongFormatTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        qfDesignSettings.setAmc("0,4r", "0.99ö");
        this.designSettingsReader = DesignSettingsReader.builder().qfDesignSettings(qfDesignSettings).build();

        Method retrieveMetricMaximumMethod = designSettingsReader.getClass().getDeclaredMethod("retrieveMetricMaximum", Metric.class);
        retrieveMetricMaximumMethod.setAccessible(true);

        assertEquals(-1, (Double) retrieveMetricMaximumMethod.invoke(designSettingsReader, Metric.AMC));
        qfDesignSettings.setWmc(null, null);
        assertEquals(Double.MAX_VALUE, retrieveMetricMaximumMethod.invoke(designSettingsReader, Metric.WMC));

        retrieveMetricMaximumMethod.setAccessible(false);
    }
    @Test
    void retrieveMetricMinimumWrongFormatTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        qfDesignSettings.setAmc("0,4r", "0.99ö");
        this.designSettingsReader = DesignSettingsReader.builder().qfDesignSettings(qfDesignSettings).build();

        Method retrieveMetricMinimumMethod = designSettingsReader.getClass().getDeclaredMethod("retrieveMetricMinimum", Metric.class);
        retrieveMetricMinimumMethod.setAccessible(true);

        assertEquals(-1.0, retrieveMetricMinimumMethod.invoke(designSettingsReader, Metric.AMC));
        qfDesignSettings.setWmc(null, null);
        assertEquals(0d, retrieveMetricMinimumMethod.invoke(designSettingsReader, Metric.WMC));

        retrieveMetricMinimumMethod.setAccessible(false);
    }
}