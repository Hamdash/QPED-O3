package eu.qped.java.checkers.metrics.configuration;

import eu.qped.java.checkers.metrics.utils.MetricsCheckerTestUtility;
import eu.qped.java.checkers.mass.QFMetricsSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Represents a test class for {@link MetricsCheckerSettings}.
 *
 * @author Jannik Seus
 */
class MetricsCheckerSettingsTest {

    private MetricsCheckerSettings metricsCheckerSettings;

    @BeforeEach
    void setUp() {
        this.metricsCheckerSettings = MetricsCheckerSettings.builder().build();
    }

    @Test
    void setQfMetricsSettingsTest() {
        QFMetricsSettings qfMetricsSettings = MetricsCheckerTestUtility.generateQMetricsSettings();

        metricsCheckerSettings.setAmcThreshold(new MetricThreshold(Metric.AMC, Double.parseDouble(qfMetricsSettings.getAmcMin()), Double.parseDouble(qfMetricsSettings.getAmcMax())));
        assertThat(metricsCheckerSettings.getAmcThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getAmcThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfMetricsSettings.getAmcCustomSuggestionUpper(), "AMC CUSTOM SUGGESTION UPPER BOUND");

        metricsCheckerSettings.setCaThreshold(new MetricThreshold(Metric.CA, Double.parseDouble(qfMetricsSettings.getCaMin()), Double.parseDouble(qfMetricsSettings.getCaMax())));
        assertThat(metricsCheckerSettings.getCaThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCaThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));


        metricsCheckerSettings.setCamThreshold(new MetricThreshold(Metric.CAM, Double.parseDouble(qfMetricsSettings.getCaMin()), Double.parseDouble(qfMetricsSettings.getCaMax())));
        assertThat(metricsCheckerSettings.getCamThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCamThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setCbmThreshold(new MetricThreshold(Metric.CBM, Double.parseDouble(qfMetricsSettings.getCbmMin()), Double.parseDouble(qfMetricsSettings.getCbmMax())));
        assertThat(metricsCheckerSettings.getCbmThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCbmThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setCboThreshold(new MetricThreshold(Metric.CBO, Double.parseDouble(qfMetricsSettings.getCboMin()), Double.parseDouble(qfMetricsSettings.getCboMax())));
        assertThat(metricsCheckerSettings.getCboThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCboThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setCcThreshold(new MetricThreshold(Metric.CC, Double.parseDouble(qfMetricsSettings.getCcMin()), Double.parseDouble(qfMetricsSettings.getCcMax())));
        assertThat(metricsCheckerSettings.getCcThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCcThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfMetricsSettings.getCcCustomSuggestionUpper(), "CC CUSTOM SUGGESTION UPPER BOUND");

        metricsCheckerSettings.setCeThreshold(new MetricThreshold(Metric.CE, Double.parseDouble(qfMetricsSettings.getCeMin()), Double.parseDouble(qfMetricsSettings.getCeMax())));
        assertThat(metricsCheckerSettings.getCeThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getCeThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setDamThreshold(new MetricThreshold(Metric.DAM, Double.parseDouble(qfMetricsSettings.getDamMin()), Double.parseDouble(qfMetricsSettings.getDamMax())));
        assertThat(metricsCheckerSettings.getDamThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getDamThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setDitThreshold(new MetricThreshold(Metric.DIT, Double.parseDouble(qfMetricsSettings.getDitMin()), Double.parseDouble(qfMetricsSettings.getDitMax())));
        assertThat(metricsCheckerSettings.getDitThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getDitThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setIcThreshold(new MetricThreshold(Metric.IC, Double.parseDouble(qfMetricsSettings.getIcMin()), Double.parseDouble(qfMetricsSettings.getIcMax())));
        assertThat(metricsCheckerSettings.getIcThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getIcThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setLcomThreshold(new MetricThreshold(Metric.LCOM, Double.parseDouble(qfMetricsSettings.getLcomMin()), Double.parseDouble(qfMetricsSettings.getLcomMax())));
        assertThat(metricsCheckerSettings.getLcomThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getLcomThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setLcom3Threshold(new MetricThreshold(Metric.LCOM3, Double.parseDouble(qfMetricsSettings.getLcom3Min()), Double.parseDouble(qfMetricsSettings.getLcom3Max())));
        assertThat(metricsCheckerSettings.getLcom3Threshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getLcom3Threshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfMetricsSettings.getLcom3CustomSuggestionLower(), "LCOM3 CUSTOM SUGGESTION LOWER BOUND");

        metricsCheckerSettings.setLocThreshold(new MetricThreshold(Metric.LOC, Double.parseDouble(qfMetricsSettings.getLocMin()), Double.parseDouble(qfMetricsSettings.getLocMax())));
        assertThat(metricsCheckerSettings.getLocThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getLocThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setMfaThreshold(new MetricThreshold(Metric.MFA, Double.parseDouble(qfMetricsSettings.getMfaMin()), Double.parseDouble(qfMetricsSettings.getMfaMax())));
        assertThat(metricsCheckerSettings.getMfaThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getMfaThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setMoaThreshold(new MetricThreshold(Metric.MOA, Double.parseDouble(qfMetricsSettings.getMoaMin()), Double.parseDouble(qfMetricsSettings.getMoaMax())));
        assertThat(metricsCheckerSettings.getMoaThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getMoaThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setNocThreshold(new MetricThreshold(Metric.NOC, Double.parseDouble(qfMetricsSettings.getNocMin()), Double.parseDouble(qfMetricsSettings.getNocMax())));
        assertThat(metricsCheckerSettings.getNocThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getNocThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setNpmThreshold(new MetricThreshold(Metric.NPM, Double.parseDouble(qfMetricsSettings.getNpmMin()), Double.parseDouble(qfMetricsSettings.getNpmMax())));
        assertThat(metricsCheckerSettings.getNpmThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getNpmThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setRfcThreshold(new MetricThreshold(Metric.RFC, Double.parseDouble(qfMetricsSettings.getRfcMin()), Double.parseDouble(qfMetricsSettings.getRfcMax())));
        assertThat(metricsCheckerSettings.getRfcThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getRfcThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));

        metricsCheckerSettings.setWmcThreshold(new MetricThreshold(Metric.WMC, Double.parseDouble(qfMetricsSettings.getWmcMin()), Double.parseDouble(qfMetricsSettings.getWmcMax())));
        assertThat(metricsCheckerSettings.getWmcThreshold().getLowerBound(), allOf(greaterThan(0.), lessThan(0.5)));
        assertThat(metricsCheckerSettings.getWmcThreshold().getUpperBound(), allOf(greaterThan(0.5), lessThan(1.)));
        assertEquals(qfMetricsSettings.getWmcCustomSuggestionLower(), "WMC CUSTOM SUGGESTION LOWER BOUND");
    }
}